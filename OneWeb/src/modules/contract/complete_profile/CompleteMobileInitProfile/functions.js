import api from './api' 
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";

var axios = null;
export default{
     
     async CSS_LOAI_HD(axios){ 
        var data = []; 
        await  api.CSS_LOAI_HD(axios, {}).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
          }); 
         return data;
     },
      
     async CSS_LOAIHINH_TB(axios){ 
        var data = []; 
        await  api.CSS_LOAIHINH_TB(axios, {}).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
          }); 
         return data;
     },
     async LAY_DS_HDTB_DIDONG(axios, loaihdId, status, tthdId, loaitb_id_in_hd, nhanvien_id, phanvung_id){ 
        var data = []; 
        var req = {
            "loaihd_id": loaihdId,
            "loaitb_id": loaitb_id_in_hd,
            "nhanvien_id": nhanvien_id,
            "phanvung_id": phanvung_id,
            "status": status,
            "tthd_id": tthdId
        }
        await  api.lay_ds_hdtb_didong(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            } 
            if(res.data.error != "200" && res.data.error != "204"){
                throw "Lỗi gọi api lay_ds_hdtb_didong " + res.message;
            }
          }); 
         return data;
     },
     
     async fn_frmdongbovina_hoanthien(axios, phieu_id, quytrinh_id, hdtb_id, loaihd_id, loaitb_id, nhanvien_id, donvi_id, phanvung_id, may_cn, nguoi_cn, ip_cn){
        var data = []; 
        var req = {
            "donvi_id": donvi_id,
            "hdtb_id": hdtb_id,
            "ip_cn": ip_cn,
            "loaihd_id": loaihd_id,
            "loaitb_id": loaitb_id,
            "may_cn": may_cn,
            "nguoi_cn": nguoi_cn,
            "nhanvien_id": nhanvien_id,
            "phanvung_id": phanvung_id,
            "phieu_id": phieu_id,
            "quytrinh_id": quytrinh_id
        }
        await  api.fn_frmdongbovina_hoanthien(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
            if(res.data.error != "200" && res.data.error != "204"){
                throw "Lỗi gọi api fn_frmdongbovina_hoanthien " + res.data.message;
            }
          }); 
        
        return data;
     },
     async sp_frmdongbovina_update_uploadanh(axios, hdtb_id, loaitb_id, phanvung_id){
        var data = []; 
        var req = {
            "hdtb_id": hdtb_id,
            "loaitb_id": loaitb_id,
            "phanvung_id": phanvung_id
          }
        await  api.sp_frmdongbovina_update_uploadanh(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            } 
          }); 
        
        return data;
     },
     async checkDaUploadAnhTraSau(axios, hdtb_id){
        //let data = await commonFn.MAP_ID(axios, "sync_stb", "css.hd_thuebao", "where hdtb_id = " + hdtb_id);
        let data = this.GetData(await api.fn_tt_hd_thuebao(axios, { type: 12, param: hdtb_id }));
        console.log("checkDaUploadAnhTraSau",data )
        return data*1 == 1;
     },
     async checkMNP(axios, hdtb_id, LoaiHinhTB){
        //let data = await commonFn.MAP_ID(axios, "chuyenmang", "css.hd_thuebao", "where loaitb_id = " + LoaiHinhTB.toString() + " and hdtb_id =" + hdtb_id);
        //let data = this.GetData(await api.lay_tt_chuyenmang_hd_thuebao(axios, { loaitb_id: LoaiHinhTB, hdtb_id: hdtb_id }));
        //return data*1 == 1;
        return false;
     },
     async fn_frmdongbovina_capnhat(axios, req){
        var data = [];  
        await  api.fn_frmdongbovina_capnhat(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
            if(res.data.error != "200" && res.data.error != "204"){
                throw res.data.message;
            }
          }); 
        
        return data;
     },
      
     async sp_frmdongbovina_lay_goidadv(axios, req){
        var data = [];  
        await  api.sp_frmdongbovina_lay_goidadv(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
            if(res.data.error != "200" && res.data.error != "204"){
                throw res.data.message;
            }
          }); 
        
        return data;
     }, 
     async lay_ds_goi_cv_vnp(axios, req){
        var data = [];  
        await  api.lay_ds_goi_cv_vnp(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
            if(res.data.error != "200" && res.data.error != "204"){
                throw res.data.message;
            }
          }); 
        
        return data;
     }, 
     async sp_frmdongbovina_tao_goidadv(axios, req){
        var data = [];  
        await  api.sp_frmdongbovina_tao_goidadv(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
            if(res.data.error != "200" && res.data.error != "204"){
                throw res.data.message;
            }
          }); 
        
        return data;
     }, 
     async capnhat_status_hopdong_thuebao(axios, req){
        var data = [];  
        await  api.capnhat_status_hopdong_thuebao(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
            if(res.data.error != "200" && res.data.error != "204"){
                throw res.data.message;
            }
          }); 
        
        return data;
     }, 
     async update_hd_thuebao_status(axios, req){
        var data = [];  
        await  api.update_hd_thuebao_status(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
            if(res.data.error != "200" && res.data.error != "204"){
                throw "Đồng bộ thành công, có lỗi khi update trạng thái trong db: " + res.data.message;
            }
          }); 
        
        return data;
     }, 
     async KIEMTRA_OPEN_SYNC_VINA($this, keyname) {
        let kq = await $this.$root.context.get("web-ccdv/hoanthienhschuyenquyen/kiemtra_open_sync_vina", {
          keyname: keyname
        })
        if (kq.data && kq.data.length > 0) {
          return kq.data[0].KEY_VALUE
        } else {
          return false
        }
      },
     GetData: function (response) { 
        if (response.data.error === 200 || response.data.error === '200') {
          return response.data.data
        } else {
          console.log(response.data.error_code)
        }
        return null
      },
}


    