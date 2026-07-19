<template src='./XetDuyetCongCuBaoHo.html'></template>
<style scoped src='./XetDuyetCongCuBaoHo.scss'></style>
<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import breadcrumb from '@/components/breadcrumb'
import { getterName,actionName,statePropertyName,mutationName} from '../store/xetduyetcongcubaoho'
import select2 from '@/components/Select2.vue'
import { LOAI_DV,KETQUA_XL,SERVER_IMAGES} from "@/const/enums"
import Vue from "vue";


import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query,Predicate } from '@syncfusion/ej2-data';
// import { Cropper } from 'vue-advanced-cropper';
// import 'vue-advanced-cropper/dist/style.css';
import { TooltipPlugin } from "@syncfusion/ej2-vue-popups";
import { Tooltip } from '@syncfusion/ej2-popups';
Vue.use(TooltipPlugin);
Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb, appSelect2: select2 },
  name: "XetDuyetCongCuBaoHo",
  mounted() {
  },
  
  data() {
    return {
      header: {
          title: "XÉT DUYỆT CÔNG CỤ BẢO HỘ",
          list: [
            {
              name: "Quản lí vật tư",
              link: { name: "Ui.buttons" },
            },
            {
              name: "Xét duyệt công cụ bảo hộ",
              link: { name: "Ui.buttons" },
            },   
          ],
      },

      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",        
        waterMark: "Chọn ngày",
      },
      params:{
        tuNgay: new Date(),
        denNgay: new Date(),
        trangThai: -1,
        donViId:-1,
        phieuId:0,
        ghiChu: "Nhập ghi chú",
        isDayDu: false
      },
      dataResponse:{
        DSTramThiCong: [],
        DSTiepNhan: [],
        DSThamSoMacDinh : [],
        DSChiTietCCDC : [],
      },
      isHiddenPreview: false,
      batBuocChupAnh: false,
      daChupAnh : false,
      isDuyet: true,
      rowTiepNhanFocus: [],
      checkboxCo: [],
      checkboxDayDu:[],
      disabledInput:{
        duyet:false,
        khongDuyet:false,
      },
      
      DSTrangThai:[
        {
          trangThaiId:-1,          
          trangThai: "Chờ duyệt",
        },
        {
          trangThaiId:KETQUA_XL.DUOC_DUYET,
          trangThai: "Đã duyệt",
        },
        {
          trangThaiId:KETQUA_XL.KO_DUOC_DUYET,
          trangThai: "Không được duyệt",
        }  
      ],
      thongTinNguoiDung: {
        ngayCN: new Date(), //this.$auth.ngayCN
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung
        mayCN: "", //this.$auth.MAYCN
        ipCN: "", //this.$auth.IPCN
        nhanVienId: this.$root.token.getNhanVienID(), //this.$root.token.getNhanVienID()
        donviId: this.$root.token.getDonViID(),
        maNhanVien:this.$root.token.getMaNhanVien(),
        maPhanVung: this.$root.token.getMaTinh(),
      },
      groupTiepNhan: { showDropArea: false, columns: ["TEN_CA"] },
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },       
      pageSettings: { pageCount: 5 },
      editSettings: { allowEditing: true, mode:'Batch'},
      cssImg:{
        widthImg:50,
        widthMainImg: 100,
        scale:1,
        rotate : 0,
      },
      dataImg: "",
      isCropView: false,
      urlImg: SERVER_IMAGES.NO_IMAGE_URL,
      pathImg: "",
      nameImg: "",
      listImg : [],
      listWidth: [25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400],
      columnsCheckboxCo: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.checkboxCo"
                                    :value="data.LOAIVT_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: { }
              }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              },
            }
          }
        }
      },

      columnsCheckboxDayDu: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.checkboxDayDu"
                                    :value="data.LOAIVT_ID" 
                                    style="pointer-events: none;"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: { }
              }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              },
            }
          }
        }
      },
      filterDonVi: function(e) {
        var query = new Query();
        let predicate;  
        
        if (e.text !== ''){
          predicate  = new Predicate('TEN_DV', 'contains', e.text.trim(), true);              
          query.where(predicate)
        }
        e.updateData(this.dataSource, query);
      },
      tooltip: Tooltip
    };
  },

  computed: {
    ...mapState("xetduyetcongcubaoho", statePropertyName),
    ...mapGetters("xetduyetcongcubaoho", getterName),

    
  },
  methods: {
    ...mapActions("xetduyetcongcubaoho", actionName),
    ...mapMutations("xetduyetcongcubaoho", mutationName),

    onClose: function(e) {
        this.tooltip.close();
    },
    onBeforeRender : function(args) {
      var result = this.$refs.cboTuKho.dataSource;      
      var i;        
      for (i = 0; i < result.length; i++) {        
        if (result[i].TEN_DV === args.target.textContent) {
          this.tooltip.content = result[i].TEN_DV;
          this.tooltip.dataBind();
          break;
        }
      }
    },
    onDropdownCreate:  function(args) {   
      this.tooltip = new Tooltip({
          content: 'Loading...',      
          target: '.e-list-item',     
          position: 'top center',     
          beforeRender: this.onBeforeRender
      });
      this.tooltip.appendTo('body');
    },

    async testCrop({ coordinates, canvas }){
      console.log(coordinates, canvas);
    },
    async cropView(){
      this.isCropView = !this.isCropView
      if(!this.isCropView){
        document.getElementById("ImgPreview1").src = this.dataImg;        
      }
    },
    async downloadImg() {
      const filePath= this.urlImg.split('/');
      const linkSource = this.dataImg;
      const downloadLink = document.createElement("a");
      const fileName = filePath[filePath.length - 1];
      downloadLink.href = linkSource;
      downloadLink.download = fileName;    
      downloadLink.click();
    },

    scalePlus(){
      // this.cssImg.scale += 0.25;
      this.cssImg.widthMainImg += 25
    },
    scaleNone(){
      // this.cssImg.scale = 1;
      this.cssImg.widthMainImg = 100
    },
    scaleMinus(){    
      // if(this.cssImg.scale >= 0.5){
      //   this.cssImg.scale -= 0.25;
      // }    
      
      if(this.cssImg.widthMainImg >= 50){
        this.cssImg.widthMainImg -= 25       
      }  
    },
    rotateLeft(){
      this.cssImg.rotate -= 90;
    },
    rotateRight(){
      this.cssImg.rotate += 90;
    },
    async loadDSTramThiCong(){
      this.dataResponse.DSTramThiCong = await this.getDSDonViTramThiCong({
        donViId:0,
        nhanVienId:this.thongTinNguoiDung.nhanVienId,
        loaiDvIdArr:LOAI_DV.TRAM_VT,
      });      
      if(this.dataResponse.DSTramThiCong.length > 1){
        this.dataResponse.DSTramThiCong.unshift({DONVI_ID:-1,TEN_DV:"--Tất cả--"})
      } 
    },

    kiemTraDKDuyet(){
      if(this.batBuocChupAnh && !this.daChupAnh){
        this.$toast.error("Chưa có ảnh chụp. Không thể duyệt.");
        return false
      }
      if(this.dataResponse.DSChiTietCCDC.length == 0){
        this.$toast.error("Chưa có công cụ dụng cụ cần có. Không thể duyệt.");
        return false
      }
      for(const i in this.dataResponse.DSChiTietCCDC){
        if(this.dataResponse.DSChiTietCCDC[i].BATBUOC == "1" && this.dataResponse.DSChiTietCCDC[i].DAY_DU == "0"){
          this.$toast.error(this.dataResponse.DSChiTietCCDC[i].LOAI_VT + " bắt buộc phải đầy đủ! Không thể duyệt.");
          return false
        }
      }
      return true
    },

    onClickDuyet(){
      this.isDuyet = true;
      this.$bvModal.show("popupTCTTTB");      
    },

    onClickXacNhanGhiChu(){
      try{
        this.$bvModal.hide("popupTCTTTB");     
    
        if(this.params.ghiChu.length > 500){
          this.$toast.error("Độ dài ghi chú k được vượt quá 500 ký tự!")
          return
        }
        
        if(this.isDuyet){
          if(!this.kiemTraDKDuyet()){
            return;
          }
          this.xetDuyet(KETQUA_XL.DUOC_DUYET);
        }else{
          this.xetDuyet(KETQUA_XL.KO_DUOC_DUYET);
        }
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    onClickHuyDuyet(){     
      this.isDuyet = false;
      this.$bvModal.show("popupTCTTTB");        
    },

    async onClickSaveGhiChu(){
      await this.capNhatGhiChuCTPCC();
      if(this.dataResponse.DSTiepNhan.length > 0){        
        this.dataResponse.DSChiTietCCDC = await this.getDSChiTietCCDC(this.params.phieuId);
        this.checkboxDayDu = []
        this.checkboxCo = []
        for(const i in this.dataResponse.DSChiTietCCDC){
          var e = this.dataResponse.DSChiTietCCDC[i]
          if( e.BATBUOC == "1"){
            this.checkboxDayDu.push(e.LOAIVT_ID)
          }
          if(e.DAY_DU == "1"){
            this.checkboxCo.push(e.LOAIVT_ID)
          }
        }
        console.log(this.dataResponse.DSChiTietCCDC , " = DSChiTietCCDC")
      }  
    },

    async xetDuyet(trangThai){      
    
      if(this.params.ghiChu == "Nhập ghi chú" || this.params.ghiChu == null){
        this.$toast.error("Bạn chưa nhập ghi chú duyệt");
        return
      }

      this.loading(true);
      await this.duyetPhieuCapCCDC({
        phieuId: this.params.phieuId,
        ghiChu:this.params.ghiChu,
        kqxlId:trangThai,
        nhanVienId: this.thongTinNguoiDung.nhanVienId,
        maNd:this.thongTinNguoiDung.nguoiCN,
        mayCn:this.thongTinNguoiDung.mayCN,
        loaiVatTuId:0
      });
   
      await this.capNhatGhiChuCTPCC();
      this.$toast.success("Cập nhật thành công");
      this.loadDSTiepNhan();
      this.loading(false);
    },

    async capNhatGhiChuCTPCC(){
      var dataRequest = []
      for(const i in this.dataResponse.DSChiTietCCDC){       
        dataRequest.push({
          phieuId: this.dataResponse.DSChiTietCCDC[i].PHIEU_ID,
          ghiChu: this.dataResponse.DSChiTietCCDC[i].GHICHU,
          loaiVatTuId: this.dataResponse.DSChiTietCCDC[i].LOAIVT_ID,
        });
      }
      await this.capNhatGhiChu({listGhiChuPhieu:dataRequest});
    },

  
    async PhieuSelected(row){          
      this.loading(true);
      if(row){     
        this.params.phieuId  = row.PHIEU_ID;   
        await this.getAnhChup(row.MOTA); 
        this.dataResponse.DSChiTietCCDC = await this.getDSChiTietCCDC(row.PHIEU_ID);
        this.checkboxDayDu = []
        this.checkboxCo = []
        this.params.isDayDu = false
        for(const i in this.dataResponse.DSChiTietCCDC){
          var e = this.dataResponse.DSChiTietCCDC[i]
          if( e.BATBUOC == "1"){
            this.checkboxDayDu.push(e.LOAIVT_ID)
          }
          if(e.DAY_DU == "1"){            
            this.checkboxCo.push(e.LOAIVT_ID)
          }
        }
      }      
      this.loading(false);
    },

    async onChangeTrangThai(){
      if(this.params.trangThai == -1){
        this.disabledInput.duyet = false,
        this.disabledInput.khongDuyet = false
      }else if (this.params.trangThai == KETQUA_XL.DUOC_DUYET){
        this.disabledInput.duyet = true,
        this.disabledInput.khongDuyet = false
      }
      else if (this.params.trangThai == KETQUA_XL.KO_DUOC_DUYET){
        this.disabledInput.duyet = false,
        this.disabledInput.khongDuyet = true
      }
      this.loading(true);
      this.loadDSTiepNhan();
      this.loading(false);
    },

    async onClickTraCuu(){
      this.loading(true);
      this.loadDSTiepNhan();
      this.loading(false);
    },

    async loadDSTiepNhan(){
      
      this.dataResponse.DSTiepNhan = await this.getDSTiepNhan({
        listDonViId: this.params.donViId,
        nhanVienId:this.thongTinNguoiDung.nhanVienId,
        kqxlId: this.params.trangThai,
        tuNgay: moment(this.params.tuNgay).format("DD/MM/yyyy"),
        denNgay: moment(this.params.denNgay).format("DD/MM/yyyy"),
      });      
      
    },
    
    async getAnhChup(xmlDoc){
      this.listImg = []
    
      const parser = new DOMParser();
      const dom = parser.parseFromString(xmlDoc, "application/xml");

      var imgTag = dom.getElementsByTagName('img');
      for (var i = 0; i < imgTag.length; i++) {   
        this.listImg.push(imgTag[i].getAttribute('url'))          
      }
      // for (var i = 0; i < imgTag.length; i++) {   
      //   this.listImg.push({
      //     url: SERVER_IMAGES.BASE_URL + this.thongTinNguoiDung.maPhanVung + "/ccdc/"  + imgTag[i].getAttribute('url'),
      //     title:imgTag[i].getAttribute('title'),
      //   })          
      // }

      // this.urlImg = SERVER_IMAGES.NO_IMAGE_URL;
      // this.daChupAnh = false;
      // //Kiểm tra IMG có tồn tại
      // for(const i in this.listImg){             
      //   if(this.listImg[i].url.includes(".png") || this.listImg[i].url.includes(".jpg") || this.listImg[i].url.includes(".jpeg")){ // url định dạng ảnh
      //     var checkImage = await this.checkCodeUrl({url:this.listImg[i].url});
      //     console.log(checkImage ," = checkImage")
      //     this.daChupAnh = checkImage != 200
      //     if(checkImage == 200){   
      //       this.urlImg = this.listImg[i].url
      //       break
      //     }   
      //   } 
      // }
      // console.log(this.urlImg , " = this.urlImg")
      const data = await this.downloadImageFromUrl({
        urlIMG: this.listImg       
      })
      console.log(data , " = data")
      if(data){       
        this.daChupAnh = true; 
        this.dataImg = "data:image/png;base64," + data.data.body.data;
        // document.getElementById("ImgPreview1").src = this.dataImg;
        // document.getElementById("ImgPreview2").src = this.dataImg;
        // document.getElementById("ImgPreview3").src = this.dataImg;
      }
      
    },
    
    async onClickDayDu(){
      this.loading(true)
      this.params.isDayDu = !this.params.isDayDu  
      this.checkboxCo = []
      for(const i in this.dataResponse.DSChiTietCCDC){
        var e = this.dataResponse.DSChiTietCCDC[i]       
        e.DAY_DU = this.params.isDayDu ? "1" : "0" 
        if(this.params.isDayDu){
          this.checkboxCo.push(e.LOAIVT_ID)
        }           
      }
      
      this.$refs.gridChiTiet.grid.refreshColumns()
      this.loading(false)
    },
    async editGridChiTiet(arg){ 
      let gridChiTiet = this.$refs.gridChiTiet
      let changes = gridChiTiet.getBatchChanges()
      if(changes.changedRecords.length == 0){
        return
      } 
      gridChiTiet.batchUpdate(changes)    
    },
    hiddenPreview(){
      this.isHiddenPreview = !this.isHiddenPreview
    },

    
  },
  watch:{
    "checkboxCo": async function(value){    
      this.loading(true)  
      this.checkboxCo = value
      var dataRequest = []
      for(const i in this.dataResponse.DSChiTietCCDC){
        var e = this.dataResponse.DSChiTietCCDC[i]        
        if( this.checkboxCo.includes(e.LOAIVT_ID)){
          e.DAY_DU = "1"        
        }else{
          e.DAY_DU = "0"
        }

        dataRequest.push({
          phieuId: e.PHIEU_ID,
          kqxlId: e.DAY_DU == "1" ? KETQUA_XL.DAY_DU : KETQUA_XL.KO_DAY_DU,
          loaiVatTuId: e.LOAIVT_ID,
        });
      }
      await this.capNhatChiTietDungCu({listTrangThaiPhieu:dataRequest});
      this.loading(false) 
    },
  },
  created: async function() {       
    
    try{
      this.loading(true);
      await this.loadDSTramThiCong();
      await this.loadDSTiepNhan();  
      await this.getAnhChup("")  
      this.dataResponse.DSThamSoMacDinh = await this.getDSThamSoMacDinh(-1);  

      for(const i in this.dataResponse.DSThamSoMacDinh){
        if(this.dataResponse.DSThamSoMacDinh[i].maTS == "BATBUOC_CHUPANH_CCDC" && this.dataResponse.DSThamSoMacDinh[i].tenTS == "1"){
          this.batBuocChupAnh = true       
          break
        }
      }
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
       this.loading(false);
    }
    
   
  },
 
};
</script>