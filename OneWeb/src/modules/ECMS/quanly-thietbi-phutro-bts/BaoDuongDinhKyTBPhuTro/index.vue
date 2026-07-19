<template src="./index.template.html"></template>
<script>
import DateTimeLib from "../../../../utils/DateTimeLib";
import TraCuuTBi from ".././TraCuuTBi";
import VanHanh_KM from ".././VanHanhKM";
import EventBus from "../../../../utils/eventBus";
export default {
  components: {
    TraCuuTBi,VanHanh_KM
  },
  data: function () {
    return {
      currentItem: {},
      dsThuocTinh: [],
      dsLichSuVanHanhTBI: [],
      dsTiepNhan: [],
      txtMaTs:"",
      ma_ts:"",
      txtdonviql:"",
      txtNoiDung:"",
      txtGhichu:"",
      txtKetQua:"",
      txtTongtien:0,
      vthietbi_id:0,
      vdonvi_id:0,
      vvattu_id:0,
      gridTiepNhan:[],
      gridlichsu:[],
      grid_Value:[],
      waterMark: "Chọn ngày",
      dateVal:  Date,
      dateFormat: "dd/MM/yyyy",
      ngay_yc: new Date(),
      ngay_ht: new Date(),
      vphieuvh_id : 0,
      vloaiVH_id : 1,
      vvanhanh_id:0,
      isDisableBtnLayTT:false,
      isDisableBtnNhapMoi:false,
      isDisableBtnCapNhap:false,
      isDisableBtnXoa:false,
      isDisableBtnHuy:false,
      isDisableHoanThanh:false,
      isDisableTongTien:false,
      currentItem:{},
      modalThongBao:false,
      kieu_goi:0
    };
  },
   props: ["modalId", "thietbi_id","Kieugoi"],
   watch: {
   txtTongtien: function(val){
     this.txtTongtien = this.numberWithCommas(val)
   }
   },
  computed: {},
  methods: {
    //#region "Nút bấm"
    focusMyElement:async function(){
    this.ma_ts = this.thietbi_id
    this.kieu_goi = this.Kieugoi
    await  this.frmBaoDuongDinhKy_Load()
    },
    hiddenForm:  function (){
    this.clear()
    this.dsTiepNhan =[]
    },
    btnNhapMoi_Click: function (){
     try
            {
                this.clear();
                this.setbutton(1);
               
            }
            catch (ex)
            {
                this.$root.toastError("Có lỗi lấy danh sách tiếp nhận");
            }
    },
    btnCapNhap_Click:async function (){
     try
            {
              this.$root.showLoading(true)
                if (!this.isDisableBtnNhapMoi)
                {
                     console.log('update')
                    if (this.vvanhanh_id == 0)
                    {
                        this.$root.toastError("Không có thiết bị để cập nhập!");
                        return;
                    }
                    let vdonvi_nhan_id = 0;
                    //update
                    await this.phutro_trangthai_tiepnhan(2,this.vvanhanh_id,"",this.vloaiVH_id,this.vthietbi_id,this.txtNoiDung,this.ngay_yc,"",this.txtKetQua,1,
                                                      this.ngay_ht,this.txtGhichu,"","",this.vphieuvh_id,vdonvi_nhan_id);
                    await this.update_vanhanh(vdonvi_nhan_id,this.vvanhanh_id)
                    this.$root.toastSuccess("Cập nhập thiết bị thành công!");
                    await this.load_ds_tiepnhan(1, 0);
                }
                else
                {

                     console.log('insert')
                    if (this.vthietbi_id == 0)
                    {
                        this.$root.toastError("Không có thiết bị!");
                        return;
                    }
                    if (this.txtMaTs == "")
                    {
                        this.$root.toastError("Chưa có mã tài sản!");
                        return;
                    }
                    var vdonvi_nhan_id = 0;
                    this.vvanhanh_id = 0;

                    let dt6 = [...this.dsTiepNhan]
                    if (dt6 != null)
                    {
                        let aa = dt6.filter(r => r.LOAIVH_ID == this.vloaiVH_id && r.THIETBI_ID == this.vthietbi_id).length;
                        if (aa > 0)
                        {
                            this.$root.toastError("Thiết bị cập nhật bị trùng!");
                            return;
                        }
                    }
                    //them moi
                    await this.phutro_trangthai_tiepnhan(1,this.vvanhanh_id,"",this.vloaiVH_id,this.vthietbi_id,this.txtNoiDung,this.ngay_yc,"",this.txtKetQua,1,
                                                      this.ngay_ht,this.txtGhichu,"","",this.vphieuvh_id,vdonvi_nhan_id);
                    await this.update_vanhanh(vdonvi_nhan_id,this.vvanhanh_id);
                    await this.load_ds_tiepnhan(1, 0);
                    this.$root.toastSuccess("Tiếp nhận thiết bị thành công!");
                    this.setbutton(2);
                }
            }
            catch (ex)
            {
              console.log(ex)
                this.$root.toastError("Cập nhật thiết bị thất bại")
            }finally{
               this.$root.showLoading(false)
            }
    },
    btnXoa_Click: function (){
     try
            {             
                if (this.vvanhanh_id != 0)
                {
                    this.modalThongBao = true;            
                }
                else
                {
                    this.$root.toastError("Không có thiết bị để xóa!");
                    return;
                }
            }
            catch ( ex)
            {
               console.log(ex)
                this.$root.toastError("Xóa  thất bại")
            }
    },
    xacNhan: async function(){
      try{
        this.$root.showLoading(true)
                    let vdonvi_nhan_id = 0;
                    await this.phutro_trangthai_tiepnhan(3,this.vvanhanh_id,"",this.vloaiVH_id,this.vthietbi_id,this.txtNoiDung,this.ngay_yc,"",this.txtKetQua,1,
                                                      this.ngay_ht,this.txtGhichu,"","",this.vphieuvh_id,vdonvi_nhan_id);
                     this.$root.toastSuccess("Xóa thiết bị thành công!");
                    this.clear();
                    await this.load_ds_tiepnhan(1, 0);
      }catch(e){
        console.log(e)
      }finally{
               this.$root.showLoading(false)
            }
    },
    btnHuy_Click: function (){
             if (this.currentItem == null)
            {
                if (this.dsTiepNhan.length ==0)
                    return;
              this.$refs.gridTiepNhan.setCurrentSelectedRow(0);
            }
            else
            {
               
            }
            this.setbutton(2);
    },
    btnHoanThanh_Click:async function (){
      try
            {
                if (this.vvanhanh_id == 0)
                {
                    this.$root.toastError("Không có thiết bị để hoàn thành!");
                    return;
                }
                let vdonvi_nhan_id = 0;
                await this.phutro_trangthai_tiepnhan(2,this.vvanhanh_id,"",this.vloaiVH_id,this.vthietbi_id,this.txtNoiDung,this.ngay_yc,"",this.txtKetQua,6,
                                                      this.ngay_ht,this.txtGhichu,"","",this.vphieuvh_id,vdonvi_nhan_id);
               await this.load_ds_tiepnhan(1, 0);
               this.$root.toastSuccess("Cập nhập hoàn thành thiết bị thành công!");

            }
            catch (ex)
            {
                this.$root.toastError("Hoàn thành thất bại");
            }

    },
    tsbtnLaytt_Click: async function(){
      try
            {
                if (this.txtMaTs == "")
                {
                  this.$root.toastError("Mã tài sản không được trống!")
                    return;
                }
                await this.load_grid(this.txtMaTs);
                await this.grid_lichsu(0, this.vthietbi_id);

            }
      catch (ex)
      {
         this.$root.toastError("Lấy thông tin thất bại")
      }
    },
    //#endregion
    load_grid: async function(a){
      try
            {
                let dt1 = [];
                dt1 = await this.phutro_lay_ds_theo_tbi(a, 1);
                console.log(dt1)
                if (dt1 != null && dt1.length > 0)
                {
                    this.txtdonviql = dt1[0]["TEN_DV"];
                    this.vthietbi_id = dt1[0]["THIETBI_ID"];
                    this.vdonvi_id = dt1[0]["DONVI_ID"];
                    this.vvattu_id = dt1[0]["VATTU_ID"];
                    await this.load_thuoctinh(this.vvattu_id, this.vthietbi_id);
                }
            }
            catch ( ex)
            {
                this.$root.toastError("Lấy thông tin thất bại")
            }
    },
    //#region  "api"
    phutro_lay_ds_theo_tbi: async function(vma_vt,  vkieu){
      try{
       let data ={
         ma_vt:vma_vt,
         kieu:vkieu
       }
       let response = await this.$root.context.get(`/web-ecms/quanlythietbiphutro/phutro_lay_ds_theo_tbi`, data)
       return response.data
      }catch (err) {
        return null
      }
    },
    phutro_lay_ds_tnhan_tbi_v3: async function(ttvh_id, thietbi_id, loaivh_id){
      try{
       let data ={
         ttvh_id:ttvh_id,
         thietbi_id:thietbi_id,
         loaivh_id:loaivh_id
       }
       let response = await this.$root.context.post(`/web-ecms/quanlythietbiphutro/phutro_lay_ds_tnhan_tbi_v3`, data)
       return response.data
      }catch (err) {
        return null
      }
    },
    phutro_lay_ds_tnhan_tbi: async function(ttvh_id, thietbi_id){
      try{
       if(ttvh_id!=null && thietbi_id!=null){
       let response = await this.$root.context.get(`/web-ecms/quanlythietbiphutro/phutro_lay_ds_tnhan_tbi`, {
         ttvh_id:ttvh_id,
         thietbi_id:thietbi_id
       })
       return response.data
       }
      }catch (err) {
        return null
      }
    },
    load_thuoctinh: async function(vattu_id, thietbi_id){
       try{
       if(vattu_id!=null && thietbi_id!=null){
       let response = await this.$root.context.get(`/web-ecms/quanlythietbiphutro/Lay_ThongTin_ThuocTinh_Theo_VatTu_ThietBi`,
                                                 {
         vattu_id:vattu_id,
         thietbi_id:thietbi_id
       })
       this.dsThuocTinh = response.data
       }
      }catch (err) {
        return null
      }
    },   
     update_vanhanh: async function(donvi_nhan_id, vanhanh_id){
       try{

       let response = await this.$root.context.post(`/web-ecms/vanhanh-thuongxuyen/capnhat-donvinhanid-vanhanh`,
                                                 {
         donvi_nhan_id:donvi_nhan_id,
         vanhanh_id:vanhanh_id
       })
      }catch (err) {
        return null
      }
    },   
    phutro_trangthai_tiepnhan: async function(kieu,vanhanh_id,ma_vh,loaivh_id,thietbi_id,
                noidung,ngay_yc,ketqua_td,ketqua,ttvh_id,ngay_ht,ghichu,dexuat,mota,phieuvh_id,donvi_nhan_id){
       try{
       let ip= await this.getIp();
       let data  = {
        kieu:kieu,
        vanhanh_id:vanhanh_id,
        ma_vh:ma_vh,
        loaivh_id:loaivh_id,
        thietbi_id:thietbi_id,
        noidung:noidung,
        ngay_yc:DateTimeLib.toDateDisplay (ngay_yc),
        nhanvien_id:this.$root.token.getNhanVienID(),
        donvi_id:this.$root.token.getDonViID(),        
        ketqua_td:ketqua_td,
        ketqua:ketqua,
        ttvh_id:ttvh_id,
        ngay_ht:DateTimeLib.toDateDisplay(ngay_ht),
        ghichu:ghichu,
        ngay_cn:DateTimeLib.toDateDisplay(new Date()),
        nguoi_cn:this.$root.token.getUserName(),
        may_cn:'',
        ip_cn:ip,
        dexuat:dexuat,
        mota:mota,
        phieuvh_id:phieuvh_id,
        donvi_nhan_id:donvi_nhan_id
       }
       console.log('data PT',data)
       let response = await this.$root.context.post(`/web-ecms/vanhanh-thuongxuyen/phutro-trangthai-tiepnhan`, data)
       return response.data
       
      }catch (err) {
        return null
      }
    } , 
     getIp:async function(){
      try{
      const response = await fetch("https://jsonip.com/")
      const data = await response.json();
      return data.ip;
      }catch(e) {
        return ""
      }
    }, 
    //#endregion
    async frmBaoDuongDinhKy_Load()
        {
            try
            {   
               this.$root.showLoading(true)         
                await this.load_ds_tiepnhan(1, 0);
                if (this.kieu_goi == 1)
                {
                    this.clear();
                    this.txtMaTs = this.ma_ts;
                    await this.tsbtnLaytt_Click()
                    this.setbutton(1);
                }
            }
            catch (ex)
            {
              console.log(ex)
               this.$root.toastError("Lấy thông tin thất bại")
            }finally{
               this.$root.showLoading(false)  
            }
    },
    load_ds_tiepnhan: async function(ttvh_id, thietbi_id){
     try
            {
                let dt5 = [];
                dt5 = await this.phutro_lay_ds_tnhan_tbi_v3(ttvh_id, thietbi_id, this.vloaiVH_id);

                if (dt5 != null && dt5.length >0)
                {
                    this.dsTiepNhan = [...dt5]
                }
                else
                {
                    this.clear(); 
                    this.setbutton(1);
                }
            }
            catch (ex)
            {
              console.log(ex)
                this.$root.toastError("Có lỗi lấy danh sách tiếp nhận");
            }
    },
    grid_lichsu: async function(vttvh_id, vthietbi_id){
      try
            {
                let dt8 = [];
                dt8 = await this.phutro_lay_ds_tnhan_tbi(vttvh_id, vthietbi_id);
                if (dt8 != null)
                {
                   this.dsLichSuVanHanhTBI = [...dt8]
                }

            }
            catch (ex)
            {
                this.$root.toastError("Có lỗi lấy danh sách lịch sử ");
            }
    },
    clear: function () {
            this.vthietbi_id = 0;
            this.ngay_yc= new Date(),
            this.ngay_ht= new Date(),
            this.txtNoiDung = ""         
            this.txtGhichu = "";         
            this.txtKetQua = "";
            this.txtMaTs = "";
            this.txtdonviql = "";
            this.txtTongtien = "";
            this.dsThuocTinh = [];
            this.dsLichSuVanHanhTBI=[];
    },
    setbutton: function (kieu) {
      if (kieu == 1)
            {
                this.isDisableTongTien = true;
                //btnTiepnhan.Enabled = true;
                this.isDisableBtnLayTT = false;
                this.isDisableBtnXoa = true;
                this.isDisableBtnCapNhap = false;
                this.isDisableHoanThanh = true;
                this.isDisableBtnNhapMoi = true;
            }
      if (kieu == 2)
            {
               // btnTiepnhan.Enabled = false;
                this.isDisableBtnLayTT = false;
                this.isDisableBtnXoa = false;
                this.isDisableBtnCapNhap = false;
                this.isDisableTongTien = false;
                this.isDisableHoanThanh = false;
                this.isDisableBtnNhapMoi = false;
            }
    },
    gridDsTiepNhan_rowSelected:async function(args){
      console.log('args.data',args.data)
     if(args!=null){
      Object.assign(this.currentItem,args.data)
      this.vvanhanh_id =args.data.VANHANH_ID;
      this.vphieuvh_id = args.data.PHIEUVH_ID;
      this.txtGhichu = args.data.GHICHU;
      this.txtNoiDung = args.data.NOIDUNG;
      this.ngay_yc = args.data.NGAY_YC;
      this.txtdonviql = args.data.TEN_DV;
      this.txtMaTs= args.data.THIETBI_ID;
      this.vthietbi_id = args.data.THIETBI_ID;
      this.vvattu_id = args.data.VATTU_ID;
      this.txtTongtien = args.data.THANHTIEN;
      this.txtKetQua = args.data.KETQUA;
      this.ngay_ht = DateTimeLib.toDateDisplay(args.data.NGAY_HT);
      this.vmota = args.data.MOTA;
      await this.load_thuoctinh(this.vvattu_id, this.vthietbi_id);
      await this.grid_lichsu(0, this.vthietbi_id);
     }
    },
    maTs_keypress: function(event){
        if(event.code =='Enter'){
        this.tsbtnLaytt_Click()
       }
    },
    btnTongtien_Click:async function(){
      try
            {
              this.txtTongtien = "";
              this.$bvModal.show('formVanHanhKM')           
            }
            catch (ex)
            {
                this.$root.toastError("Có lỗi xảy ra")
            }
    },
    chapNhanTongTien: async function(tongtien){
      this.txtTongtien = tongtien
       await  this.load_ds_tiepnhan(1, 0);
    },
    bntLayMaTbi_Click: function (){
                this.clear();
                this.$bvModal.show('formTraCuuTBi')               
    },
    chapNhanTraCuuTBi: async function (obj){
       if(obj!=null){
         this.ma_ts = obj.MA_TBI
         this.txtMaTs =  obj.MA_TBI
         this.vthietbi_id = obj.THIETBI_ID
         this.vdonvi_id = obj.DONVI_ID
         this.vvattu_id = obj.VATTU_ID
         this.txtdonviql = obj.TEN_DV
         await this.load_thuoctinh(this.vvattu_id, this.vthietbi_id);
       }
    },
    txtTongtien_press: function(event){
    if(!(event.charCode >= 48 && event.charCode <= 57))
      event.preventDefault();
    },
    numberWithCommas(number) {
      try{
      number = number.toString().replaceAll(",","");
      return number.toString().replace(/^[+-]?\d+/, function(int) {
      return int.replace(/(\d)(?=(\d{3})+$)/g, '$1,');
     });
      }catch(e){
        console.log(e)
        return 0
      }
    } ,
  },
  async mounted() {
    try {
      EventBus.$on("chapNhanTraCuu", (data) => {
         this.chapNhanTraCuuTBi(data)
      });
      EventBus.$on("chapNhanTongTien", (data) => {
         this.chapNhanTongTien(data)
      });
    } catch (error) {
    } finally {
    }
  },
  destroyed() {
    EventBus.$off("chapNhanTraCuu");
    EventBus.$off("chapNhanTongTien");
  },
};
</script>
<style>
.modal-fullscreen .modal-dialog{
    max-width: 100%;
    margin: 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100vh;
    display: flex;
    position: fixed;
    z-index: 100000;
  }
div#formBaoDuong___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>