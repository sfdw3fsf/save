<template src="./DivisionRevenueCrossSell.html"></template>
<script setup>

</script>
<style src="./DivisionRevenueCrossSell.scss"></style>
<style >
.modal-overlay-tu {
  position: fixed !important;
  top: 0 !important;
  left: 0 !important;
  width: 100% !important;
  height: 100% !important;
  background: rgba(0, 0, 0, 0.5) !important;
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
  z-index: 1000 !important; /* Đảm bảo modal có z-index cao */
  background: white;
}
.popup {
  /* background: white; */
  padding: 20px;
  border-radius: 5px;
  position: relative;
}

.close-btn {
  position: absolute;
  top: 15px;
  right: 15px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #333; /* Đặt màu cho nút đóng */
  transition: color 0.3s ease;
}
.confirm-btn {
  background-color: #4caf50;
  color: white;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  border: none;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.popup-buttons {
  display: flex;
  justify-content: center;
  gap: 40px; /* Thêm khoảng cách giữa các nút */
  margin-top: 20px;
}
.modal-content-tu {
  background: white !important;
  padding: 30px !important;
  border-radius: 5px !important;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1) !important;
  text-align: center !important;
  z-index: 1001 !important; /* Cao hơn z-index của overlay để chắc chắn nội dung nằm trên overlay */
}
</style>
<script>
import breadcrumb from '@/components/breadcrumb'
import API from './api'
import KTableCustom from "./components/KTableCustom.vue"
import cacheDM from './cacheDM'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
// thêm 
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
export default {
  name: 'DivisionRevenueCrossSell',
  components: {
    breadcrumb,KTableCustom,KRequiredMarker,SearchContractModal
  },
  provide: {
        multiselect: [CheckBoxSelection]
    },
  data() {
    return {
      danhsach_phathanh:[],
      list_thongtincoche:[],
      list_thongtincoche_edit:[],
      index_currentPage:1,
      check_listcoche:false,
      modalVisible: false,
      rid_ao:0,
      Ma_coche:"",
      check_edit:0,
      coche_id:0,
      index_click:0,
      check_nhapmoi_success:true,
      check_themhang:true,
      // donvi_BH:'',
      phanloai_hd : [{value : "", list :[]},],
      so_congvan : '',
      donvi :[{value : "", list :[]},],
      donvi_BH :[{value : "", list :[]},],
      Sl_thuebao : "",
      loai_HD : [{value : [], list :[]},],
      ma_kh : '',
      ma_sothue : "",
      ten_kh : "",
      diachi_kh : "",
      currentTab: 'tiep_nhan', // tiep_nhan || xu_l
      phieuhd_id: 0,
      donvi_dl_id:0,
      check_phanvung:true,
      donvi_nhan_id: 0,
      popupComponentName: '',
      popupComponentHeader: '',
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {},
      testtt:352,
      hdkh_id:"",
      tt_nd:"",
      phanvung_id:"",
      isModalVisible:false,
      check_1:false,
      allowCheckBox:true,
      list_TT_phanchia:[],
      check_ghilai:null,
      donvibh_id:"",
      Select_coche:"",
      click_check:true,
      donviKey:0,
      congvan_id:0,
      check_nhapmoi:false,
      list_index_select:[],
      list_loaihinh:[],
      phanchia_doanhthu : [],
      gridDanhSachPCDT: [
        // {
        //   ten_dvvt: "test",
        //   loaihinh_tb:" 35234525",
        //   loai_pt: "fewtte",
        //   don_vi: " ewykl",
        //   cong_doan: "4teryetu",
        //   tyle_PCDT:"ewyeru",
        //   dichvuvt_id : 2,
        //   CheckEdit:false,
        // }
      ],
      ds_coche:{
        list:[],
        header :[
        { fieldName: 'ma_coche', headerText: 'Mã cơ chế', allowfiltering: true },
        { fieldName: 'socongvan', headerText: 'Số công văn', allowfiltering: true },
        { fieldName: 'ma_kh', headerText: 'Mã KH', allowfiltering: true },
        { fieldName: 'ngay_cn', headerText: 'Ngày nhập', allowfiltering: true },
        { fieldName: 'nguoi_cn', headerText: 'Người nhập', allowfiltering: true },
        { fieldName: 'ten_dv', headerText: 'Đơn vị bán hàng', allowfiltering: true },
        { fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowfiltering: true },
        { fieldName: 'mst', headerText: 'Mã số thuế', allowfiltering: true },
        { fieldName: 'phanloai_hd', headerText: 'Phân loại hợp đồng', allowfiltering: true },
        { fieldName: 'ds_loaihd', headerText: 'Loại HĐ', allowfiltering: true },
        { fieldName: 'sl_tb', headerText: 'SL thuê bao', allowfiltering: true },

        ]
      },

      columns2: [{
        field: 'ten_dvvt',
        label: 'Dịch vụ',
        allowEdit: true,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
        check_dovi:false,
        check_loaihinh:false,
        check_loaiPT:false,
        check_congdoan:false,
        check_tyle:false,
      },{
        field: 'loaihinh_tb',
        label: 'Loại hình',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
        check_dovi:false,
        check_loaihinh:true,
        check_loaiPT:false,
        check_congdoan:false,
        check_tyle:false,
      },{
        field: 'loai_pt_text',
        label: 'Loại phát triển',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
        check_dovi:false,
        check_loaihinh:false,
        check_loaiPT:true,
        check_congdoan:false,
        check_tyle:false,
      },{
        field: 'ten_dv',
        label: 'Đơn vị',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
        check_dovi:true,
        check_loaihinh:false,
        check_loaiPT:false,
        check_congdoan:false,
        check_tyle:false,
      },
      {
        field: 'congdoan',
        label: 'Công đoạn',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
        check_dovi:false,
        check_loaihinh:false,
        check_loaiPT:false,
        check_congdoan:true,
        check_tyle:false,
      },
      {
        field: 'tyle',
        label: 'Tỷ lệ PCDT(%)',
        allowEdit: false,
        allowFilter: true,
        allowEditDate: false,
        isCheckColumn: false,
        check_dovi:false,
        check_loaihinh:false,
        check_loaiPT:false,
        check_congdoan:false,
        check_tyle:true,
      },
      {
        field: 'thao_tac',
        label: 'Thao tác',
        allow_thaotac:true,
      },
    ],
      listbox:{
        dichvu_vt: {
          list: [],
          value: ''
        },
        donvi: {
          list: [],
          value: ''
        },
        loaihinh:{
          list: [],
          value: ''
        },
        congdoan:{
          list: [],
          value: ''
        },
        loai_pt:{
          list: [],
          value: ''
        },
      }
      ,

    }
  },
  created: async function () {
  },
  mounted :async function() {
    console.log("bắt đầu")
    this.loading(true)
    this.tt_nd = await this.$root.token.getThongTinNguoiDung()
    var token=await this.$root.token
    console.log("token",token)

    this.donvi_dl_id =await this.$root.token.getDonViDuLieuID()
    console.log("getDonViDuLieuID()",this.donvi_dl_id)

    token=await this.$root.token.getDsQuyenChuquan()
    console.log("getDsQuyenChuquan()",token)

    token=await this.$root.token.getUserName()
    console.log("getUserName()",token)

    token=await this.$root.token.getProperty()
    console.log("getProperty()",token)

    this.phanvung_id =this.tt_nd.phanvung_id
    var tutest=await this.$root.token.getDonViID()
    console.log("bắt đầu ",this.tt_nd , this.phanvung_id,tutest)
    await  this.Get_dichvuvt()
    await  this.loai_hd()
    await  this.lay_danhsach_loai_phattrien()
    await  this.lay_danhsach_donvi_phathanh()
    await  this.lay_danhsach_phanloai_hd()
    await  this.lay_danhsach_congdoan()
    await  this.Get_Loaihinh()
    await this.CSS_LOAIHINH_TB()
    //await  this.lay_lay_ds_coche_doanhthu()
    this.check_nhapmoi=true
    
    this.formLoad()
    // this.gridDSTB_rowSelected()
    console.log("kết thúc")
    this.check_1=false
    this.loading(false)
  },
  methods: {
    formLoad :async function (){
      // this.tt_nd = await this.$root.token.getThongTinNguoiDung()
      // var tutest=await this.$root.token.getDonViID()
      // await  this.Get_dichvuvt()
      // await  this.loai_hd()
      // await  this.lay_danhsach_loai_phattrien()
      // await  this.lay_danhsach_donvi_phathanh()
      // await  this.lay_danhsach_phanloai_hd()
      // await  this.lay_danhsach_congdoan()
      // await  this.Get_Loaihinh()
      this.loading(true)
      if(this.check_nhapmoi){
        await  this.lay_lay_ds_coche_doanhthu()
        this.check_nhapmoi=false
      }
      else await this.Get_gridDanhSachPCDT(this.coche_id)
      this.check_ghilai=1
      this.check_edit=0
      this.rid_ao=0
      this.loading(false)
      //await  this.lay_danhsach_donvi_banhang(tutest)
      // await this.tracuu_congvan()
    },



    Click_themhhang: async function(){
      this.allowCheckBox=true
      this.rid_ao=this.rid_ao+1
      if (this.check_themhang){
        var list_LHSelect= this.list_loaihinh.filter(item => item.dichvuvt_id == this.listbox.dichvu_vt.list[0].id);
        this.listbox.loaihinh.list= []
        this.listbox.loaihinh.list= list_LHSelect.map((x) =>{
          return { id: x['loaitb_id'], text: x['loaihinh_tb'] }
        })
        let newObject = { 
          ten_dvvt: this.listbox.dichvu_vt.list[0].text,
          loaitb_id:this.listbox.loaihinh.list[0].id,
          donvi_id:this.listbox.donvi.list[0].id,
          tyle:0,
          loai_pt_text:this.listbox.loai_pt.list[0].text,
          dichvuvt_id: this.listbox.dichvu_vt.list[0].id,
          congdoan_id:this.listbox.congdoan.list[0].id,
          congdoan:this.listbox.congdoan.list[0].text,
          ten_dv:this.listbox.donvi.list[0].text,
          loaihinh_tb:this.listbox.loaihinh.list[0].text,
          CheckEdit:true,
          CheckEdit_donvi:true,
          CheckEdit_loaihinh:true,
          CheckEdit_tyle:true,
          CheckEdit_congdoan:true,
          CheckEdit_loai_PT:true,
          allow_thaotac:true,
          loai_pt : 1,
          rid: this.rid_ao
          }

        console.log("loai_pt : ,",newObject)
        this.$nextTick(() => { this.gridDanhSachPCDT.push(newObject)})
        // this.gridDanhSachPCDT.push(newObject)
        //this.gridDanhSachPCDT[this.gridDanhSachPCDT.length-1].loai_pt=1
        console.log("this.gridDanhSachPCDT.push(newObject)",this.gridDanhSachPCDT)
        if(!this.click_check){
          this.check_ghilai=0
        }
        else this.check_ghilai=1
        this.check_themhang=false
      }
      else {
        var list_PCDT= this.gridDanhSachPCDT
        list_PCDT.forEach(item => {
          item.CheckEdit=false               
          item.CheckEdit_donvi=false
          item.CheckEdit_loaihinh=false
          item.CheckEdit_tyle=false
          item.CheckEdit_congdoan=false
          item.CheckEdit_loai_PT=false     
        });
        var list_LHSelect= this.list_loaihinh.filter(item => item.dichvuvt_id == this.listbox.dichvu_vt.list[0].id);
        this.listbox.loaihinh.list= []
        this.listbox.loaihinh.list= list_LHSelect.map((x) =>{
          return { id: x['loaitb_id'], text: x['loaihinh_tb'] }
        })
        let newObject = { 
          ten_dvvt: this.listbox.dichvu_vt.list[0].text,
          loaitb_id:this.listbox.loaihinh.list[0].id,
          donvi_id:this.listbox.donvi.list[0].id,
          tyle:0,
          loai_pt_text:this.listbox.loai_pt.list[0].text,
          dichvuvt_id: this.listbox.dichvu_vt.list[0].id,
          congdoan_id:this.listbox.congdoan.list[0].id,
          congdoan:this.listbox.congdoan.list[0].text,
          ten_dv:this.listbox.donvi.list[0].text,
          loaihinh_tb:this.listbox.loaihinh.list[0].text,
          CheckEdit:true,
          CheckEdit_donvi:true,
          CheckEdit_loaihinh:true,
          CheckEdit_tyle:true,
          CheckEdit_congdoan:true,
          CheckEdit_loai_PT:true,
          loai_pt : 1,
          allow_thaotac:true,
          rid: this.rid_ao
          }
        this.gridDanhSachPCDT=list_PCDT
        this.gridDanhSachPCDT.push(newObject)
        this.gridDanhSachPCDT[this.gridDanhSachPCDT.length-1].loai_pt=1
        console.log("this.gridDanhSachPCDT.push(newObject)",this.gridDanhSachPCDT)
        if(!this.click_check){
          this.check_ghilai=0
        }
        else this.check_ghilai=1
        
      }
    },
    nhap_moi :function(){
      this.clear_ds()
      this.gridDanhSachPCDT=[]
      this.donvi_BH.value=this.donvi_BH.list[0].id
      this.loai_HD.value = ['1']
      this.lay_danhsach_donvi_banhang(this.donvi_BH.value) 
      this.Click_themhhang()
      this.click_check=false
      // this.check_themhang=true
      this.check_ghilai=0
      this.check_nhapmoi=true
    },
    capnhat_coche_doanhthu:async function(data){
      try {
      let res = await API.capnhat_coche_doanhthu(this.axios, data)
      if (res.data.data && res.data.data.length >0 ){
        if(res.data.data==="OK"){
        this.$toast.success("Cập nhật thành công")
        this.check_nhapmoi_success=true
      }
        else { 
          this.$toast.error(res.data.data) 
          this.check_nhapmoi_success=false
        }
        } 
        else {
          this.check_nhapmoi_success=false
          this.$toast.error('Cập nhật thất bại')
          this.loading(false)
        }
      }
      catch(ex){
        this.$toast.error('Cập nhật thất bại')
        this.loading(false)
      }
    },
    lay_danhsach_loai_phattrien :async function(){
      API.lay_danhsach_loai_phattrien(this.axios).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.listbox.loai_pt.list = response.data.data.map((x) => {
          return { id: x['id'], text: x['name'] }
        }),
        this.listbox.loai_pt.value = this.listbox.loai_pt.list[0].id
      }
      else this.listbox.loai_pt.list = []
    })   
    },
    lay_thongtin_hdkh_theo_hdkh_id : async function(data){
      try {
      let res = await API.lay_thongtin_hdkh_theo_hdkh_id(this.axios, data)
      if (res.data.data && res.data.data.length >0 ){
        this.ma_kh = res.data.data[0].ma_kh
        this.ma_sothue = res.data.data[0].mst
        this.ten_kh=res.data.data[0].ten_kh
        this.diachi_kh=res.data.data[0].diachi_kh
        this.hdkh_id=res.data.data[0].hdkh_id
        this.$toast.success("Tìm thấy Mã KH phù hợp")
        } 
        else {
          this.$toast.error('Không tìm thấy Mã KH phù hợp')
        }
      }
      catch(ex){
        this.$toast.error('Không tìm thấy Mã KH phù hợp')
      }

    },

    tracuu_congvan:async function(data){
      var input={
        p_socongvan :data,
        p_donvi_ph_id : 0,
        p_ten_congvan : 0,
        p_loai_cv_id : 0,
        p_tungay :0,
        p_denngay :0
      }
      try {
      let res = await API.tracuu_congvan(this.axios, input)
      if (res.data.data && res.data.data.length >0 ){
        this.so_congvan = res.data.data[0].socongvan
        this.congvan_id = res.data.data[0].congvan_id
        this.$toast.success("Tìm thấy số công văn phù hợp")
        } 
        else {
          this.$toast.error('Không tìm thấy số công văn cần tìm')
        }
      }
      catch(ex){
        this.$toast.error('Không tìm thấy số công văn cần tìm')
      }
    },
    parseDate:function(dateString) {
      const [day, month, year, time] = dateString.split(/[/ ]/);
      return new Date(`${year}-${month}-${day}T${time}`);
    },
    xoa_chitiet_coche_doanhthu :async function(data){
      var input={p_js_rid:JSON.stringify(data)}
      try {
      let res = await API.xoa_chitiet_coche_doanhthu(this.axios, input)
      if (res.data.data && res.data.data.length >0 ){
        } 
        else {
          this.$toast.error('Cập nhật thao tác xóa thất bại')
        }
      }
      catch(ex){
        this.$toast.error('Cập nhật thao tác xóa thất bại')
      }
    },
    async loai_hd(){
            try{
                this.loading(true)
                let response = await API.loai_hd(this.axios);
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  var idsToFind = [1, 2, 5,6,7,8,16,24,25,30,41]
                  var res_data=[]
                  res_data=response.data.data
                  const stringIdsToFilter = idsToFind.map(String);
                  const loai_hd_yc = res_data.filter(item => stringIdsToFilter.includes(item.ID));     
                  this.loai_HD.list = loai_hd_yc.map((x) => {
                  return { id: x['ID'], text: x['NAME'] }
                })
                  // this.loai_HD.value = this.loai_HD.list[0].id
                  console.log("REponse api loại HĐ ",this.loai_HD.list)
                }else{
                    this.cboLoaiHD.list=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách loại hợp đồng')
            }finally{
                this.loading(false)
            }
        },
    clear_ds : function(){
      this.Ma_coche=""
      // this.phanloai_hd.value=this.phanloai_hd.list[0].id
      this.so_congvan=""
      this.Sl_thuebao=""
      this.loai_HD.value="" // 35232
      this.ma_kh=""
      this.ten_kh=""
      this.ma_sothue=""
      this.diachi_kh=""
      this.congvan_id=null

    },
    Click_DeleteTT:async function(){

      if(this.list_TT_phanchia.length>0){
        // console.log("edfewgfe4ewedrf",this.list_TT_phanchia)
        // var list_rid=this.list_TT_phanchia.map(item => item.rid)
        // const filteredData = this.gridDanhSachPCDT.filter(item => !list_rid.includes(item.rid))
        // this.gridDanhSachPCDT=filteredData
        // this.gridDanhSachPCDT=this.gridDanhSachPCDT.filter((_, index) => !this.list_index_select.includes(index));
        this.gridDanhSachPCDT = this.gridDanhSachPCDT.filter(x => !this.list_TT_phanchia.some(y => y.rid === x.rid))
        if(!this.click_check){
          this.check_ghilai=0
        }
        else this.check_ghilai=1

      }
      else (this.$toast.error('Chưa chọn hàng để xóa'))
      
    },
    CSS_LOAIHINH_TB :async function(){
      API.CSS_LOAIHINH_TB(this.axios, null).then((response) => { 
        console.log("CSS_LOAIHINH_TB", response)}) 
    },

    lay_danhsach_congdoan :async function(){
      API.lay_danhsach_congdoan(this.axios, null).then((response) => {
      if (response && response.data && response.data.data.length >0) {
        var list_congvan= response.data.data.filter(item => item.ttcd_id === 1)
        this.listbox.congdoan.list = list_congvan.map((x) => {
          return { id: x['congdoan_id'], text: x['congdoan'] }
        })
        this.listbox.congdoan.val=this.listbox.congdoan.list[0].id
    }})  
    },
    Get_dichvuvt : async function(){
      API.dichvu_vt(this.axios, null).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.listbox.dichvu_vt.list = response.data.data.map((x) => {
          return { id: x['DICHVUVT_ID'], text: x['TEN_DVVT'] }
        }),
        this.listbox.dichvu_vt.value = 1
      }
      else this.listbox.dichvu_vt.list = []
    })   
    },
    Get_Loaihinh : async function(){
      let res = API.CSS_LOAIHINH_TB(this.axios, null)
        res.then(
        (response) => {
        if (response.data.data.length >0 ){
          var ds_loaihinh = response.data.data.map(obj => {
          return {
            loaitb_id: obj.LOAITB_ID,
            loaihinh_tb: obj.LOAIHINH_TB,
            dichvuvt_id: obj.DICHVUVT_ID
          };
        })
          this.list_loaihinh=ds_loaihinh
        }}
        )
    },
    lay_danhsach_donvi_phathanh: async function(){
      let res = await API.lay_danhsach_donvi_phathanh(this.axios, null)
      if (res.data.data && res.data.data.length >0 ){
        this.danhsach_phathanh=res.data.data
        console.log("Tú kiểm tra danh sách phát hành",this.danhsach_phathanh )
        if (this.phanvung_id != 97) var data_dv = res.data.data.filter(item => item.phanvung_id === this.phanvung_id /*&& /TTKD VNPT/.test(item.ten_dv)*/)
        else {
          this.check_phanvung=false
          var data_dv = res.data.data.filter(item => item.phanvung_id == this.phanvung_id)
      }
        // var data_dv = res.data.data.filter(item => item.donvi_id == this.donvi_dl_id)
        this.donvi_BH.list = data_dv.map((x) => {
            return { id: x['donvi_id'], text: x['ten_dv'] }
          })
        var listbox_dv = res.data.data.map((x) => {
            return { id: x['donvi_id'], text: x['ten_dv'] }
          })
        this.listbox.donvi.list=listbox_dv
        if(this.donvi_BH.list.length>0 )
          {
          this.donvi_BH.value = this.donvi_BH.list[0].id
          await this.lay_danhsach_donvi_banhang(this.donvi_BH.value)
          
          this.listbox.donvi.value=this.listbox.donvi.list[0].id
        }else this.$toast.error('Liên hệ admin để set donvi_dl_id')

      } else {
        this.donvi_BH.list = []
        this.listbox.donvi.list=[]
        }


    },
    select_donvicon :async function(event){
      if(this.donvi_BH.list.length>0)
      { await this.lay_danhsach_donvi_banhang(this.donvi_BH.value)}
    },
    lay_danhsach_donvi_banhang : async function(donvi_id ){
      var res =await API.lay_danhsach_donvi_banhang(this.axios,donvi_id )
      if (res.data.data && res.data.data.length >0 ){
      this.donvi.list = res.data.data.map((x) => {
          return { id: x['donvi_id'], text: x['ten_dv'] }
        })
        this.donviKey++;
        this.donvi.value = this.donvi.list[0].id
      } else {
        this.donviKey++
        this.donvi.list = [] 
        this.donvi.value = ''
      }
    },
    lay_danhsach_phanloai_hd : async function () {
      let response = await API.lay_danhsach_phanloai_hd(this.axios, null)
      if (response.data.data && response.data.data.length >0 ){
        this.phanloai_hd.list = response.data.data.map((x) => {
          return { id: x['phanloaihd_id'], text: x['phanloai_hd'] }
        })
        this.phanloai_hd.value = this.phanloai_hd.list[0].id
      } else this.phanloai_hd.list = []
    },
    lay_lay_ds_coche_doanhthu : async function () {
      let response = await API.lay_lay_ds_coche_doanhthu(this.axios, null)
      if (response.data.data && response.data.data.length >0 ){
        var result = response.data.data
        this.check_listcoche = false
        result.forEach(item => {
          this.donvi_BH.list.forEach(item_dv => {
            if (item.ten_dv === item_dv.text) {
              item.donvi_cha_id = item_dv.id;  
            }
          });
      })
        result.sort((a, b) => this.parseDate(b.ngay_cn) - this.parseDate(a.ngay_cn))
        this.ds_coche.list=[]
        this.ds_coche.list = result
          // this.donvi_BH.list đây là list donvi
          //this.get_dscoche_select(0)
      } else { 
        this.ds_coche.list = []
        this.check_listcoche = true
      }
    },
    lay_thongtin_phanchia_doanhthu : async function (data) {
      let response = await API.lay_thongtin_phanchia_doanhthu(this.axios, data)
      if (response.data.data){
        var data=response.data.data
        data.forEach(obj => {
          obj.CheckEdit=false
          obj.CheckEdit_donvi=false
          obj.CheckEdit_loaihinh=false
          obj.CheckEdit_tyle=false
          obj.CheckEdit_congdoan=false
          obj.CheckEdit_loai_PT=false
          obj.allow_thaotac=true
        })
        this.list_thongtincoche = data.map(item => ({ rid: item.rid }));
        console.log("this.list_thongtincoche",this.list_thongtincoche)
        return data
        // console.log("thông tin phân chia",this.gridDanhSachPCDT)
      } 
    },
    handleMultiSelectChange :function(data){
    },
    gridDSTB_rowSelected : async function (event) {
      this.index_currentPage=1
      this.loai_HD.value=[]
      this.check_nhapmoi=false
      if (this.check_1){
        console.log("Đã chọn lại row", event)
        this.loading(true);
        this.gridDanhSachPCDT=[]
        var coche_id = event.data.coche_id
        this.coche_id=coche_id
        var index = event.rowIndex
        // this.get_dscoche_select(index)
        this.Ma_coche=event.data.ma_coche
        var result=event.data.ten_dv 
        this.donvi_BH.value=event.data.donvi_cha_id
        if(this.donvi_BH.list.length>0 ) await this.lay_danhsach_donvi_banhang(this.donvi_BH.value)
        this.donvi.value=event.data.donvi_id
        this.phanloai_hd.value=event.data.phanloaihd_id
        this.so_congvan=event.data.socongvan
        this.Sl_thuebao=event.data.sl_tb
        if (event.data.ds_loaihd_id != null) this.loai_HD.value=event.data.ds_loaihd_id.split(',')//.map(Number) // 35232
        console.log("Loại hợp đồng ",this.loai_HD.value, typeof this.loai_HD.value )
        this.ma_kh=event.data.ma_kh
        this.ten_kh=event.data.ten_kh
        this.ma_sothue=event.data.mst
        this.diachi_kh=event.data.diachi_kh
        // 
        this.congvan_id=event.data.congvan_id
        
        var data = await this.lay_thongtin_phanchia_doanhthu(coche_id)
        data.forEach(item => {
          this.listbox.loai_pt.list.forEach(item_loaipt => {
              if (item.loai_pt === item_loaipt.id) {
                item.loai_pt_text = item_loaipt.text;  
              }
            });})
        data.forEach(item => {         
          this.listbox.donvi.list.forEach(item_donvi => {
            if (item.ten_dv == item_donvi.text) {
                item.donvi_id = item_donvi.id;  
              }
          });
        })
        this.gridDanhSachPCDT= data
        this.Select_coche=event.data
        //this.check_ghilai=1
        this.loading(false);
      }
      else this.check_1=true
    },
    Get_gridDanhSachPCDT :async function(coche_id){
      this.gridDanhSachPCDT=[]
      var data = await this.lay_thongtin_phanchia_doanhthu(coche_id)

      data.forEach(item => {
        this.listbox.loai_pt.list.forEach(item_loaipt => {
            if (item.loai_pt === item_loaipt.id) {
              item.loai_pt_text = item_loaipt.text;  
            }
          });})
      data.forEach(item => {         
        this.listbox.donvi.list.forEach(item_donvi => {
          if (item.ten_dv == item_donvi.text) {
              item.donvi_id = item_donvi.id;  
            }
        });
      })
      this.gridDanhSachPCDT= data
    },
    Click_Edit :function(){
      for(let i = 0; i < this.list_index_select.length;i++){
        this.gridDanhSachPCDT[i].CheckEdit=true
        this.gridDanhSachPCDT[i].CheckEdit_donvi=true
        this.gridDanhSachPCDT[i].CheckEdit_loaihinh=true
        this.gridDanhSachPCDT[i].CheckEdit_tyle=true
        this.gridDanhSachPCDT[i].CheckEdit_congdoan=true
        this.gridDanhSachPCDT[i].CheckEdit_loai_PT=true
      }
    },
    get_dscoche_select: async function (index) {

      this.Ma_coche=this.ds_coche.list[index].ma_coche
      var result=this.ds_coche.list[index].ten_dv 
      this.donvi_BH.value=this.ds_coche.list[index].donvi_cha_id
      await this.lay_danhsach_donvi_banhang(this.donvi_BH.value)
      this.phanloai_hd.value=this.ds_coche.list[index].phanloaihd_id
      this.so_congvan=this.ds_coche.list[index].socongvan
      this.Sl_thuebao=this.ds_coche.list[index].sl_tb
      this.loai_HD.value=this.ds_coche.list[index].ds_loaihd_id // 35232
      this.ma_kh=this.ds_coche.list[index].ma_kh
      this.ten_kh=this.ds_coche.list[index].ten_kh
      this.ma_sothue=this.ds_coche.list[index].mst
      this.diachi_kh=this.ds_coche.list[index].diachi_kh
      console.log("kết thúc")
    },
    showPopupCongVan : function () {
    },
    timkiem_makh :async function () {
      this.loading(true)
      await this.get_makh()
      this.loading(false)
    },
    get_makh :async function () {
      try{
        API.sp_tracuu_tt_kh_theo_makh(this.axios,this.ma_kh).then((response) => {
          if(response.data.data.length>0 && response.data.error_code== "BSS-00000000"){
            this.ten_kh=response.data.data[0].ten_kh
            this.diachi_kh=response.data.data[0].diachi_kh
            this.ma_sothue=response.data.data[0].mst
            this.$toast.success("Tìm thấy mã khách hàng phù hợp")
          }
          else {
            this.$toast.error('Không tìm thấy mã khách hàng phù hợp')
            this.ten_kh=""
            this.diachi_kh=""
            this.ma_sothue=""
        }
      }) 
      } catch (ex) {     
        this.$toast.error('Không tìm thấy mã khách hàng phù hợp')
        }
    },
    showPopupSearchContract:function() {
      this.$refs.popupSearchContract.showModal()
     // this.isModalVisible = false
    },
    showModal() {
      this.isModalVisible = true;
    },

    acceptSearchContract: function(data) {
      this.isModalVisible = false
      this.ma_kh=data.ma_kh
      this.ten_kh=data.ten_kh
      this.diachi_kh=data.diachi_kh
      this.ma_sothue=data.mst
    },

    // acceptSearchContract: function(item) {
    //   this.isModalVisible = false
    //   console.log("tusd kieertm tra",item)
    // },


    timkiem_congvan :async function () {
      try{
        if (this.so_congvan !=""){
          this.loading(true)
          var data=this.so_congvan
          this.so_congvan=''
          this.tracuu_congvan(data)
          this.loading(false)

        }

      }catch(ex){
        this.loading(false)
        this.$toast.error('Lỗi hiển thị số công văn')
      }
    },
    showPopupSearchAccount: function () {
      this.isModalVisible = false;
      this.popupComponent = () =>
        import(
          "@/modules/search/subscriber/SearchAccount"
        );
      this.popupComponentName = "SearchMaKH";
      this.popupComponentHeader = 'Tra cứu ';
      this.popupComponentData = {
        isPopup: true
      }; 
      this.Popup("popupComponents");
    },
    //
    // showPopupSearchContract: function () {
    //   this.isModalVisible = false;
    //   this.popupComponent = () =>
    //     import(
    //       "@/modules/search/subscriber/SearchContract"
    //     );
    //   this.popupComponentName = "SearchMaKhHopdong";
    //   this.popupComponentHeader = 'Tra cứu ';
    //   this.popupComponentData = {
    //     isPopup: 1
    //   }; 
    //   this.Popup("popupComponents");
    // },
    
    showPopupCongVan : function () {
      this.popupComponent = () =>
        import(
          "./popups/popupChonThanhVien"
        );
      this.popupComponentName = "showPopupCongVan";
      this.popupComponentHeader = 'Tra cứu Công Văn ';
      this.popupComponentData = {
        isPopup: true
      }; 
      this.Popup("popupComponents");
    },
