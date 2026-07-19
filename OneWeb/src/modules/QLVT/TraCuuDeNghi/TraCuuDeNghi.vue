<template src='./TraCuuDeNghi.html'></template>
<style scoped src='./TraCuuDeNghi.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/tracuudenghi";
import select2 from "@/components/Select2.vue";
import Vue from "vue";
import EventBus from '@/utils/eventBus'
import {previewPrint} from "../../../utils/util";
import { Query,Predicate } from '@syncfusion/ej2-data';

export default {
  components: { breadcrumb, appSelect2: select2 },
  name: "TraCuuDeNghi",
  mounted() {},

  data() {
    return {
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      URL: "http://10.70.52.164:8081/FILE/",
      params:{
        tuNgay: new Date(),
        denNgay: new Date(),
        maPhieu:"",
        loaiPhieuId: "0",
        quyetToanId: 0
      },
      Enabled:{
        inPhieu:false
      },
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      dataSource:{
        DSFile:[],
        DSPhieu:[],
        DSLoaiPhieu:[],
        DSChiTietPhieu: [],
        DSChiTietNhanVien: []
      },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      filterLoaiPhieu: function(e) {
        var query = new Query();
        let predicate;  
        
        if (e.text !== ''){
          predicate  = new Predicate('LOAIPHIEU', 'contains', e.text.trim(), true);              
          query.where(predicate)
        }
        e.updateData(this.dataSource, query);
      },
      onClickNhanVien: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>
                        <button class="btn btn-main" @click="openFrmNhanVien" title="danh sách nhân viên thực hiện">
                          <span class="icon one-search" ></span>
                        </button>
                      </div>`,                        
            data () { 
              return { data: {} }; 
            },
            methods: {
              openFrmNhanVien() {                  
                  EventBus.$emit('openFrmNhanVien', this.data);
              },              
            }
          })
        }
      },
    };
  },

  computed: {
    ...mapState("tracuudenghi", statePropertyName),
    ...mapGetters("tracuudenghi", getterName),
  },
  methods: {
    ...mapActions("tracuudenghi", actionName),
    ...mapMutations("tracuudenghi", mutationName),
    
    async open(){

    },
    async dialogOpen(){
      try{
        this.loading(true)
        this.dataSource.DSLoaiPhieu = await this.getDSLoaiPhieu({    
          nhanVienId: 0,
          kieu: 5,
          param1: "",
          param2: ""
        })
        // Từ ngày bằng ngày hiện tại trừ lui 1 tháng
        var currentDate = new Date()
        currentDate.setMonth(currentDate.getMonth() - 1)
        this.params.tuNgay = new Date(currentDate)
      
        await this.onClickTimKiem()
       
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
    },

    async dialogClose(){
      this.$refs.frmTraCuuDenghi.hide();
    },
    async onClickTimKiem(){
      try{
        this.loading(true);
        var DSPhieu = await this.getDSPhieu({
          nhanVienId: this.$root.token.getNhanVienID(),
          maPhieu: this.params.maPhieu,
          loaiPhieuId: this.params.loaiPhieuId,
          ttPhId: 0,
          tuNgay: moment(this.params.tuNgay).format("DD/MM/yyyy"),
          denNgay:moment(this.params.denNgay).format("DD/MM/yyyy")
        })
        if(DSPhieu.length == 0){
          this.dataSource.DSChiTietPhieu = []
          this.dataSource.DSFile = []
        }else{
          DSPhieu.forEach(e=>{        
            e.NGAYGIAO = e.NGAYGIAO.replaceAll("-","/")
          })
        }      
        this.dataSource.DSPhieu = DSPhieu
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },

    async onClickDownFile(value) {
      if(value) {           
        var checkFile = await this.checkFileExist({fileSource : value.urlFile})        
        var linkFile = ""
        if(checkFile.message == "Success" && checkFile.data.data){
          let getLink = await this.getLinkFile({fileSource : value.urlFile}) 
          linkFile = getLink.data.name                 
        }else{  
          linkFile  = this.URL + value.urlFile     
        }
        const downloadLink = document.createElement("a");      
        downloadLink.href = linkFile;
        const filePath = value.urlFile.split('/');
        const fileName = filePath[filePath.length - 1];
        downloadLink.download = fileName;    
        downloadLink.target = "_blank";
        downloadLink.click();   
        
      }
    },
    async onDownloadFileHandler(value) {
      if(value) {
        const data = await this.downloadFile(value);
        if(data) {
          const filePath= value.split('/');
          const linkSource = `data:application/octet-stream;base64,${data.data.body.data}`;
          const downloadLink = document.createElement("a");
          const fileName = filePath[filePath.length - 1];
          downloadLink.href = linkSource;
          downloadLink.download = fileName;    
          downloadLink.click();
        }
      }
    },
    async DSPhieuFocus(row) {
      try{
        this.loading(true)
        this.params.quyetToanId = 0
        if(row){
          this.params.maPhieu = row.MA
          this.params.quyetToanId = row.QUYETTOAN_ID == "" || row.QUYETTOAN_ID == null ? 0 : row.QUYETTOAN_ID
          this.Enabled.inPhieu = this.params.quyetToanId != 0
          this.dataSource.DSChiTietPhieu = await this.getDSChiTietPhieu({
            id: row.ID,
            loaiPhieuId: row.LOAIPHIEU_ID
          })
          
          this.dataSource.DSFile = []
          if(row.MOTA != null && row.MOTA != ""){
            var ChiTietFile = JSON.parse(row.MOTA);
            if(ChiTietFile.length > 0){
              var urlSplit = ChiTietFile[0].url.split("/")
              this.dataSource.DSFile.push({
                fileName:urlSplit[urlSplit.length - 1],
                urlFile: ChiTietFile[0].url
              })
            }      
          }          
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    onClickNhapMoi(){
      this.params.maPhieu = ""
      this.params.loaiPhieuId = "0"
      this.params.denNgay = new Date()
      var currentDate = new Date()
      currentDate.setMonth(currentDate.getMonth() - 1)
      this.params.tuNgay = new Date(currentDate)
      this.dataSource.DSFile = []
      this.dataSource.DSPhieu = []
      this.dataSource.DSChiTietPhieu = []
    },
    async openFrmNhanVien(item){
      this.dataSource.DSChiTietNhanVien = await this.getDSNhanVienTheoPhieu(item.PHIEU_ID) 
      this.$refs.popupDSNhanVien.show();   
    },
    async onClickPhieuTongHop(){
      try{
        this.loading(true)
        const baseUrl = process.env.API;
          await this.axios({
            url:
              `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-de-nghi-thanh-qt-vt?extend=pdf&quyetToanId=${this.params.quyetToanId}`,
            method: "POST",
            responseType: "blob",
          }).then((response) => {
            previewPrint(response.data);
          });
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async onClickPhieuChiTiet(){
      try{
        this.loading(true)
        const baseUrl = process.env.API;
          await this.axios({
            url:
              `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-tong-hop-qt-vt-theo-dv?extend=pdf&quyetToanId=${this.params.quyetToanId}`,
            method: "POST",
            responseType: "blob",
          }).then((response) => {
            previewPrint(response.data);
          });
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
  },
  created: async function () {
    EventBus.$on('openFrmNhanVien', this.openFrmNhanVien);
    try{
      this.loading(true)
      let so_phieu = this.$route.query.so_phieu 
      this.params.maPhieu = this.$route.query.phieu_de_nghi != null ? this.$route.query.phieu_de_nghi : ""
      this.params.loaiPhieuId = this.$route.query.loaidn_id != null ? this.$route.query.loaidn_id : "0"
    
      this.dataSource.DSLoaiPhieu = await this.getDSLoaiPhieu({    
        nhanVienId: 0,
        kieu: 5,
        param1: "",
        param2: ""
      })
      // Từ ngày bằng ngày hiện tại trừ lui 1 tháng
      var currentDate = new Date()
      currentDate.setMonth(currentDate.getMonth() - 1)
      this.params.tuNgay = new Date(currentDate)      
      await this.onClickTimKiem()
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false)
    }
  },
  destroyed(){
    EventBus.$off('openFrmNhanVien', this.openFrmNhanVien);
  },
  
};
</script>