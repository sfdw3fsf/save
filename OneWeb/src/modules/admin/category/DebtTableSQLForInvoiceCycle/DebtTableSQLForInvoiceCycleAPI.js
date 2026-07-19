export default{
    sp_lay_ds_taobang: async (axios, data) => axios.post('/web-quantri/taobangno/sp_lay_ds_taobang', data),

    sp_ghilai_ds_taobang: async (axios, data) => axios.post('/web-quantri/taobangno/sp_ghilai_ds_taobang', data),

    sp_del_ds_taobang: async (axios, data) => axios.post('/web-quantri/taobangno/sp_del_ds_taobang', data),

    sp_lay_ds_taobang_col: async (axios, tenbang) => axios.post('/web-quantri/taobangno/sp_lay_ds_taobang_col', {
        tenbang:tenbang
    }),
    
    sp_capnhat_taobang_col: async (axios, data) => axios.post('/web-quantri/taobangno/sp_capnhat_taobang_col', data),

    sp_lay_ds_taobang_cmd: async (axios, module_id, tenbang) => axios.post('/web-quantri/taobangno/sp_lay_ds_taobang_cmd', {   
        module_id: module_id,
        tenbang: tenbang
    }),

    sp_lay_ds_taobang_cmd_all: async (axios, thutu, module_id, tenbang) => axios.post('/web-quantri/taobangno/sp_lay_ds_taobang_cmd_all', {   
        thutu: thutu,
        module_id: module_id,
        tenbang: tenbang
    }), 

    sp_del_taobang_cmd: async (axios, thutu, module_id, tenbang) => axios.post('/web-quantri/taobangno/sp_del_taobang_cmd', {   
        thutu: thutu,
        module_id: module_id,
        tenbang: tenbang
    }), 
    
    sp_ghilai_taobang_cmd: async (axios, data) => axios.post('/web-quantri/taobangno/sp_ghilai_taobang_cmd', data),
    
}