///
    Popup:function(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    onRowCheckChange(){
    },

    onDataCheckeds_1:function(data,data1){
      this.list_TT_phanchia=data
      this.list_index_select=data1
      console.log("danh sách được chọn xóa",this.list_TT_phanchia , typeof this.list_TT_phanchia )
    },
    Change_Dichvu:function(val,item){
      const index = this.gridDanhSachPCDT.findIndex(x => x.rid === item.rid);
      this.gridDanhSachPCDT[index].dichvuvt_id=val
      var ten_dvvt= this.listbox.dichvu_vt.list.find(item => item.id == val)
      console.log("this.listbox.dichvu_vt.list",ten_dvvt.text, typeof ten_dvvt)
      this.gridDanhSachPCDT[index].ten_dvvt = ten_dvvt ?  ten_dvvt.text : ""
      var list_LHSelect= this.list_loaihinh.filter(item => item.dichvuvt_id == val);
      if (list_LHSelect.length>0){
        this.listbox.loaihinh.list= []
        this.listbox.loaihinh.value= ''
        this.listbox.loaihinh.list= list_LHSelect.map((x) =>{
          return { id: x['loaitb_id'], text: x['loaihinh_tb'] }
        })
        this.listbox.loaihinh.value=this.listbox.loaihinh.list[0].id
        var loaihinh_tb= this.listbox.loaihinh.list.find(item => item.id == this.listbox.loaihinh.value)
        this.gridDanhSachPCDT[index].loaihinh_tb = loaihinh_tb ?  loaihinh_tb.text : ""
        this.gridDanhSachPCDT[index].loaitb_id=this.listbox.loaihinh.value
      } else {
        this.gridDanhSachPCDT[index].loaitb_id=0
        this.listbox.loaihinh.list= []
        this.$toast.error("Vui lòng chọn loại dịch vụ khác, Liên hệ Admin để xử lý")
      }
    },
    Change_Loaihinh:function(val,item){
      const index = this.gridDanhSachPCDT.findIndex(x => x.rid === item.rid);
      this.gridDanhSachPCDT[index].loaitb_id=val
      var loaihinh_tb= this.listbox.loaihinh.list.find(item => item.id == val)
      this.gridDanhSachPCDT[index].loaihinh_tb = loaihinh_tb ?  loaihinh_tb.text : ""
    },
    Click_DeleteRow:async function(item){
      const index = this.gridDanhSachPCDT.findIndex(x => x.rid === item.rid);
      console.log("Click_DeleteRow", index,this.gridDanhSachPCDT[index].rid)
      this.gridDanhSachPCDT.splice(index, 1)
      if(!this.click_check){
          this.check_ghilai=0
        }
      else this.check_ghilai=1
      // this.loading(true)
      // // var data=[{rid:this.gridDanhSachPCDT[val].rid}]
      // // await this.xoa_chitiet_coche_doanhthu(data)
      // //this.check_nhapmoi=true
      // this.formLoad()
      // this.loading(false)
    },
    Click_EditRow:function(item){
      const index = this.gridDanhSachPCDT.findIndex(x => x.rid === item.rid);
      this.check_edit=this.check_edit+1
        var list_PCDT= this.gridDanhSachPCDT
        list_PCDT.forEach(item => {
          item.CheckEdit=false               
          item.CheckEdit_donvi=false
          item.CheckEdit_loaihinh=false
          item.CheckEdit_tyle=false
          item.CheckEdit_congdoan=false
          item.CheckEdit_loai_PT=false     
        });
        this.gridDanhSachPCDT=[]
        list_PCDT[index].CheckEdit=true
        this.listbox.dichvu_vt.value = list_PCDT[index].dichvuvt_id
        list_PCDT[index].CheckEdit_donvi=true
        this.listbox.donvi.value = list_PCDT[index].donvi_id
        list_PCDT[index].CheckEdit_loaihinh=true

        var list_LHSelect= this.list_loaihinh.filter(item => item.dichvuvt_id == this.listbox.dichvu_vt.value);
        this.listbox.loaihinh.list= []
        this.listbox.loaihinh.list= list_LHSelect.map((x) =>{
          return { id: x['loaitb_id'], text: x['loaihinh_tb'] }
        })
        this.listbox.loaihinh.value =list_PCDT[index].loaitb_id
        list_PCDT[index].CheckEdit_tyle=true
        list_PCDT[index].CheckEdit_congdoan=true
        list_PCDT[index].CheckEdit_loai_PT=true
        this.listbox.loai_pt.value = list_PCDT[index].loai_pt
        if(!this.click_check){
          this.check_ghilai=0
        }
        else this.check_ghilai=2
        //this.check_ghilai=2
        this.gridDanhSachPCDT=list_PCDT
    },
    Click_CopyRow:function (item){
      const index = this.gridDanhSachPCDT.findIndex(x => x.rid === item.rid);
      this.rid_ao=this.rid_ao+1
      var dt_copy=this.gridDanhSachPCDT[index]
      var data={
        rid: this.rid_ao,
        CheckEdit:false,
        CheckEdit_donvi:false,
        CheckEdit_loaihinh:false,
        CheckEdit_tyle:false,
        CheckEdit_congdoan:false,
        CheckEdit_loai_PT:false,
        allow_thaotac:true,
        ten_dv:dt_copy.ten_dv ,
        congdoan_id:dt_copy.congdoan_id ,
        dichvuvt_id:dt_copy.dichvuvt_id ,
        coche_id: dt_copy.coche_id,
        loaihinh_tb: dt_copy.loaihinh_tb,
        tyle:dt_copy.tyle ,
        loaitb_id:dt_copy.loaitb_id,
        congdoan: dt_copy.congdoan,
        loai_pt: dt_copy.loai_pt,
        ten_dvvt: dt_copy.ten_dvvt,
        loai_pt_text: dt_copy.loai_pt_text,
        donvi_id: dt_copy.donvi_id,

      }
      this.gridDanhSachPCDT.unshift(data)
      if(!this.click_check){
          this.check_ghilai=0
        }
        else this.check_ghilai=1
      // var tam=this.gridDanhSachPCDT
      // this.gridDanhSachPCDT =[]
      // this.gridDanhSachPCDT=tam
      // this.check_ghilai=1
      //this.Update_data_TTPCDT()
    },
    onChangePage:function(val){
      this.index_currentPage=val
    },
    Change_Donvi:function(val,item){
      const index = this.gridDanhSachPCDT.findIndex(x => x.rid === item.rid);
      this.gridDanhSachPCDT[index].donvi_id=val
      var ten_dv= this.listbox.donvi.list.find(item => item.id == val)
      this.gridDanhSachPCDT[index].ten_dv = ten_dv ?  ten_dv.text : ""
    },
    Change_congdoan:function(val,item){
      const index = this.gridDanhSachPCDT.findIndex(x => x.rid === item.rid);
      this.gridDanhSachPCDT[index].congdoan_id=val
      var congdoan= this.listbox.congdoan.list.find(item => item.id == val)
      this.gridDanhSachPCDT[index].congdoan = congdoan ?  congdoan.text : ""
    },
    Change_Loai_pt:function(val,item){
      const index = this.gridDanhSachPCDT.findIndex(x => x.rid === item.rid);
      this.gridDanhSachPCDT[index].loai_pt=val
      var loai_pt_text= this.listbox.loai_pt.list.find(item => item.id == val)
      this.gridDanhSachPCDT[index].loai_pt_text = loai_pt_text ?  loai_pt_text.text : ""
    },
    Change_Tyle:function(val,item){
      try{
        const index = this.gridDanhSachPCDT.findIndex(x => x.rid === item.rid);
        var number_tyle=Number(val)
        if( number_tyle>=0 && number_tyle<=100){
          this.gridDanhSachPCDT[index].tyle=val
        }
        else {
          this.gridDanhSachPCDT[index].tyle=0
          this.$toast.error('Vui lòng nhập lại tỷ lệ trong khoảng >=0 và <=100')
        }
      }catch(ex){
        this.$toast.error('Tỷ lệ yêu cầu là number')
      }

    },

    handleInput:function(event) {
      console.log(`Text changed to ${event.target.value}`);
      // Thực hiện các hành động khác khi `text` thay đổi
    },
    // ghi_lai : function(){
    //   data={
        
    //     p_thaotac:"", // number 1 insert ,2 update
    //     p_coche_id: "", // number
    //     p_congvan_id:"", // number
    //     p_donvi_cha_id: this.donvi_BH.value, // number
    //     p_donvi_id : this.donvi.value,// number
    //     p_phanloai_hd: this.phanloai_hd.value,// number
    //     p_sl_tb: this.Sl_thuebao,
    //     p_hdkh_id :"",// number
    //     p_js_loaihd:"" ,//string
    //     p_js_ct_coche_dt:""//string
    //   }
    // },
    Update_data_TTPCDT:async function () {
      if(this.Select_coche.ds_loaihd_id!=null){
          // var ds_loaihd_id = this.Select_coche.ds_loaihd_id.split(",").map(Number)
          var ds_hd_id=this.loai_HD.value.map(loaihd_id=>({loaihd_id}))
        }else var ds_hd_id =""
        this.gridDanhSachPCDT = this.gridDanhSachPCDT.map(item => {
          let matchedItem = this.danhsach_phathanh.find(d => d.ten_dv == item.ten_dv);
          return {
            ...item,
            phanvung_id: matchedItem ? matchedItem.phanvung_id : null
          };
        });
        var add_coche=this.gridDanhSachPCDT.map(x=>{
          return {
            loaitb_id:parseInt(x.loaitb_id),
            loai_pt:parseInt(x.loai_pt),
            phanvung_id:parseInt(x.phanvung_id),
            congdoan_id:parseInt(x.congdoan_id),
            donvi_id:parseInt(x.donvi_id),
            tyle:parseInt(x.tyle),
           }
        })
        this.list_thongtincoche_edit= this.gridDanhSachPCDT.map(item => ({ rid: item.rid }));
        console.log("this.list_thongtincoche_edit",this.list_thongtincoche_edit)
        const result = this.list_thongtincoche.filter(item1 => !this.list_thongtincoche_edit.some(item2 => item1.rid == item2.rid));
        this.xoa_chitiet_coche_doanhthu(result)
        console.log("add_coche",add_coche)
        add_coche = add_coche.filter((value, index, self) =>
          index === self.findIndex((t) => (
            JSON.stringify(t) === JSON.stringify(value)
          ))
        );
        console.log("add_coche2",add_coche)
        var data = {
        p_thaotac:2,
        p_coche_id:this.Select_coche.coche_id,
        p_congvan_id:this.Select_coche.congvan_id,
        p_donvi_cha_id:this.Select_coche.donvi_cha_id,
        p_donvi_id:this.Select_coche.donvi_id,
        p_phanloai_hd:this.Select_coche.phanloaihd_id,
        p_sl_tb:this.Select_coche.sl_tb,
        // p_hdkh_id:this.Select_coche.hdkh_id,
        p_ma_kh:this.ma_kh,
        p_mst:this.ma_sothue,
        p_ten_kh:this.ten_kh,
        p_diachi_kh:this.diachi_kh,
        p_js_loaihd: JSON.stringify(ds_hd_id),
        p_js_ct_coche_dt:JSON.stringify(add_coche),
        }
        console.log("Input api",data)
        this.loading(true)
        await this.capnhat_coche_doanhthu(data)
        this.allowCheckBox=true
        this.loading(false)
        this.check_nhapmoi=true
        this.formLoad()
    },

    ghi_lai :async function () {
      if (this.check_listcoche && this.check_ghilai==1){
        this.$toast.error("Vui lòng nhấn nhập mới và thao tác lại")
        return
      }
      if (this.check_ghilai==1){ //update
        if(true){
          // var ds_loaihd_id = this.loai_HD.value.split(",").map(Number)
          var ds_hd_id=this.loai_HD.value.map(loaihd_id=>({loaihd_id}))
        }else var ds_hd_id =""
        this.gridDanhSachPCDT = this.gridDanhSachPCDT.map(item => {
          let matchedItem = this.danhsach_phathanh.find(d => d.ten_dv == item.ten_dv);
          return {
            ...item,
            phanvung_id: matchedItem ? matchedItem.phanvung_id : null
          };
        });
        console.log("kieertm tra gridDanhSachPCDT",this.gridDanhSachPCDT)
        var add_coche=this.gridDanhSachPCDT.map(x=>{
          return {
            loaitb_id:parseInt(x.loaitb_id),
            loai_pt:parseInt(x.loai_pt),
            phanvung_id:parseInt(x.phanvung_id),
            congdoan_id:parseInt(x.congdoan_id),
            donvi_id:parseInt(x.donvi_id),
            tyle:parseInt(x.tyle),
           }
        })
        this.list_thongtincoche_edit= this.gridDanhSachPCDT.map(item => ({ rid: item.rid }));
        console.log("this.list_thongtincoche_edit",this.list_thongtincoche_edit)
        const result = this.list_thongtincoche.filter(item1 => !this.list_thongtincoche_edit.some(item2 => item1.rid == item2.rid));
        this.xoa_chitiet_coche_doanhthu(result)
        console.log("add_coche",add_coche)
        add_coche = add_coche.filter((value, index, self) =>
          index === self.findIndex((t) => (
            JSON.stringify(t) === JSON.stringify(value)
          ))
        );
        console.log("add_coche2",add_coche)
        var data = {
        p_thaotac:2,
        p_coche_id:this.Select_coche.coche_id,
        p_congvan_id:this.congvan_id,
        p_donvi_cha_id:this.donvi_BH.value,
        p_donvi_id:this.donvi.value,
        p_phanloai_hd:this.phanloai_hd.value,
        p_sl_tb:this.Sl_thuebao,
        // p_hdkh_id:this.Select_coche.hdkh_id,
        p_ma_kh:this.ma_kh,
        p_mst:this.ma_sothue,
        p_ten_kh:this.ten_kh,
        p_diachi_kh:this.diachi_kh,
        p_js_loaihd: JSON.stringify(ds_hd_id),
        p_js_ct_coche_dt:JSON.stringify(add_coche),
        }
        this.loading(true)
        await this.capnhat_coche_doanhthu(data)
        this.allowCheckBox=true
        this.loading(false)
        this.check_ghilai=1
        this.check_nhapmoi=true
        this.formLoad()
      }
      else if(this.check_ghilai==2){ //update
        this.Update_data_TTPCDT()
        this.check_ghilai=2
      }
      else if (this.check_ghilai==-1){
        if(this.Select_coche.ds_loaihd_id!=null){
          var ds_loaihd_id = this.Select_coche.ds_loaihd_id.split(",").map(Number)
          console.log("ds_loaihd_id",ds_loaihd_id)
          var ds_hd_id=ds_loaihd_id.map(loaihd_id=>({loaihd_id}))
        }else var ds_hd_id =""
        //
        this.gridDanhSachPCDT = this.gridDanhSachPCDT.map(item => {
          let matchedItem = this.danhsach_phathanh.find(d => d.ten_dv == item.ten_dv);
          return {
            ...item,
            phanvung_id: matchedItem ? matchedItem.phanvung_id : null
          };
        });
        //
        var add_coche=this.gridDanhSachPCDT.map(x=>{
          return {
            loaitb_id:x.loaitb_id,
            loai_pt:x.loai_pt,
            phanvung_id:x.phanvung_id,
            congdoan_id:x.congdoan_id,
            donvi_id:x.donvi_id,
            tyle:x.tyle,
           }
        })
        var data = {
        p_thaotac:3,
        p_coche_id:this.Select_coche.coche_id,
        p_congvan_id:this.Select_coche.congvan_id,
        p_donvi_cha_id:this.Select_coche.donvi_cha_id,
        p_donvi_id:this.Select_coche.donvi_id,
        p_phanloai_hd:this.Select_coche.phanloaihd_id,
        p_sl_tb:this.Select_coche.sl_tb,
        // p_hdkh_id:this.Select_coche.hdkh_id,
        p_ma_kh:this.ma_kh,
        p_mst:this.ma_sothue,
        p_ten_kh:this.ten_kh,
        p_diachi_kh:this.diachi_kh,
        p_js_loaihd: JSON.stringify(ds_hd_id),
        p_js_ct_coche_dt:JSON.stringify(add_coche),
        }
        this.loading(true)
        await this.capnhat_coche_doanhthu(data)
        this.allowCheckBox=true
        this.loading(false)
        this.check_nhapmoi=true
        this.formLoad()
      }
      else {
        try { // Nhập mới 
          // if (this.ma_sothue=="" || this.ma_sothue==null){
          //   this.$toast.error("Mã KH không có mã số thuế")
          //   return
          // }
          if (this.ten_kh=="" || this.ten_kh==null){
            this.$toast.error("Mã KH không có tên khách hàng")
            return
          }
          if (this.diachi_kh=="" || this.diachi_kh==null){
            this.$toast.error("Mã KH không có địa chỉ khách hàng")
            return
          }
          if (this.donvi.value=="" || this.donvi.value==null){
            this.$toast.error("Chưa chọn đơn vị ")
            return
          }
          var ds_hd_id=this.loai_HD.value.map(loaihd_id=>({loaihd_id}))

          if(this.gridDanhSachPCDT.length>0){
            
          this.gridDanhSachPCDT = this.gridDanhSachPCDT.map(item => {
              let matchedItem = this.danhsach_phathanh.find(d => d.ten_dv == item.ten_dv);
              return {
                ...item,
                phanvung_id: matchedItem ? matchedItem.phanvung_id : null
              };
            });
            var add_coche=this.gridDanhSachPCDT.map(x=>{
            return {
              loaitb_id:x.loaitb_id,
              loai_pt:x.loai_pt,
              phanvung_id:x.phanvung_id,
              congdoan_id:x.congdoan_id,
              donvi_id:x.donvi_id,
              tyle:x.tyle,
            }
            })
          }else var add_coche=""
          var data = {
          p_thaotac:1,
          p_coche_id:0,
          p_congvan_id:this.congvan_id,
          p_donvi_cha_id:this.donvi_BH.value,
          p_donvi_id:this.donvi.value,
          p_phanloai_hd:this.phanloai_hd.value,
          p_sl_tb:this.Sl_thuebao,
            // p_hdkh_id:this.Select_coche.hdkh_id,
          p_ma_kh:this.ma_kh,
          p_mst:this.ma_sothue,
          p_ten_kh:this.ten_kh,
          p_diachi_kh:this.diachi_kh,
          p_js_loaihd: JSON.stringify(ds_hd_id),
          p_js_ct_coche_dt:JSON.stringify(add_coche),
          }
          this.loading(true)
          await this.capnhat_coche_doanhthu(data)
          if(!this.check_nhapmoi_success) {
            this.loading(false)
            return
          }
          this.loading(false)
          this.click_check=true
          this.check_ghilai=1
          this.check_nhapmoi=true
          this.formLoad()
      }catch(x){
        this.$toast.error("Cập nhật thất bại")
        this.loading(false)
      }}
    },
    Delete :async function () {
        this.check_ghilai=-1
        this.check_nhapmoi=true
        this.ghi_lai()
    },
    async popupClosed(val) {
      // console.log("Tú check,", val)
      // this.ma_kh=val.ma_kh
      // this.ten_kh=val.khachhang.ten_kh
      // this.diachi_kh=val.khachhang.diachi_kh
      // this.ma_sothue=val.khachhang.mst
      if (!val) {
        this.ClosePopup("popupComponents");
        return;
      }
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "SearchMaKhHopdong": 
            break;
          case "SearchMaKH": 
            this.ma_kh=val.ma_kh
            this.ten_kh=val.khachhang.ten_kh
            this.diachi_kh=val.khachhang.diachi_kh
            this.ma_sothue=val.khachhang.mst
            break;
          case "showPopupCongVan": 
            this.so_congvan=val.socongvan
            this.congvan_id=val.congvan_id
            break;
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error("" + ex.data.message_detail);
        } else {
          this.$toast.error("" + ex);
        }
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
  },

  watch: {
    gridDanhSachPCDT(newVal, oldVal) {
      // Thực hiện các hành động khác khi `text` thay đổi
      this.gridDanhSachPCDT=this.gridDanhSachPCDT
    },

  },
  computed: {
  }
}
</script>
