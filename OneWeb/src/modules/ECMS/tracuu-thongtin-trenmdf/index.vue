<template src="./index.template.html"></template>
<script>
  import { Group } from "@syncfusion/ej2-vue-grids";
  import Modal from './Modal'
  let initial = true;
export default {
  components: {Modal},
  props:{
    ma_thuebao:''
  },
  data() {
    return {
      ok:true,
      isError:false,
      dsCboTongDai: [],
      dsThuebaoPhiendoc: [],
      cboTongDai: 0,
      checkrdo : 'rdoMDF',
      somayAcc:'',
      kyhieuPD:'',
      shJumper:'',
      thuebao_id: 0,
      groupOptions: { columns: ['SOI'] },
      isDisableBtnCapNhat: false,
      dsTonghop:{},
      modalShow: false,
      gridDanhSachCount: -1,
      disableText: true,
      selected: -1,
      dataSelected: {
        ma_tb: null,
        soi: null
      }
    };
  },

  created: function () {
    this.getDsTongDai()
    
  },
  methods: {
    initData: function(data){
     this.somayAcc = data
    
     },
    onEnterAcc: function(){
      this.tracuuThongtin()
    },
    onEnterPD: function(){
      this.tracuuThongtin()
    },
    onEnterSH: function(){
      this.tracuuThongtin()
    },
    changeOption () {
      if (this.checkrdo === 'rdoMDF') {
        this.disableText = false
      }
      else if (this.checkrdo === 'rdoPD'){
        this.disableText = true
      }
    },
    getDsTongDai: async function () {
      var rs = await this.$root.context.get("/web-ecms/tracuu/mdf/lay_ds_cbo_tongdai",{loaidv_id:'39,45'});
      this.dsCboTongDai = rs.data;
    },
    resetInput: function(){
      if (this.checkrdo === 'rdoMDF'){
        this.$refs.tongdai.value = '';
        this.$refs.pdoc.value = '';
        this.$refs.jumper.value = '';

        // console.log(this.kyhieuPD)
      }
      else{
        this.$refs.acc.value = '';
      }
    },
    tracuuThongtin: async function(){
      try{
        this.loading(true);
        let kieu = -1;
        if (this.checkrdo === 'rdoMDF'){
          if (this.somayAcc === ''){
            this.$toast.error('Bạn chưa chọn mã thuê bao cần tra cứu !')
            this.$refs.acc.focus()
            this.isError=true
            return
          }
          let ma_tb = this.somayAcc.trim()
          let rs = await this.$root.context.post("/web-ecms/tracuu/mdf/danhsachmdfacc",{ma_tb:ma_tb,donvi_dl_id:0});
          let dsTb = rs.data
          if (dsTb.length > 0){
            if (dsTb.length == 1){
              this.thuebao_id = dsTb[0].THUEBAO_ID
              kieu = 1
            }
            else{
              this.$toast.error('Trường hợp ra tìm được nhiều hơn 1 thông tin thuê bao chưa biết thực hiện!')
              return
              // frmTraCuu_ThueBao frm = new frmTraCuu_ThueBao(ds);
              // frm.ShowDialog();
              // if (frm.chapnhan)
              // {
              //   int idex = frm.index;
              //
              //   vthuebao_id = Convert.ToInt32(ds.Tables[0].Rows[idex]["thuebao_id"].ToString());
              // }
              // else
              //   return;
            }
          }
          else{
            this.$toast.error('Không tìm thấy thông tin thuê bao!')
             return
          }
        }
        else{
         
          if (this.shJumper.trim() === "")
          {
            this.$toast.error('Bạn nhập thiếu thông tin Jumper !')
            this.$refs.jumper.focus()
            this.isError=true
            return
          }
          if (this.kyhieuPD.trim() === "")
          {
            this.$toast.error('Bạn nhập thiếu thông tin phiến dọc !')
            this.$refs.pdoc.focus()
            this.isError=true
            return
          }
          if (this.cboTongDai == null) {
            this.$toast.error('Chưa chọn tổng đài')
            this.$refs.tongdai.focus()
            this.isError=true
            return
          }
          kieu = 2
        }

        let data = {
          vkieu: kieu,
          vthuebao_id: this.thuebao_id,
          vtongdai: this.cboTongDai,
          vkyhieupd: this.kyhieuPD,
          vjumper: this.shJumper
        
        }
        let res = await this.$root.context.post("/web-ecms/tracuu/mdf/danhsachmdfphiendoc",data)
        this.dsThuebaoPhiendoc = res.data
        this.gridDanhSachCount = res.data.length
        if (res.data.length <= 0){
          if (this.checkrdo === 'rdoMDF')
          {
            this.$toast.error('Không tìm thấy thông tin tra cứu MDF theo mã thuê bao!')
            this.loading(false)
            return
          }
          else
          {
            this.$toast.error('Không tìm thấy thông tin tra cứu thuê bao theo phiến dọc!')
            this.loading(false)
            return
          }
        }
        this.loading(false)
      }
      catch (e) {
        this.$toast.error('error '+e.response.data.message_detail)
      }finally {
        this.loading(false);
      }
    },
    gridDanhsach_selectedRowChanged: async  function(data){
      console.log(data)
      if (data !== null) {
        this.isDisableBtnCapNhat = true
        try {
          this.loading(true)
          this.dataSelected = {
            ma_tb: data.MA_TB,
            soi: data.SOI
          }
          
        } catch (error) {
          this.$toast.error(' '+error)
        }
        finally{
          this.loading(false)
        }
       
      }
      
    },
    gridDanhsach_selectedItemsChanged: async function(data){
     console.log(data)
    },
    openTuyenCap: async function () {

      if (this.dataSelected.soi != null && this.dataSelected.ma_tb != null) {
        try {
          this.loading(true)
          let soi = this.dataSelected.soi
          let matb = this.dataSelected.ma_tb
          let dsDando
          let dsTongdai
          let dataCap = await this.$root.context.post('/web-ecms/tracuu/mdf/lay_tt_cap_theo_matb', {vma_tb: matb})
           console.log('lay_tt_cap_theo_matb ', dataCap.data)
          if (dataCap.data.length > 0) {
            let ketcuoi_id = dataCap.data[0].KETCUOI_ID
            let vitri = (soi === 1) ? dataCap.data[0].VITRI : dataCap.data[0].VITRI_2
            let res = await this.$root.context.post('/web-ecms/tracuu/mdf/lay_giando_theodaunoi', {vketcuoi_id: ketcuoi_id, vvitri: vitri})
            console.log('lay_giando_theodaunoi ', res)
            dsDando = res.data
          }
          if (dsDando.length > 0) {
            
            let kiemtra = false
            let capvaoid = 0
            for (let i = 0; i < dsDando.length; i++) {
              console.log('dsDando ', dsDando[i].LOPMC_ID)
              if (dsDando[i].LOPMC_ID === 1) {
                capvaoid = dsDando[i].CAPVAO_ID
                console.log('vcap_id ', capvaoid)
                kiemtra = true
                break
              }
              kiemtra = false
            }

            if (kiemtra) {
              let ddv = dsDando[0].DOIDAYVAO
              
              console.log('vdoidayvao ', ddv)
              let res = await this.$root.context.post('/web-ecms/tracuu/mdf/lay_tt_tongdai_theo_cap', {vcap_id: capvaoid, vdoidayvao: ddv, vkieu: 1})
              console.log('lay_tt_tongdai_theo_cap ', res)
              dsTongdai = res.data
            } else {
              dsTongdai = []
            }

            let index = 0
            dsDando.map(obj => ({STT: index++, ...obj}))
            this.dsTonghop = {
              dsDando: dsDando,
              dsTongdai: dsTongdai,
              matb: matb,
              gridDanhSachCount: dsDando.length + 1,
            }
            console.log('dsTonghop ', this.dsTonghop)
          }
          
          this.modalShow = !this.modalShow
        } catch (e) {
          this.$toast.error(e)
        } finally {
          this.loading(false)
        }
      } else {
        this.$toast.error('Không tìm thấy thông tin tra cứu thuê bao theo phiến dọc !')
        return
      }

    }

  },
  provide: {
    grid: [Group]
  },
  mounted() {
 this.$refs.acc.focus();
  }
};
</script>
<style scoped>
.disabled {
  pointer-events: none;
  color: #0176FF;
  border-left: 1px solid #BAE7FF;opacity: 0.65;
}
</style>

