import API from './CommonAPI'  
export default{
    
    async thongtin_nguoidung2(axios){ 
         
        var data = 0;
        await API.thongtin_nguoidung2(axios, {}).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
               data = response.data.data; 
            }
        });
        return data;
    },
    async MAP_ID(axios, in_id_neo, in_table, in_dk){ 
         
        var data = 0;
        await API.map_id(axios, {
          "in_dk": in_dk,
          "in_id_neo": in_id_neo,
          "in_table": in_table
        }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
               data = response.data.data; 
            }
        });
        return data;
    },
    async lay_ds_luong_thaotac(axios, luong_id){ 
        var data = [];
        await API.sp_lay_luong_thaotac(axios, {
            "luong_id": luong_id
        }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
               data = response.data.data; 
            }
        });
        return data;
    }, 
    async phailam(axios, luong_id, code){ 
        var data = [];
        data = await this.lay_ds_luong_thaotac(axios, luong_id);
        if(data && data.length > 0){
            return data.findIndex(
                (item) => item.enable === 1 && item.CODE === code
              ) >= 0
        }

        return false;
    },
    async lay_ct_thaotac_control(axios, thaotac_id){ 
        var data = [];
        await API.lay_ct_thaotac_control(axios, {
            "thaotac_id": thaotac_id
        }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
               data = response.data.data; 
            }
        });
        return data;
    },  
    async LAY_DS_KIEU_LD(axios, loaihd_id, loaitb_id){ 
        var data = []; 
        var req = {
            "loaihd_id": loaihd_id,
            "loaitb_id": loaitb_id
          }
        await  API.lay_ds_kieu_ld(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data.filter(x=>x.kieuld_id*1 > 0);
            }
          }); 
         return data;
     },
     async LAY_DS_DANGKY_DVGT(axios, vhdtb_id){ 
        var data = []; 
        var req = {
            "vhdtb_id": vhdtb_id 
          }
        await  API.lay_ds_dangky_dvgt(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
          }); 
         return data;
     },
     async lay_huonggiao_theo_quytrinh(axios, quytrinh_id, tthd_id){ 
        var data = []; 
        var req = {
            quytrinh_id: quytrinh_id*1,
            tthd_id: tthd_id*1,
        }
        await  API.lay_huonggiao_theo_quytrinh(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            } 
          }); 
         return data;
     }, 
     async lay_huonggiao_theo_luong_id(axios, luongId){ 
        var huongiao = null;
        var postData = { "luong_id": luongId };
        await  API.lay_huonggiao_theo_luong_id(axios, postData).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                if(res.data.data && res.data.data.length > 0)
                    huongiao = res.data.data[0] 
            }
          }); 
         return huongiao;
     },
     async lay_ds_quytrinh_v2(axios, dsloaihd_id, tthd_id, dsdichvuvt_id){ 
        var data = []; 
        var req = {
            "dsloaihd_id": dsloaihd_id,
            "tthd_id": tthd_id,
            "kieu": 0,
            "dichvuvt_id": dsdichvuvt_id,
          }
        await  API.sp_lay_ds_quytrinh_v2(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            } 
          }); 
         return data;
     }, 
     async lay_ds_diachi_theo_hdtbid(axios, hdtb_id){ 
        var data = []; 
        var req = {
            params: {
                hdtbId: hdtb_id
            }
          }
        await  API.lay_ds_diachi_theo_hdtbid(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            } 
          }); 
         return data;
     }, 
     async CapnhatToaDoHDTB(axios, Lat, Lng, hdtb_id, mode){ 
        var data = []; 
        var req = {
            "vlat": Lat,
            "vlng": Lng,
            "vhdtb_id": hdtb_id,
            "vmode": mode
        }
        await  API.sp_capnhattoadohdtb(axios, req).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            } 
          }); 
         return data;
     }, 

     //#region HRM
     async Find_Employees_By_EmailOrPhone(axios, email){ 
        var data = null; 
        var req = {
            params: {"email": email,}           
        }
        await  API.get_info_by_email(axios, req).then(res=>{  
            console.log(res.data);
            if (res && res.data && res.data.errorCode == 0) {
                data = res.data.data;
            }
          }); 
        return data;
     }, 
     //#endregion
     ChuanHoaTen(ten) {
        if (!ten) return '';
        var ten_new = '';
        var str_con = ten.trim();
        var str_xl = "";
        var k = 0;
        var found = true;
        if (str_con.length < 1) return;
        str_con = str_con.replaceAll(/\s+/g, ' ');
        try {
            while (found) {
                k = str_con.indexOf(' ');
                if (k > 0) {
                    str_xl = str_con.substring(0, k);
                    str_con = str_con.substring(k + 1);
                    str_xl = this.ChuyenChuHoa(str_xl);
                    if (ten_new != "")
                        ten_new += " " + str_xl;
                    else ten_new = str_xl;
                    found = true;
                } else found = false;
            }
            if (str_con != "") {
                str_xl = str_con;
                str_xl = this.ChuyenChuHoa(str_xl);
                if (ten_new != "")
                    ten_new = ten_new + " " + str_xl;
                else
                    ten_new = str_xl;
            }
            return ten_new;
        } catch (e) {
            return ten;
        }
    }, 
    LowerCasePropertyList(obj) {
        if(!obj) return [];
        return obj.map( function( item ){
            for(var key in item){
                var upper = key.toLowerCase();
                // check if it already wasn't uppercase
                if( upper !== key ){
                    item[ upper ] = item[key];
                    delete item[key];
                }
            }
            return item;
        });
    },
    UpperCasePropertyList(obj) {
        if(!obj) return [];
        return obj.map( function( item ){
            for(var key in item){
                var upper = key.toUpperCase();
                // check if it already wasn't uppercase
                if( upper !== key ){
                    item[ upper ] = item[key];
                    delete item[key];
                }
            }
            return item;
        });
    },
    showException($this, ex){
        console.error("showException", ex);
        if(ex.message){
            let err = ex.message
            if (ex.message_detail) {
                err += "\n" + ex.message_detail
            }
            $this.$toast.error(err); // + (ex.message_detail ? "\n" + ex.message_detail : "")
        }else if(ex.response && ex.response){
            $this.$toast.error(ex.response);
        }else if(ex.data && ex.data.message){
            let err = ex.data.message
            if (ex.data.message_detail) {
                err += "\n" + ex.data.message_detail
            }
            $this.$toast.error(err);
        }else if(ex.statusText && ex.statusText){
            $this.$toast.error(ex.status + ' ' + ex.statusText);
        }else if(ex != "No Content"){
            $this.$toast.error(ex);
        } 
    },
    async GetKey(axios, keyname){
        var key = 0;
        await API.post_get_keys(axios, {"keyname": keyname}).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                key= response.data.data;  
            }
        });

        return key;
    },

    GetData: function (response) {

        if(response.data.errorCode && response.data.errorCode != "" && response.data.faultString && response.data.faultString != ""){ 
          throw response.data.faultString
        }
  
        if (response.data.error === 200 || response.data.error === '200') {
          if (response.data.error_code === 'BSS-00000000' || response.data.message === "Success")
            return response.data.data
          else{
            commonFn.showException(this, response.data.message);
            return response.data.data
          }
        } else {
          console.log(response.data.error_code)
        }
        return null
      },
}


    