import API from './DeclareMPLSAPI' 
export default{
    async fn_kiemtra_hoancong_hoancongtsl(axios, vds){ 
        //REQ: {
        //       "vds": "[{\"LUONG_ID\": 1322,\"HDKH_ID\": 452,\"HDTB_ID\": 2003394, \"MA_TB\": \"thuanvb80\", \"PHIEU_ID\": 147202,\"TEN_DV\": \"Viễn Thông Test\"}]"
        //     }
        var data = 0;
        await API.fn_kiemtra_hoancong_hoancongtsl(axios, {
          "vds": JSON.stringify(vds), 
        }).then((response) => {
            if (response && response.data && response.data.data && response.data.error && response.data.error === '200') {
               data = response.data.data;  
            }
            // if(response.data.message != "Success"){
            //     throw response.data.message;
            // }
        });
        return data;
    },

    async fn_thuchien_hoancong_hoancongtsl(axios, vds, vkieu){ 
        //REQ: 
        // {
        //     "vds": "[{\"HDTB_ID\": 2003394,\"LUONG_ID\": 1322,\"PHIEU_ID\": 147202,\"NHANVIEN_ID\": 452,\"NGUOIGV_ID\": 452, \"NGAY_GV\": '07/09/2021 00:00:00', \"NGAY_BG\": '07/09/2021 00:00:00',\"LOAIHD_ID\": 1,\"MA_TB\": \"thuanvb80\" }]",
        //     "vkieu": 2
        // }
        var data = 0;
        await API.fn_thuchien_hoancong_hoancongtsl(axios, {
          "vds": JSON.stringify(vds), 
          "vkieu": vkieu,
        }).then((response) => {
            if (response && response.data && response.data.data && response.data.error && response.data.error === '200') {
               data = response.data.data; 
            }
            // if(response.data.message != "Success"){
            //     throw response.data.message;
            // }
        });
        return data;
    },
    async fn_co_thuebao_didong(axios, thanhtoan_id){ 
        // REQ
        // {
        //     "thanhtoan_id": 1219856
        // }
        var data = 0;
        await API.fn_co_thuebao_didong(axios, {
          "thanhtoan_id": thanhtoan_id, 
        }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
               data = response.data.data; 
            }
            if(response.data.message != "Success"){
                throw response.data.message;
            }
        });
        return data*1;
    },
    async fn_theoloaihd_hoancong_hoancongtsl(axios, vds, vhuonggiaodich){ 
        // REQ
        // {
        //     "vhuonggiaodich": 692,
        //     "vds": "[{\"HDTB_ID\": 3262308,\"LUONG_ID\": 1322,\"PHIEU_ID\": 5711095,\"NHANVIEN_ID\": 452,\"NGAY_BG\": \"07/09/2021 00:00:00\",\"LOAIHD_ID\": 1,\"MA_TB\": \"quang67905\",\"DONVI_ID\": 900,\"MAY_CN\": \"TESST\",\"NGUOI_CN\": \"TEST\",\"IP_CN\": \"TEST IP\",\"DICHVUVT_ID\": 4,\"DAUCUOI_ID\": NULL,\"HUONGGIAO_ID\": 692}]"
        // }
        var data = 0;
        await API.fn_theoloaihd_hoancong_hoancongtsl(axios, {
          "vds": JSON.stringify(vds), 
          "vhuonggiaodich": vhuonggiaodich
        }).then((response) => {
            if (response && response.data && response.data.data && response.data.error && response.data.error === '200') {
               data = response.data.data; 
            }
            // if(response.data.message != "Success"){
            //     throw response.data.message;
            // }
        });
        return data;
    },
    async LAY_DS_DIACHI_THEO_HDKH_ID(axios, hdkh_id){  
        var data = null;
        await API.lay_ds_diachi_theo_hdkh_id(axios, {
          "hdkh_id": hdkh_id,  
        }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
               data = response.data.data; 
            }
            if(response.data.message != "Success"){
                throw response.data.message;
            }
        });
        return data;
    },
    async LAY_DS_DIACHI_THEO_HDTT_ID(axios, hdkh_id){  
        var data = null;
        await API.lay_ds_diachi_theo_hdtt_id(axios, {
          "hdkh_id": hdkh_id,  
        }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
               data = response.data.data; 
            }
            if(response.data.message != "Success"){
                throw response.data.message;
            }
        });
        return data;
    },

    async fn_kethuc_hoancong_hoancongtsl(axios, vds){  
        var data = null;
        await API.fn_kethuc_hoancong_hoancongtsl(axios, {
          "vds": JSON.stringify(vds),  
        }).then((response) => {
            if (response && response.data && response.data.data && response.data.error && response.data.error === '200') {
               data = response.data.data; 
            }
            if(response.data.message != "Success"){
                throw response.data.message;
            }
        });
        return data;
    },
    
}


    