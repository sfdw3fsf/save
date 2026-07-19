export default {
   getDsTbKhoiphucThanhlyTamdung: async (axios, vtapob_id, vtungay, vdenngay) => axios.get('/web-tratruoc/obnhancong/sp_lay_ds_thuebao_ob?tapob_id='+vtapob_id+'&tu_ngay='+vtungay+'&den_ngay='+vdenngay),
   getDsThongtinThuebaoOB: async (axios, vsdt,vtapob_id) => axios.get('/web-tratruoc/obnhancong/sp_lay_thongtin_tb_ob?sdt_ob='+vsdt+'&tapob_id='+vtapob_id),
   getKiemtraSdtOB: async (axios, vsdtob) => axios.get('/web-tratruoc/obnhancong/sp_kiemtra_sdtob?sdt_ob='+vsdtob),
   getDanhsachUserOB: async (axios, data) => axios.get('/web-tratruoc/obnhancong/sp_lay_ds_nguoidung_ipcc'),
   getcboNhanVienDinhmuc: async (axios, donviId,thangKt,cTrinh) => axios.get('/web-tratruoc/ttkdhcmservice/dsNvDinhMuc?donvi_id='+donviId+'&thang_kt='+thangKt+'&chuongtrinh='+cTrinh),
   getcboDanhsachTapOb: async (axios, data) => axios.get('/web-tratruoc/ttkdhcmservice/sp_lay_ds_tapob'),
   getcboTrangthaiKetnoi: async (axios, data) => axios.get('/web-tratruoc/ob_ghtt/sp_lay_tt_ketnoi'),
   getcboTracuuOb: async (axios, data) => axios.get('/web-tratruoc/obnhancong/sp_lay_doituong_tracuu'),
   getThuchienCuocgoi: async (axios, sdtOb, dausoOb ) => axios.get('/web-tratruoc/obnhancong/sp_lay_tt_thuchien_cuocgoi?sdt_ob='+sdtOb+'&dauso_ob='+dausoOb),

   // 
   poscboKetquaOb: async (axios, request) => axios.post('/web-tratruoc/obnhancong/sp_ds_kqob_nv', request),
   postKetquaOBNC: (axios, ds_para) => axios.post('/web-tratruoc/obnhancong/sp_luu_ket_qua_ob', ds_para),
   postAgentState: async (axios, request) => axios.post('/web-tratruoc/obnhancong/sp_change_agent_state', request),
   postThongtinTracuu: async (axios, request) => axios.post('/web-tratruoc/obnhancong/sp_lay_lichsu_ob', request),
   postCallOutbound: async (axios, request) => axios.post('/web-tratruoc/obnhancong/sp_call_outbound', request),


}
