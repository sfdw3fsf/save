<template src='./LoaiVT.html'></template>
<style scoped src='./LoaiVT.scss'></style>
<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from '@/components/breadcrumb'
import { getterName,actionName,statePropertyName,mutationName} from '../store/loaivt'
import Vue from "vue";

import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query, Predicate } from "@syncfusion/ej2-data";

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb},
  name: "LoaiVT",
  mounted() {
  },
  
  data() {
    return {
      DSLoaiVT : [],
      DSNhomVT: [],
      DSLoaiVTCha: [],
      DS_MD_CHA: [],
      focus_id: -1,
      loaivt_id: 0,
      focus_id : -1,
      params: {
        cboNhomVT: 0,
        txtMaLVT: "",
        txtLoaiVT: "",
        cboLoaiVTcha: null,
      },
      Enabled:{
        tsbtnNhapMoi : false,
        tsbtnGhiLai : false,
        tsbtnXoa : false,
        tsbtnHuyBo : false,
      },
      fields: {
        dataSource: [],
        id: "LOAIVT_ID",
        parentID: "LOAIVT_CHA_ID",
        text: "TEN_LVT",
        hasChildren: "hasChild",
        selected: "isSelected",
      },
      filterVTCha: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("LOAI_VT", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
    };
  },

  computed: {
    ...mapState("loaivt", statePropertyName),
    ...mapGetters("loaivt", getterName),

    
  },
  methods: {
    ...mapActions("loaivt", actionName),
    ...mapMutations("loaivt", mutationName),

    async frmLoaiVT_Load(){
      try{
        this.loading(true)
        this.DSNhomVT = await this.getDSNhomVT()
        this.params.cboNhomVT = this.DSNhomVT.length > 0 ? this.DSNhomVT[0].NHOMVT_ID : 0
        this.setButton(1)
      }catch(e){
        this.$toast.error("Có lỗi xảy ra, không thể lấy dữ liệu!");
      }finally{
        this.loading(false)
      }
    },
    setButton(kieu){
      this.Enabled.tsbtnNhapMoi = false
      this.Enabled.tsbtnGhiLai = false
      this.Enabled.tsbtnXoa = false
      this.Enabled.tsbtnHuyBo = false

      if(kieu == -1) {
        this.$refs.txtMaLVT.focus()
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnHuyBo = true
      }else if(kieu == 0) {
        this.$refs.txtMaLVT.focus()
        this.Enabled.tsbtnNhapMoi = true
        this.Clear()
      }else if(kieu == 1) {
        this.$refs.txtMaLVT.focus()
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnHuyBo = true
        this.Clear()
      }else if(kieu == 2) {
        this.$refs.txtMaLVT.focus()
        this.Enabled.tsbtnNhapMoi = true
        this.Enabled.tsbtnXoa = true
        this.Clear()
      }else if(kieu == 3) {
        this.Enabled.tsbtnNhapMoi = true
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnXoa = true
        this.Enabled.tsbtnHuyBo = true
      }
    },
    Clear(){
      this.params.txtMaLVT = ""
      this.params.txtLoaiVT = ""
      this.params.cboLoaiVTcha = null
    },
    async cboNhomVT_EditValueChanged(){
      try{
        this.loading(true)
        await this.Loai_tree()
        await this.Load_DT_MD_CHA()
        this.DSLoaiVTCha = this.DS_MD_CHA
      }catch(e){
        this.$toast.error("Có lỗi xảy ra, không thể lấy dữ liệu!");
      }finally{
        this.loading(false)
      }      
    },
    async Loai_tree(){
      try{
        this.loading(true)
        this.DSLoaiVT =  await this.getDSLoaiVT(this.params.cboNhomVT)
        this.createTree(this.DSLoaiVT)
        this.fields = {
          dataSource: this.DSLoaiVT,
          id: "LOAIVT_ID",
          parentID: "LOAIVT_CHA_ID",
          text: "TEN_LVT",
          hasChildren: "hasChild",
          selected: "isSelected",
        };
      }catch(e){
        this.$toast.error("Có lỗi xảy ra, không thể lấy dữ liệu!");
      }finally{
        this.loading(false)
      }     
    },
    async Load_DT_MD_CHA(){
      this.DS_MD_CHA = await this.getDSCboLoaiVT({
        loaiVTId: -1,
        nhomVTId: this.params.cboNhomVT
      })
    },
    tsbtnNhapMoi_Click(){
      this.DSLoaiVTCha = this.DS_MD_CHA
      this.setButton(1)
      if(this.focus_id  != -1){
        this.params.cboLoaiVTcha = parseInt(this.focus_id)
      }
    },
    tsbtnHuyBo_Click(){
      this.setButton(0);
      this.DSLoaiVTCha = this.DS_MD_CHA
    },
    async tsbtnXoa_Click(){
      console.log(this.loaivt_id , " = loaivt_id")
      if(this.loaivt_id == 0){
        return
      }

      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có chắc chắn muốn xóa loại vật tư!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return;
      }
      try{

        this.loading(true)
        await this.capNhatLoaiVT({
          nhomVtId: this.params.cboNhomVT,
          maLoaiVt: this.params.txtMaLVT,
          loaiVt: this.params.txtLoaiVT,
          kieu: 2,
          loaiVtId: this.loaivt_id,
          loaiVtChaId: 0
        })
        this.$toast.success("Xóa loại vật tư thành công!")
        this.focus_id = -1
        this.Clear()
        this.Loai_tree();   
        this.setButton(0)     
      }catch(e){
        this.$toast.error("Đã có lỗi xảy ra, không thể xóa vật tư!");
      }finally{
        this.loading(false)
      }      
    },
    async tsbtnGhiLai_Click(){
      if(this.params.txtLoaiVT.trim() == ""){
        this.$toast.error("Chưa nhập tên loại vật tư!");
        this.$refs.txtLoaiVT.focus()
        return;
      }
      if(this.params.txtMaLVT.trim() == ""){
        this.$toast.error("Chưa nhập mã vật tư!");
        this.$refs.txtMaLVT.focus()
        return;
      }
      if(this.params.cboNhomVT == 0){
        this.$toast.error("Chưa chọn nhóm vật tư!");        
        return;
      }
   
      try{
        this.loading(true)
        if(this.Enabled.tsbtnNhapMoi ==  false){
          for(const e of this.DSLoaiVT){
            if(e.MA_LVT == this.params.txtMaLVT.trim()){
              this.$toast.error("Mã vật tư đã tồn tại, vui lòng nhập mã khác!");
              this.$refs.txtMaLVT.focus()
              return;
            }
          }
          this.focus_id = await this.capNhatLoaiVT({
            nhomVtId: this.params.cboNhomVT,
            maLoaiVt: this.params.txtMaLVT,
            loaiVt: this.params.txtLoaiVT,
            kieu: 1,
            loaiVtId: 0,
            loaiVtChaId: this.params.cboLoaiVTcha
          })
          await this.Load_DT_MD_CHA()
          await this.Loai_tree()
          this.setButton(3)
          /// focus node MA_LVT
          /////////////////////
          this.$toast.success("Thêm mới loại vật tư thành công!")
        }else{
          this.focus_id  = await this.capNhatLoaiVT({
            nhomVtId: this.params.cboNhomVT,
            maLoaiVt: this.params.txtMaLVT,
            loaiVt: this.params.txtLoaiVT,
            kieu: 0,
            loaiVtId: this.loaivt_id,
            loaiVtChaId: this.params.cboLoaiVTcha
          })
          await this.Load_DT_MD_CHA()
          await this.Loai_tree()
          this.setButton(3)        
          this.$toast.success("Cập nhật loại vật tư thành công!")
        }
      }catch(e){
        this.$toast.error("Đã có lỗi xảy ra, không thể ghi lại!");
      }finally{
        this.loading(false)
      }
    },
    async onLoaiVTSelected(data) {
     
      let treeObj = document.getElementById("treeview").ej2_instances[0];
      this.focus_id = treeObj.selectedNodes[0];
      if(this.Enabled.tsbtnNhapMoi == false){   
        this.params.cboLoaiVTcha = this.focus_id == -1 ? null : parseInt(this.focus_id)
      }else{
        if(this.DSLoaiVT.length == 0){// trường hợp này chắc chắn k vào, code theo C# cho zui
          this.setButton(0)
          this.DSLoaiVTCha = this.DS_MD_CHA
          this.focus_id = -1
          return
        }
        if(this.focus_id == -1){// node gốc
          this.setButton(0)
          this.DSLoaiVTCha = this.DS_MD_CHA
          this.focus_id = -1
          return
        }
        this.loaivt_id = this.focus_id
        this.DSLoaiVTCha = await this.getDSCboLoaiVT({
          loaiVTId: this.loaivt_id,
          nhomVTId: this.params.cboNhomVT
        })
        this.treeviewSelect()
      }
    },
    treeviewSelect(){
      console.log("treeviewSelect")
      this.setButton(3)
      var focus_id = this.focus_id
      var noteFocus = this.DSLoaiVT.filter(
        function (el) {
          return el.LOAIVT_ID == focus_id;
        }
      );
      if(noteFocus.length > 0){
        this.params.txtLoaiVT = noteFocus[0].LOAI_VT
        this.params.txtMaLVT = noteFocus[0].MA_LVT
        this.params.cboLoaiVTcha = parseInt(noteFocus[0].LOAIVT_CHA_ID)
      }
    },
    findLoaiVTCha(dataTree,loaiVTFocusId){
     
      var isExistParrent = true
      var listIdCha = []
      while(isExistParrent){
        var loaiVTFocus = dataTree.filter(function (el) {
          return el.LOAIVT_ID == loaiVTFocusId;
        });          
        
        if(loaiVTFocus.length > 0 && 'LOAIVT_CHA_ID' in loaiVTFocus[0] ){         
          listIdCha.push(loaiVTFocus[0].LOAIVT_CHA_ID )  
          loaiVTFocusId =  loaiVTFocus[0].LOAIVT_CHA_ID              
        }

        if(loaiVTFocus.length == 0){
          isExistParrent = false          
        }        
      }
      return listIdCha
    },
    createTree(dataTree) {
      this.loaivt_id = this.focus_id
      var loaiVTFocusId = this.focus_id       
      var listVTIdCha = this.findLoaiVTCha(dataTree,loaiVTFocusId)
    
      for (const i in dataTree) {
        
        var LOAIVT_ID = dataTree[i].LOAIVT_ID;
        var LOAIVT_CHA_ID = dataTree[i].LOAIVT_CHA_ID;

        var arrChild = dataTree.filter(function (el) {
          return el.LOAIVT_CHA_ID == LOAIVT_ID;
        });
        
        if (arrChild.length > 0) {
          dataTree[i].hasChild = true;
        }
        
        // expanded vào root và list VT Cha
        dataTree[i].expanded = LOAIVT_ID == -1 || listVTIdCha.includes(LOAIVT_ID) ? true : false;
        dataTree[i].isSelected = LOAIVT_ID == loaiVTFocusId  ? true : false;
       
        // remove LOAIVT_CHA_ID
        var arrParrent = dataTree.filter(function (el) {
          return el.LOAIVT_ID == LOAIVT_CHA_ID;
        });
        if (arrParrent.length == 0) {
          delete dataTree[i].LOAIVT_CHA_ID;
        }
      }
    },
    
  },
  created: async function() {       
    this.loading(true)
    await this.frmLoaiVT_Load()
    this.loading(false)
  }
 
};
</script>