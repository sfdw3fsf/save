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
 },   
 data: function () {
    return {
    title:'PHÂN TẬP KHDN THEO ',
    loaiDonVi:[],
    loaiDonViID:0,
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
   item_child : function(value){
       
       var list_item=this.Tree_view.filter((item)=>item.ID_CHA===value);
       
       return list_item;
   }, 
   khuvuc_child : function(value){
       
       var list_item=this.tree_KhuVuc.filter((item)=>item.KHUVUC_CHA_ID===value);
       
       return list_item;
   },
   kiemtra: function(){
       if (this.loaiDonVi===0 ||this.loaiDonVi==='')
        {
            this.$toast.error('Bạn chưa chọn loại đơn vị');
            return true;
        };
        if (this.donViID===0)
        {
            this.$toast.error('Bạn chưa chọn đơn vị');
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
                else
                    this.loaiDonViID=this.loaiDonVi[0].LOAIDV_ID;
            } catch (error) {}    
 },
    getDSLKHChuaGan:async function(donvi_id,loaidonvi_id){
        this.dsChuaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-loai-khach-hang?loaiDS=0&loaiDVId=${loaidonvi_id}&donViId=${donvi_id}`
                );
                this.dsChuaGan = rs.data;   

            } catch (error) {}    
    },
    getDSLKHDaGan:async function(donvi_id,loaidonvi_id){
        this.dsDaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-loai-khach-hang?loaiDS=1&loaiDVId=${loaidonvi_id}&donViId=${donvi_id}`
                );
                this.dsDaGan = rs.data;   

            } catch (error) {}    
    },
    getDSDiaChiChuaGan:async function(khuvuc_id,loaidonvi_id,donvi_id){
        this.dsChuaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-dia-chi-don-vi?loai=1&loaiDVId=${loaidonvi_id}&khuVucId=${khuvuc_id}&donViId=${donvi_id}`
                );
                this.dsChuaGan = rs.data;   

            } catch (error) {}    
    },
    getDSDiaChiDaGan:async function(khuvuc_id,loaidonvi_id,donvi_id){
        this.dsDaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-dia-chi-don-vi?loai=2&loaiDVId=${loaidonvi_id}&khuVucId=${khuvuc_id}&donViId=${donvi_id}`
                );
                this.dsDaGan = rs.data;   

            } catch (error) {}    
    },
     postDSLoaiKhachHang:async function(donvi_id,loaidonvi_id,loaikh_id,kieu){
        let param={
                    donViId: donvi_id,
                    loaiKHId: loaikh_id,
                    loaiDVId: loaidonvi_id,
                    hanhDong: kieu

                        }; 
        try {
                const rs = await this.$root.context.post(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/cap-nhat-don-vi-loai-khach-hang`,param
                );   


            } catch (error) {this.$toast.error("Có lỗi trong quá trình cập nhật");}    
    },
     postDSDoanhThu:async function(donvi_id,loaidonvi_id,dt_tu,dt_den,kieu){
        let param={
                    donViId: donvi_id,                    
                    loaiDVId: loaidonvi_id,
                    doanhThuTu:dt_tu,
                    doanhThuDen:dt_den,
                    hanhDong: kieu

                        }; 
        try {
                const rs = await this.$root.context.post(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/cap-nhat-don-vi-doanh-thu`,param
                );   


            } catch (error) {this.$toast.error("Có lỗi trong quá trình cập nhật");}    
    },
     postDSDiemTinNhiem:async function(donvi_id,loaidonvi_id,dtn_tu,dtn_den,kieu){
        let param={
                    donViId: donvi_id,                    
                    loaiDVId: loaidonvi_id,
                    diemTinNhiemTu:dtn_tu,
                    diemTinNhiemDen:dtn_den,
                    hanhDong: kieu

                        }; 
        try {
                const rs = await this.$root.context.post(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/cap-nhat-don-vi-diem-tin-nhiem`,param
                );   


            } catch (error) {this.$toast.error("Có lỗi trong quá trình cập nhật");}    
    },    
     getDSNNChuaGan:async function(donvi_id,loaidonvi_id){
        this.dsChuaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-don-vi-nganh-nghe?loai=1&loaiDVId=${loaidonvi_id}&donViId=${donvi_id}`
                );
                this.dsChuaGan = rs.data;   

            } catch (error) {}    
    },
    getDSNNDaGan:async function(donvi_id,loaidonvi_id){
        this.dsDaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-don-vi-nganh-nghe?loai=2&loaiDVId=${loaidonvi_id}&donViId=${donvi_id}`
                );
                this.dsDaGan = rs.data;   

            } catch (error) {}    
    },

    getDSDTDaGan:async function(donvi_id,loaidonvi_id){
        this.dsDaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-don-vi-theo-doanh-thu?loaiDVId=${loaidonvi_id}&donViId=${donvi_id}`
                );
                this.dsDaGan = rs.data;   

            } catch (error) {}    
    },
     getDSDTNDaGan:async function(donvi_id,loaidonvi_id){
        this.dsDaGan=[];
        try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/phan-tap-doanh-nghiep/ds-don-vi-theo-diem-tin-nhiem?loaiDVId=${loaidonvi_id}&donViId=${donvi_id}`
                );
                this.dsDaGan = rs.data;   

            } catch (error) {}    
    },
    btn_DaGan(){
     let arr = [];
      
     
        if (this.nodeID===1||this.nodeID===2)

        {  
              const dsSelected_canChuyen = this.$refs.dsChuaGan.getSelectedRecords() || [];
              if (this.kiemtra()) return;
              
              if (dsSelected_canChuyen.length===0)
            {
                this.$toast.warning('Bạn chưa chọn danh sách chưa gán');
                return;
            }
              if (dsSelected_canChuyen.length===0)
                  {
                      this.$toast.warning('Bạn chưa chọn danh sách chưa gán');
                      return;
                  }
                  this.$bvModal.msgBoxConfirm(
                                "Bạn có thật sự muốn cập nhật dữ liệu?",
                                {
                                    title: "Thông báo",
                                    centered: true,
                                    size: "sm",
                                    okTitle: 'Đồng ý',
                                    cancelTitle: 'Không đồng ý',
                                }
                                )
                                .then((value) => {
                                    if (value) {                                        
                                              if (this.nodeID===1)
                                                    {
                                                    dsSelected_canChuyen.map((val) => {
                                                      let idx = this.dsChuaGan.findIndex((v) => v.LOAIKH_ID == val.LOAIKH_ID);
                                                      if (idx >= 0) {
                                                        this.postDSLoaiKhachHang(this.donViID,this.loaiDonViID,this.dsChuaGan[idx].LOAIKH_ID,1)  
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
                                                      arr.push(this.dsChuaGan[idx]);
                                                      this.dsChuaGan = this.dsChuaGan.filter((v) => v.NGANHNGHE_ID != val.NGANHNGHE_ID);
                                                    }
                                                  });
                                                  }
                                                                              
                                              this.dsDaGan = [...arr, ...this.dsDaGan];
                                              this.dsSelectMoi = [];    
                                              this.$toast.success('Cập nhật thành công');
                                        } else {
                                        return;
                                        }
                                        
                                });

       
        }
      if (this.nodeID===4)
            {
                let idx = this.dsDaGan.findIndex((v) => v.DOANHTHU_TU == this.giatri_tu && v.DOANHTHU_DEN == this.giatri_den);
                if (idx >= 0) {
                    this.$toast.error("Đã tồn tại doanh thu này");
                    return;
                }
                let arr = [];
                this.postDSDoanhThu(this.donViID,this.loaiDonViID,this.giatri_tu, this.giatri_den,1);
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
                this.postDSDiemTinNhiem(this.donViID,this.loaiDonViID,this.giatri_tu, this.giatri_den,1)
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
       this.$bvModal.msgBoxConfirm(
                                "Bạn có thật sự muốn cập nhật dữ liệu?",
                                {
                                    title: "Thông báo",
                                    centered: true,
                                    size: "sm",
                                    okTitle: 'Đồng ý',
                                    cancelTitle: 'Không đồng ý',
                                }
                                )
                                .then((value) => {
                                    if (value) {                                        
                                                    if (this.nodeID===1)
                                                    {
                                                          dsSelected_canChuyen.map((val) => {
                                                          let idx = this.dsDaGan.findIndex((v) => v.LOAIKH_ID == val.LOAIKH_ID);
                                                          if (idx >= 0) {
                                                            this.postDSLoaiKhachHang(this.donViID,this.loaiDonViID,this.dsDaGan[idx].LOAIKH_ID,2)  
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
                                                          arr.push(this.dsDaGan[idx]);
                                                          this.dsDaGan = this.dsDaGan.filter((v) => v.NGANHNGHE_ID != val.NGANHNGHE_ID);
                                                        }
                                                      });
                                                    }
                                                  
                                                  if (this.nodeID===4)
                                                    {
                                                      
                                                      dsSelected_canChuyen.map((val) => {
                                                      let idx = this.dsDaGan.findIndex((v) => v.DOANHTHU_TU == val.DOANHTHU_TU && v.DOANHTHU_DEN == val.DOANHTHU_DEN);
                                                      if (idx >= 0) {
                                                        this.postDSDoanhThu(this.donViID,this.loaiDonViID,val.DOANHTHU_TU, val.DOANHTHU_DEN,2);
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
                                                        this.postDSDiemTinNhiem(this.donViID,this.loaiDonViID,val.DIEMTN_TU, val.DIEMTN_DEN,2)
                                                        arr.push(this.dsDaGan[idx]);
                                                        this.dsDaGan = this.dsDaGan.filter((v) => v.DIEMTN_TU != val.DIEMTN_TU && v.DIEMTN_DEN != val.DIEMTN_DEN);
                                                      }
                                                      
                                                    });
                                                    }

                                                    this.dsChuaGan = [...arr, ...this.dsChuaGan];
                                                    this.dsSelectMoi = [];
                                              this.$toast.success('Cập nhật thành công');
                                        } else {
                                        return;
                                        }
                                        
                                });

      
    },
    selectedItemsChuaGan(data) {
      this.dsSelectCu = data;      
    },
    selectedItemsDaGan(data) {
      this.dsSelectMoi = data;      
    },    
   showLeave(id){
        var co=true;
        this.tree_leaves.forEach(el=>{
            if (el===id)
                co=false;
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
                    this.nhanVienID=el.NHANVIEN_ID;
                    console.log(el)
                }
        });
       this.ds_leaves_kv=[...tmp_leaves];                        
       if (this.khuVucID!=0 && this.loaiDonViID!=0)
            this.load_danhsach(this.nodeID,this.donViID,this.loaiDonViID,this.khuVucID,this.nhanVienID);     
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
       if (this.donViID!=0 && this.loaiDonViID!=0)
            this.load_danhsach(this.nodeID,this.donViID,this.loaiDonViID,this.khuVucID,this.nhanVienID);     
       if (this.nodeID===3 && this.donViID!=0)
          this.loadKhuVuc(this.donViID);
   },
      
dsColumn:function(){
    return GRID_COLUMS[this.nodeID];
},
load_danhsach:function(nodeID,donvi,loaidv,khuvuc,nhanvien){
      if (nodeID===1)
        {
        this.getDSLKHChuaGan(donvi,loaidv);
        this.getDSLKHDaGan(donvi,loaidv);
        }
       if (nodeID===2)
        {
        this.getDSNNChuaGan(donvi,loaidv);
        this.getDSNNDaGan(donvi,loaidv);
        } 
        if (nodeID===3)
        {
            
        this.getDSDiaChiChuaGan(khuvuc,loaidv,donvi);
        this.getDSDiaChiDaGan(khuvuc,loaidv,donvi);
        }         
        if (nodeID===4)
        {
      
        this.getDSDTDaGan(donvi,loaidv);
        } 
        if (nodeID===5)
        {
       
        this.getDSDTNDaGan(donvi,loaidv);
        } 
        
        
}, 
},
watch: {    
    "loaiDonViID":function(newval,newold){
        this.load_danhsach(this.nodeID,this.donViID,newval,this.khuVucID,this.nhanVienID);  
    }
},
}
</script>
