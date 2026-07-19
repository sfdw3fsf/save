<template src="./index.html"></template>
<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import api from "./api/index.js";
import state from "./define.js";
import { required, minValue, maxValue, maxLength,minLength,numeric } from 'vuelidate/lib/validators'

export default {
  components: { 
    moment,
    breadcrumb, 
    gridview,        
  },
  name: "",
  validations: {          
  },
  data() {
    return {
      header: {
        title: "DANH MỤC LOẠI NHÀ",
        list: [
          { name: "Danh mục", link: { name: "Ui.cards" } },
          {
            name: "Danh mục loại nhà",
            link: { name: "Ui.buttons" },
          },
        ],
      },      
      ...state
      
    };
  },
  created() {     
    this.init()
    this.layDS()

  },
  mounted() {
    
  },
  computed: {},
  watch: {     
  },
  methods: {
    async init(){
      try {        
        this.SetButton(1)
        await api.sp_nhom_loainha_lay_ds(this.axios).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.options.cbo_nhomln = response.data.data;    
            this.formTT.nhomln_id = response.data.data[0].nhomln_id

          }
        });
        this.may_cn = await this.$root.token.getMachine()
        this.ip_cn = await this.$root.token.getIP()
        console.log(this.may_cn)
        
      } catch (error) {
        console.log(error)        
      }
    },    
    async layDS(){
      try {        
        
        await api.sp_loainha_lay_ds(this.axios).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.gvDanhSach.tableData = response.data.data;
            for(var i in this.gvDanhSach.tableData) this.gvDanhSach.tableData[i].stt=(parseInt(i)+1).toString();
          }
        });
      } catch (error) {
        console.log(error)        
      }
    },    
    selectItem(item){
      console.log(item.data)
      
      if (!item || !item.data) return
      this.SetButton(3)
      let formdata = item.data            
      this.formTT.loainha_id = formdata.loainha_id
      this.formTT.nhomln_id = formdata.nhomln_id
      this.formTT.ma_loainha = formdata.ma_loainha      
      this.formTT.ten_loainha = formdata.ten_loainha
      this.formTT.sotang_tu = formdata.sotang_tu
      this.formTT.sotang_den = formdata.sotang_den
      this.formTT.dientich_tu = formdata.dientich_tu
      this.formTT.dientich_den = formdata.dientich_den
      this.formTT.sophong_tu = formdata.sophong_tu
      this.formTT.sophong_den = formdata.sophong_den
    },
    
    
    async btnGhilai_Click(){      
      console.log('click ghi lại')
      if (!this.KiemTraDL()) return false;
      console.log(this.formTT.loainha_id)
      //return;

      let isNew = (this.formTT.loainha_id==0)
      console.log(isNew)
      if (isNew)
        this.capnhat(1)
      else this.capnhat(2)
      
    },
    async capnhat(loai){      
      console.log('click ghi lại')
      
      this.loading(true)
      try
      {
        
        const p_data = {
            "loainha_id":this.formTT.loainha_id,
            "nhomln_id":this.formTT.nhomln_id,
            "ma_loainha":this.formTT.ma_loainha,
            "ten_loainha":this.formTT.ten_loainha,
            "sotang_tu":this.formTT.sotang_tu,
            "sotang_den":this.formTT.sotang_den,
            "dientich_tu":this.formTT.dientich_tu,
            "dientich_den":this.formTT.dientich_den,
            "sophong_tu":this.formTT.sophong_tu,
            "sophong_den":this.formTT.sophong_den,
            "nguoi_cn":this.may_cn,
            "may_cn":this.may_cn,
            "ip_cn":this.ip_cn
        }
        let input = {
          "p_type": loai,
          "p_data": JSON.stringify(p_data)
        }
        await api.fn_loainha_thucthi(this.axios,input).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.$toast.success("Cập nhật thông tin thành công.")
            this.layDS()
            this.loading(false)
          }
          else if (response &&response.data &&response.data.message) {          
            this.$toast.error(response.data.message)
            this.loading(false)
          }
          else {
            this.$toast.error("Cập nhật thông tin thất bại.")
            this.loading(false)
          }
        });

        this.loading(false)
      }
      catch (ex)
      {
          this.loading(false)
          this.$toast.error("Có lỗi khi cập nhật thông tin!"+ex)          
      }

    },
    
    async btnXoa_Click(){      
      console.log('click xóa loại nhà')
      if (this.formTT.loainha_id == 0) {
        this.$toast.error("Vui lòng chọn dòng cần xóa!")   
        return;
      }
      if (!window.confirm("Bạn thật sự muốn xóa loại nhà không ?")) {
        return
      }
      this.capnhat(3)

    },    
    btnLayTT_Click(){      
      console.log('click lấy TT')
      this.SetButton(1)
      this.layDS()
      
    },
    onActionClick (action) {
      if (action.id === 'nhapmoi') {
        this.btnThemmoi_Click()
      } else if (action.id === 'ghilai') {
        this.btnGhilai_Click()
      } else if (action.id === 'xoa') {
        this.btnXoa_Click()
      } else if (action.id === 'laytt') {
        this.btnLayTT_Click()
      } 
    },
    setActiveButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }      
    },
    btnThemmoi_Click(){      
      console.log('btnThemmoi_Click')
      this.SetButton(1)
      
    },
    SetButton(kieu){
      this.setActiveButton('nhapmoi',false)
      this.setActiveButton('ghilai',false)
      this.setActiveButton('xoahd',false)
      this.giaophieu_active=false      
      if (kieu == -1)//Bat dau
      {          
          this.setActiveButton('ghilai',true)
      }
      else if (kieu == 0)//Bat dau
      {
          this.is_enable_nhapmoi = true
          this.setActiveButton('nhapmoi',true)                    
          this.ClearGUI()
      }
      else if (kieu == 1)//Them moi
      {
          $('#ma_hd').focus() 
          this.setActiveButton('ghilai',true)
          this.ClearGUI()
      }
      else if (kieu == 2)//Huy
      {
          this.is_enable_nhapmoi = true
          this.setActiveButton('nhapmoi',true)
          this.ClearGUI()
      }
      else if (kieu == 3)//Edit
      {
          this.is_enable_nhapmoi = true
          this.setActiveButton('nhapmoi',true)
          this.setActiveButton('ghilai',true)
          this.setActiveButton('xoa',true)
          this.giaophieu_active=true
      }
      
    },
    KiemTraDL(){
        console.log(this.formTT)
        console.log('kiemtra dl')
        if (this.formTT.ma_loainha == null || this.formTT.ma_loainha =="")
        {
            this.$toast.error("Hãy nhập mã loại nhà!")
            $('#ma_loainha').focus()            
            return false
        }

        if (this.formTT.ten_loainha == null || this.formTT.ten_loainha =="")
        {
            this.$toast.error("Hãy nhập tên loại nhà!")
            $('#ten_loainha').focus()            
            return false
        }
        if (parseInt(this.formTT.dientich_tu) > parseInt(this.formTT.dientich_den) )
        {
            this.$toast.error("Diện tích đến không được nhỏ hơn diện tích từ!")                      
            return false
        }
        if (parseInt(this.formTT.sotang_tu) > parseInt(this.formTT.sotang_den) )
        {
            this.$toast.error("Số tầng đến không được nhỏ hơn số tầng từ!")                      
            return false
        }
        if (parseInt(this.formTT.sophong_tu) > parseInt(this.formTT.sophong_den) )
        {
            this.$toast.error("Số phòng đến không được nhỏ hơn số phòng từ!")                      
            return false
        }
        if (this.formTT.nhomln_id == 0 )
        {
            this.$toast.error("Hãy chọn nhóm loại nhà!")                      
            return false
        }
        return true;
    }, 
    
    ClearGUI(){
      this.formTT = {
        loainha_id:0,
        ma_loainha:"",
        nhomln_id:0,
        ten_loainha:"",
        sotang_tu:1,
        sotang_den:1,
        dientich_tu:1,
        dientich_den:1,
        sophong_tu:1,
        sophong_den:1   
      }
    
    },
  },
};
</script>
<style scoped>
  .input-icon-right.disabled input{
    cursor: default;
    pointer-events: none;
    text-decoration: none!important;
    color: grey!important;
  }
</style>