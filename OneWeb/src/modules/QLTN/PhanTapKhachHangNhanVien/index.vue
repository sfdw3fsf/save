<template src="./index.html">
</template>
<style scoped>
 a.disable-btn {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }
</style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import GRID_COLUMS from "./constant";

export default {  
  
 created: async function () {
    this.loadTreeView();    
    this.loadNhomLNV();
 },   
 data: function () {
    return {
    title:'PHÂN TẬP KHDN THEO ',
    loaiDonVi:[],
    loaiDonViID:2,
    donViID:0,
    nodeID:0,
    Tree_view:[],
    tree_KhuVuc:[],
    ds_leaves_kv:[],
    tree_leaves:[],  
    tree_note_KV:0,
    tree_note:'',
    dsChuaGan:[],
    dsDaGan:[],
    GRID_COLUMS: GRID_COLUMS,
    giatri_tu:0,
    giatri_den:0,
    khuVucID:0,
    nhanVienID:0,
    nhomLoaiNV:[],
    nhomLoaiNV_ID:0,
    dsNVGioiThieu:[],
    loaiNVID:0,
    dsLoaiNV:[],
    dsNhanVienSelected:[],    
    }

},
// computed:{
//     chuaGan_AggregatesColumns(){
//       return [{field: "MA_LOAIKH",type: "Custom",footerTemplate: this.customTemplate,customAggregateFn: this.canchuyenAggregate}];
//     },
//     dagan_AggregatesColumns(){
//       return [{field: "MA_LOAIKH",type: "Custom",footerTemplate: this.customTemplate,customAggregateFn: this.duocchuyenAggregate}];
//     }
// },
methods : {    
        ...mapActions([
            "getListTDTuFile"
        ]),
    loadTreeView : async function () {
         try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-tap-cay-thu-muc`
                );
                this.Tree_view = rs.data;   

            } catch (error) {}            
    },
    loadKhuVuc : async function (donViId) {
         try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-khu-vuc-dia-ban-cap-3/don-vi/${donViId}`
                );
                this.tree_KhuVuc = rs.data;   

            } catch (error) {}            
    },
    loadNhomLNV : async function () {
         try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-nhom-loai-nhan-vien`
                );
                this.nhomLoaiNV = rs.data;   

            } catch (error) {}            
    },
    getDSLoaiNhanVien: async function (nhomnvid) {
       try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/quan-ly-dai-ly/ds-loai-nhan-vien/nhom/${nhomnvid}`
                );
                this.dsLoaiNV= rs.data;   

            } catch (error) {}       
    },
    getDSNhanVienGioiThieu : async function (donviid,loainvid) {
         try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/quan-ly-dai-ly/ds-nhan-vien-gioi-thieu?donViId=${donviid}&loaiNVId=${loainvid}`
                );
                this.dsNVGioiThieu= rs.data;   

            } catch (error) {}            
    },


   item_child : function(value){
       
       var list_item=this.Tree_view.filter((item)=>item.ID_CHA===value);
       
       return list_item;
   }, 
   khuvuc_child : function(value){
       
       var list_item=this.tree_KhuVuc.filter((item)=>item.KHUVUC_CHA_ID===value);
       
       return list_item;
   },
   kiemtra: function(){
       if (this.nhanVienID===0 ||this.loaiNVID===0)
        {
            this.$toast.error('Bạn chưa chọn nhân viên');
            return true;
        };        
        return false;
   },
    getLoaiDonVi:async function(donViID){
     this.loaiDonVi=[];
       try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/loai-don-vi/don-vi/${donViID}`
                );
                this.loaiDonVi = rs.data;   
                if (this.loaiDonVi.length===0)
                    this.loaiDonViID=0;
            } catch (error) {}    
 },
    getDSNVLKHChuaGan:async function(nhanvenid,loainvid){
        this.dsChuaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-nhan-vien-loai-khach-hang?kieuDS=1&nhanVienId=${nhanvenid}&loaiNVId=${loainvid}`
                );
                this.dsChuaGan = rs.data;   

            } catch (error) {}    
    },
    getDSNVLKHDaGan:async function(nhanvenid,loainvid){
        this.dsDaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-nhan-vien-loai-khach-hang?kieuDS=2&nhanVienId=${nhanvenid}&loaiNVId=${loainvid}`
                );
                this.dsDaGan = rs.data;   

            } catch (error) {}    
    },
    getDSDiaChiChuaGan:async function(khuvuc_id,loainvid,nhanvienid){
        this.dsChuaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-dia-chi-nhan-vien?loai=1&nhanVienId=${nhanvienid}&khuVucId=${khuvuc_id}&loaiNVId=${loainvid}`
                );
                this.dsChuaGan = rs.data;   

            } catch (error) {}    
    },
    getDSDiaChiDaGan:async function(khuvuc_id,loainvid,nhanvienid){
        this.dsDaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-dia-chi-nhan-vien?loai=2&nhanVienId=${nhanvienid}&khuVucId=${khuvuc_id}&loaiNVId=${loainvid}`
                );
                this.dsDaGan = rs.data;   

            } catch (error) {}    
    },
    postDSNVDiaChi:async function(nhanvienid,loainvid,quanid,phuongid,phoid,apid,khuid,dacdiemid,khuvucid,kieu){
        let param={
                    nhanVienId: nhanvienid,
                    loaiNVId: loainvid,
                    quanId: quanid,
                    phuongId:phuongid,
                    phoId:phoid,
                    apId:apid,
                    khuId:khuid,
                    dacDiemId:dacdiemid,
                    khuVucId:khuvucid,
                    hanhDong: kieu

                        }; 
        try {
                const rs = await this.$root.context.post(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/cap-nhat-nhan-vien-phuong-xa`,param
                );   


            } catch (error) {this.$toast.error("Có lỗi trong quá trình cập nhật");}    
    },
     postDSNVLoaiKhachHang:async function(nhanvien_id,loainv_id,loaikh_id,kieu){
        let param={
                    nhanVienId: nhanvien_id,
                    loaiKHId: loaikh_id,
                    loaiNVId: loainv_id,
                    hanhDong: kieu

                        }; 
        try {
                const rs = await this.$root.context.post(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/cap-nhat-nhan-vien-loai-khach-hang`,param
                );   


            } catch (error) {this.$toast.error("Có lỗi trong quá trình cập nhật");}    
    },
    postDSNVNganhNghe:async function(nhanvien_id,loainv_id,nganhngheid,kieu){
        let param={
                    nhanVienId: nhanvien_id,
                    nganhNgheId: nganhngheid,
                    loaiNVId: loainv_id,
                    hanhDong: kieu

                        }; 
        try {
                const rs = await this.$root.context.post(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/cap-nhat-nhan-vien-nganh-nghe`,param
                );   


            } catch (error) {this.$toast.error("Có lỗi trong quá trình cập nhật");}    
    },
     postDSNVDoanhThu:async function(nhanvienid,loainvid,dt_tu,dt_den,kieu){
        let param={
                    nhanVienId: nhanvienid,                    
                    loaiNVId: loainvid,
                    doanhThuTu:dt_tu,
                    doanhThuDen:dt_den,
                    hanhDong: kieu

                        }; 
        try {
                const rs = await this.$root.context.post(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/cap-nhat-nhan-vien-theo-doanh-thu`,param
                );   


            } catch (error) {this.$toast.error("Có lỗi trong quá trình cập nhật");}    
    },
     postDSNVDiemTinNhiem:async function(nhanvienid,loainvid,dtn_tu,dtn_den,kieu){
        let param={
                    nhanVienId: nhanvienid,                    
                    loaiNVId: loainvid,
                    diemTinNhiemTu:dtn_tu,
                    diemTinNhiemDen:dtn_den,
                    hanhDong: kieu

                        }; 
        try {
                const rs = await this.$root.context.post(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/cap-nhat-nhan-vien-theo-diem-tin-nhiem`,param
                );   


            } catch (error) {this.$toast.error("Có lỗi trong quá trình cập nhật");}    
    },    
     getDSNVNNChuaGan:async function(nhanvienid,loainvid){
        this.dsChuaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-nhan-vien-nganh-nghe?kieuDS=1&nhanVienId=${nhanvienid}&loaiNVId=${loainvid}`
                );
                this.dsChuaGan = rs.data;   

            } catch (error) {}    
    },
    getDSNVNNDaGan:async function(nhanvienid,loainvid){
        this.dsDaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-nhan-vien-nganh-nghe?kieuDS=2&nhanVienId=${nhanvienid}&loaiNVId=${loainvid}`
                );
                this.dsDaGan = rs.data;   

            } catch (error) {}    
    },

    getDSDTDaGan:async function(nhanvienid,loainvid){
        this.dsDaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-nhan-vien-theo-doanh-thu?nhanVienId=${nhanvienid}&loaiNVId=${loainvid}`
                );
                this.dsDaGan = rs.data;   

            } catch (error) {}    
    },
     getDSDTNDaGan:async function(nhanvienid,loainvid){
        this.dsDaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-nhan-vien-theo-diem-tin-nhiem?nhanVienId=${nhanvienid}&loaiNVId=${loainvid}`
                );
                this.dsDaGan = rs.data;   

            } catch (error) {}    
    },
    btn_DaGan(){
      let arr = [];
      
      
        if (this.nodeID===1||this.nodeID===2||this.nodeID===3)

        {  
          if (this.kiemtra()) return;
          
          const dsSelected_canChuyen = this.$refs.dsChuaGan.getSelectedRecords() || [];
          if (dsSelected_canChuyen.length===0)
              {
                  this.$toast.warning('Bạn chưa chọn danh sách chưa gán');
                  return;
              }
          
          if (this.nodeID===1)
                {
                dsSelected_canChuyen.map((val) => {
                  let idx = this.dsChuaGan.findIndex((v) => v.LOAIKH_ID == val.LOAIKH_ID);
                  if (idx >= 0) {
                    this.postDSNVLoaiKhachHang(this.nhanVienID,this.loaiNVID,this.dsChuaGan[idx].LOAIKH_ID,1)  
                    arr.push(this.dsChuaGan[idx]);
                    this.dsChuaGan = this.dsChuaGan.filter((v) => v.LOAIKH_ID != val.LOAIKH_ID);
                  }
                });
                }
          if (this.nodeID===2)
              {
              dsSelected_canChuyen.map((val) => {
                let idx = this.dsChuaGan.findIndex((v) => v.NGANHNGHE_ID == val.NGANHNGHE_ID);
                if (idx >= 0) {
                  this.postDSNVNganhNghe(this.nhanVienID,this.loaiNVID,this.dsChuaGan[idx].NGANHNGHE_ID,1);
                  arr.push(this.dsChuaGan[idx]);
                  this.dsChuaGan = this.dsChuaGan.filter((v) => v.NGANHNGHE_ID != val.NGANHNGHE_ID);
                }
              });
              }
            if (this.nodeID===3)
              {
              dsSelected_canChuyen.map((val) => {
                let idx = this.dsChuaGan.findIndex((v) => v.KHUVUC_ID == val.KHUVUC_ID 
                                                          && v.QUAN_ID == val.QUAN_ID
                                                          && v.PHUONG_ID == val.PHUONG_ID
                                                          && v.PHO_ID == val.PHO_ID
                                                          && v.KHU_ID == val.KHU_ID
                                                          && v.DACDIEM == val.DACDIEM                                                          
                                                          );
                if (idx >= 0) {
                  this.postDSNVDiaChi(this.nhanVienID,this.loaiNVID,this.dsChuaGan[idx].QUAN_ID,this.dsChuaGan[idx].PHUONG_ID,this.dsChuaGan[idx].PHO_ID,this.dsChuaGan[idx].AP_ID,this.dsChuaGan[idx].KHU_ID,this.dsChuaGan[idx].DACDIEM_ID,this.dsChuaGan[idx].KHUVUC_ID,1);
                  arr.push(this.dsChuaGan[idx]);
                  this.dsChuaGan = this.dsChuaGan.filter((v) => v.KHUVUC_ID != val.KHUVUC_ID 
                                                          && v.QUAN_ID != val.QUAN_ID
                                                          && v.PHUONG_ID != val.PHUONG_ID
                                                          && v.PHO_ID != val.PHO_ID
                                                          && v.KHU_ID != val.KHU_ID
                                                          && v.DACDIEM != val.DACDIEM    );
                }
              });
              }

          this.dsDaGan = [...arr, ...this.dsDaGan];
          this.dsSelectMoi = [];
        }
      if (this.nodeID===4)
            {
                let idx = this.dsDaGan.findIndex((v) => v.DOANHTHU_TU == this.giatri_tu && v.DOANHTHU_DEN == this.giatri_den);
                if (idx >= 0) {
                    this.$toast.error("Đã tồn tại doanh thu này");
                    return;
                }
                let arr = [];
                this.postDSNVDoanhThu(this.nhanVienID,this.loaiNVID,this.giatri_tu, this.giatri_den,1);
                arr.push({DOANHTHU_TU: this.giatri_tu,DOANHTHU_DEN: this.giatri_den});
            this.dsDaGan = [...arr, ...this.dsDaGan];
            this.dsSelectMoi = [];
            }
      if (this.nodeID===5)
            {
                let idx = this.dsDaGan.findIndex((v) => v.DIEMTN_TU == this.giatri_tu && v.DIEMTN_DEN == this.giatri_den);
                if (idx >= 0) {
                    this.$toast.error("Đã tồn tại điểm tín nhiệm này");
                    return;
                }
                let arr = [];
                this.postDSNVDiemTinNhiem(this.nhanVienID,this.loaiNVID,this.giatri_tu, this.giatri_den,1)
                arr.push({DIEMTN_TU: this.giatri_tu,DIEMTN_DEN: this.giatri_den});
            this.dsDaGan = [...arr, ...this.dsDaGan];
            this.dsSelectMoi = [];
            }
    },
    btn_ChuaGan(){
      let arr = [];
      const dsSelected_canChuyen = this.$refs.dsDaGan.getSelectedRecords() || [];
      
      if (dsSelected_canChuyen.length===0)
        {
            this.$toast.warning('Bạn chưa chọn danh sách đã gán');
            return;
        }
      
      if (this.nodeID===1)
      {
            dsSelected_canChuyen.map((val) => {
            let idx = this.dsDaGan.findIndex((v) => v.LOAIKH_ID == val.LOAIKH_ID);
            if (idx >= 0) {
              this.postDSNVLoaiKhachHang(this.nhanVienID,this.loaiNVID,this.dsDaGan[idx].LOAIKH_ID,2)  
              arr.push(this.dsDaGan[idx]);
              this.dsDaGan = this.dsDaGan.filter((v) => v.LOAIKH_ID != val.LOAIKH_ID);
            }
          });
      }
      
      if (this.nodeID===2)
      {
        dsSelected_canChuyen.map((val) => {
        let idx = this.dsDaGan.findIndex((v) => v.NGANHNGHE_ID == val.NGANHNGHE_ID);
          if (idx >= 0) {
            this.postDSNVNganhNghe(this.nhanVienID,this.loaiNVID,this.dsDaGan[idx].NGANHNGHE_ID,2);
            arr.push(this.dsDaGan[idx]);
            this.dsDaGan = this.dsDaGan.filter((v) => v.NGANHNGHE_ID != val.NGANHNGHE_ID);
          }
        });
      }
        if (this.nodeID===3)
              {
              dsSelected_canChuyen.map((val) => {
                let idx = this.dsDaGan.findIndex((v) => v.KHUVUC_ID == val.KHUVUC_ID 
                                                          && v.QUAN_ID == val.QUAN_ID
                                                          && v.PHUONG_ID == val.PHUONG_ID
                                                          && v.PHO_ID == val.PHO_ID
                                                          && v.KHU_ID == val.KHU_ID
                                                          && v.DACDIEM == val.DACDIEM                                                          
                                                          );
                if (idx >= 0) {
                  this.postDSNVDiaChi(this.nhanVienID,this.loaiNVID,this.dsDaGan[idx].QUAN_ID,this.dsDaGan[idx].PHUONG_ID,this.dsDaGan[idx].PHO_ID,this.dsChuaGan[idx].AP_ID,this.dsDaGan[idx].KHU_ID,this.dsDaGan[idx].DACDIEM_ID,this.dsDaGan[idx].KHUVUC_ID,2);
                  arr.push(this.dsDaGan[idx]);
                  this.dsDaGan = this.dsDaGan.filter((v) => v.KHUVUC_ID != val.KHUVUC_ID 
                                                          && v.QUAN_ID != val.QUAN_ID
                                                          && v.PHUONG_ID != val.PHUONG_ID
                                                          && v.PHO_ID != val.PHO_ID
                                                          && v.KHU_ID != val.KHU_ID
                                                          && v.DACDIEM != val.DACDIEM    );
                }
              });
              }
     if (this.nodeID===4)
      {
        console.log(dsSelected_canChuyen);
        dsSelected_canChuyen.map((val) => {
        let idx = this.dsDaGan.findIndex((v) => v.DOANHTHU_TU == val.DOANHTHU_TU && v.DOANHTHU_DEN == val.DOANHTHU_DEN);
        if (idx >= 0) {
          this.postDSNVDoanhThu(this.nhanVienID,this.loaiNVID,val.DOANHTHU_TU, val.DOANHTHU_DEN,2);
          arr.push(this.dsDaGan[idx]);
          this.dsDaGan = this.dsDaGan.filter((v) => v.DOANHTHU_TU != val.DOANHTHU_TU && v.DOANHTHU_DEN != val.DOANHTHU_DEN);
        }
        
        });
      }
       
        if (this.nodeID===5)
      {
        dsSelected_canChuyen.map((val) => {
        let idx = this.dsDaGan.findIndex((v) => v.DIEMTN_TU == val.DIEMTN_TU && v.DIEMTN_DEN == val.DIEMTN_DEN);
        if (idx >= 0) {
          this.postDSNVDiemTinNhiem(this.nhanVienID,this.loaiNVID,val.DIEMTN_TU, val.DIEMTN_DEN,2)
          arr.push(this.dsDaGan[idx]);
          this.dsDaGan = this.dsDaGan.filter((v) => v.DIEMTN_TU != val.DIEMTN_TU && v.DIEMTN_DEN != val.DIEMTN_DEN);
        }
        
      });
      }

      this.dsChuaGan = [...arr, ...this.dsChuaGan];
      this.dsSelectMoi = [];
    },
    selectedItemsChuaGan(data) {
      this.dsSelectCu = data;      
    },
    selectedItemsDaGan(data) {
      this.dsSelectMoi = data;      
    },    
    selectedItemsNhanVien(data) {
      this.dsNhanVienSelected = data;      
      this.nhanVienID=data[0].NHANVIEN_ID;
      this.load_danhsach(this.nodeID,this.nhanVienID,this.loaiNVID,this.khuVucID);

    },    

   showLeave(id){
        var co=false;
        this.tree_leaves.forEach(el=>{
            if (el===id)
                co=true;
        });
        return co;
    },
    showLeaveKV(id){
        var co=false;
        this.ds_leaves_kv.forEach(el=>{
            if (el===id)
                co=true;
        });
        return co;
    },
    click_leaves_KV :async function(id,text){
       var tmp_leaves=[];
       var co=false;
       this.ds_leaves_kv.forEach(el => {
           if (el!=id)           
                tmp_leaves.push(el);
           else
                co=true;
               
       });
       if (!co)
            tmp_leaves.push(id)
        this.tree_note_KV=id;
        this.tree_KhuVuc.forEach(el=>{
            if (el.KHUVUC_ID===id)
                {
                    this.khuVucID=id;                    
                }
        });
       this.ds_leaves_kv=[...tmp_leaves];                        
       if (this.khuVucID!=0 && this.nhanVienID!=0)
            this.load_danhsach(this.nodeID,this.nhanVienID,this.loaiNVID,this.khuVucID);     
   },
    click_leaves :async function(id,text){
       var tmp_leaves=[];
       var co=false;
       this.tree_leaves.forEach(el => {
           if (el!=id)           
                tmp_leaves.push(el);
           else
                co=true;
               
       });
       if (!co)
            tmp_leaves.push(id)
       this.tree_leaves=[...tmp_leaves];       
       this.tree_note=id;       
       this.dsDaGan=[];
       this.dsChuaGan=[];
       this.nodeID=Number(id.substring(0,1));      
       this.donViID=id.substring(2);
       console.log(this.nodeID);
       this.getLoaiDonVi(this.donViID); 
       this.title='PHÂN TẬP KHDN THEO '+text;          
       if (this.loaiNVID!=0);
       this.getDSNhanVienGioiThieu(this.donViID,this.loaiNVID);
       if (this.nodeID===3 && this.donViID!=0)
          this.loadKhuVuc(this.donViID);
   },
      
dsColumn:function(){
    return GRID_COLUMS[this.nodeID];
},
load_danhsach:function(nodeID,nhanvien,loainv,khuvuc){
      if (nodeID===1)
        {
        this.getDSNVLKHChuaGan(nhanvien,loainv);
        this.getDSNVLKHDaGan(nhanvien,loainv);
        }
       if (nodeID===2)
        {
        this.getDSNVNNChuaGan(nhanvien,loainv);
        this.getDSNVNNDaGan(nhanvien,loainv);
        } 
        if (nodeID===3)
        {
            
        this.getDSDiaChiChuaGan(khuvuc,loainv,nhanvien);
        this.getDSDiaChiDaGan(khuvuc,loainv,nhanvien);
        }         
        if (nodeID===4)
        {
      
        this.getDSDTDaGan(nhanvien,loainv);
        } 
        if (nodeID===5)
        {
       
        this.getDSDTNDaGan(nhanvien,loainv);
        } 
        
        
}, 
},
watch: {    
    
    "nhomLoaiNV_ID":function(newval,newold){
      this.getDSLoaiNhanVien(this.nhomLoaiNV_ID);
    },
    "loaiNVID":function(newval,newold){
      this.getDSNhanVienGioiThieu(this.donViID,newval);
    }
},
}
</script>
