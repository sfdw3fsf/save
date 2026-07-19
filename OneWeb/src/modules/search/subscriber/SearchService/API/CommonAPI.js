import SearchServiceAPI from './SearchServiceAPI' 
var axios = null;
export default{
    setAxios(axios){ 
    },
    async lay_db_theo_matb_1(axios, matb, donvi_id){  
        var postData = {
            "donvi_dl_id": donvi_id,
            "ma_tb": matb
        };
        var ds = [];
        await  SearchServiceAPI.lay_db_theo_matb_1(axios, postData).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                ds = res.data.data
            }
        }); 
        return ds;
    }, 
    async lay_ds_sudung_dichvu(axios, loai_id, matb_str, thuebao_id){  
        var postData = {
            "loai_id": loai_id,
            "matb_str": matb_str,
            "thuebao_id": thuebao_id
        };
        var dichvu = {};
        await  SearchServiceAPI.sp_lay_ds_sudung_dichvu(axios, postData).then(res=>{  
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                dichvu = res.data.data;
            }
        }); 
        return dichvu;
    }, 
}


    