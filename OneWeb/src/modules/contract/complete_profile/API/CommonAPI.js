import CompleteProfileAPI from './CompleteProfileAPI' 
var axios = null;
export default{
    setAxios(axios){
        this.axios = axios;
    },
     async lay_huonggiao_theo_luong_id(axios, luongId){ 
        var huongiao = {};
        var postData = { "luong_id": luongId };
        await  CompleteProfileAPI.lay_huonggiao_theo_luong_id(axios, postData).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                huongiao = res.data.data[0] 
            }
          }); 
         return huongiao;
     },
     async lay_thongtin_lh_lapdat(axios, hdkh_id){  
         var data = {};
        await  CompleteProfileAPI.lay_thongtin_lh_lapdat(axios, {hdkh_id: hdkh_id}).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;  
            }
          }); 
         return data;
     },
     async LAY_DS_NHANVIEN_THEO_PHIEU_ID(phieu_id,  nhanvien_id, kieu_id){  
         var data = {};
         var dataPost = {
            "phieu_id": phieu_id,
            "nhanvien_id": nhanvien_id,
            "kieu_id": kieu_id
         }
        await  CompleteProfileAPI.lay_ds_nhanvien_theo_phieu_id(this.axios, dataPost).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;  
            }
        }); 
        return data;
     },
     async LAY_DS_DANGKY_DVGT(hdtb_id){  
         var data = {};
         var dataPost = {
            "vhdtb_id": hdtb_id
         }
        await  CompleteProfileAPI.lay_ds_dangky_dvgt(this.axios, dataPost).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;  
            }
        }); 
        return data;
     }
 
}


    