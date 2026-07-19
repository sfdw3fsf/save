<template src='./frmGanThuocTinh_LVT.html'></template>
<script>
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import frmGanThuocTinh_LVTAPI from  '../api/frmGanThuocTinh_LVTAPI'
// import { getterName,actionName,statePropertyName,mutationName} from '../store/tracuuvattutrongkho'
import select2 from '@/components/Select2.vue'
import { Freeze, Group, Page, Filter, Sort, Resize, ForeignKey} from "@syncfusion/ej2-vue-grids";
import { Query,Predicate } from '@syncfusion/ej2-data';
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {

  components: {breadcrumb, appSelect2: select2 },
  name: "frmGanThuocTinh_LVT",
  mounted() {
  },
  provide: {
    grid: [ Freeze, Group, Page,Filter,Sort,Resize, ForeignKey ]
  },
  computed: {
    // ...mapState("tracuuvattutrongkho", statePropertyName),
    // ...mapGetters("tracuuvattutrongkho", getterName),
    
  },
  data() {
    return {
        vattu_id: 0,
        thietbi_id: 0,
        ChapNhan: false,
        dt: [],

        gridVatTu: []

        
    };
  },
  methods: {
    
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    async frmGanThuocTinh_LVT_Load(){
      if (this.vattu_id != 0 && this.vattu_id != null){
        await frmGanThuocTinh_LVTAPI.getDSThuocTinhLoaiVT(this.axios, {vattu_id: this.vattu_id, thietbi_id: this.thietbi_id})
        .then(
            (response) => {
            if (response.data.error_code === 'BSS-00000000') {
                if (response.data.data !== undefined) {
                    this.dt = response.data.data
                    this.gridVatTu = response.data.data
                }
            }
            }
        )
        .catch(function() {

        })
        .finally(function() {

        })
      }
    },
    async GOI_DL_CAPPHAT(dt){
        var xdoc = []
        dt.forEach(a=>{
            xdoc.push({
                tbi: this.thietbi_id.toString(),
                tt: a["THUOCTINH_ID"].toString(),
                gt: a["GIATRI"].toString()
            })
        })
        return JSON.stringify(xdoc)
    },
    async tsbtnHoanThanh_Click(){
        // this.$refs.tsbtn.focus()
        this.dt = this.gridVatTu
        let data = await this.GOI_DL_CAPPHAT(this.dt)
        let kq = ""
        await frmGanThuocTinh_LVTAPI.XacNhanGanThuocTinh(this.axios, {ds: data})
            .then(
                (response) => {
                  kq = response.data.data || ""
                }
            )
            .catch(function() {

            })
            .finally(function() {

            })
        if (kq.toString() != "1"){
            this.$toast.error(kq)
            return
        }
        this.ChapNhan = true
        await this.$parent.$parent.XacNhan_frmGanThuocTinh()
        await this.Close()
    },
    edit_gridViewVatTu(arg){
      let gridViewVatTu = this.$refs.gridViewVatTu
      let changes = gridViewVatTu.getBatchChanges() 
      gridViewVatTu.batchUpdate(changes)
    },
    async Close(){
        this.$parent.hide()
    }
  },
created: async function() {
    await this.frmGanThuocTinh_LVT_Load()
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
</style>