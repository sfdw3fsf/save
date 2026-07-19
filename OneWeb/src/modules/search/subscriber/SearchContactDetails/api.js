export default{
    getDanhSachDonVi1: (axios, data) => axios.get('/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=2', data),
    getDanhSachDonVi2: (axios, data) => axios.get('/web-thuno/api/thu-no/common/don-vi/loai-don-vi', { params: data }),
    getDanhSachDonViQuanLy: (axios, data) => axios.get('/web-thuno/api/thu-no/lay-danh-sach-don-vi-quan-ly', { params: data }),
    
    getKyCuocHienHanh: (axios) => axios.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh'),
    getChuKyNoTheoKyCuoc: async (axios, data) => axios.get('/web-thuno/api/thu-no/common/chu-ky-no/ky-cuoc/' + data),

    //UR2.8.012_044
    //web-quantri/danhmuc/ds_thamso_md/{kieu_id} ---kieu_id = 0
    lay_ds_thamso_md: (axios, kieu_id) => axios.get('web-quantri/danhmuc/ds_thamso_md/'+kieu_id ),
    
    get_donvi_ql_tt: async (axios, data) => axios.get('/web-quantri/danhmuc/donvi_ql_tt/', data),
    //GET: web-tracuu/danhba/lay_ds_dichvu_vt_by_kieugoi?kieugoiId=1

    getDanhSachDichVu: (axios, params) => axios.get('/web-tracuu/phattrien/ds_dichvu', {params}),
    getDanhSachLoaiHopDong: (axios, params) => axios.get('/web-tracuu/phattrien/ds_loai_hd', {params}),
    getDanhSachDonVi: (axios, params) => axios.get('/web-tracuu/phattrien/ds_don_vi', {params}),    

    getLoaiHinh: (axios, data) => axios.post('/web-hopdong/danhmuc/loaihinh_thuebao', data),
    //web-tracuu/danhba/lay_ds_tt_theo_loaitb?loaitbId=24&loaihdId=7
    lay_ds_tt_theo_loaitb: (axios, params) => axios.get('/web-tracuu/danhba/lay_ds_tt_theo_loaitb', {params}),
    //POST: web-tracuu/danhba/lay_ds_hienthi_thongtin_dbkh
    lay_ds_hienthi_thongtin_dbkh: (axios, data) => axios.post('/web-tracuu/danhba/lay_ds_hienthi_thongtin_dbkh', data), 
    //"POST: web-tracuu/danhba/thongtin_dbtb
    thongtin_dbtb: (axios, data) => axios.post('/web-tracuu/danhba/thongtin_dbtb', data),
    lay_ds_muccuoc_thuebao: (axios, data) => axios.post('/web-tracuu/danhba/lay_ds_muccuoc_thuebao', data), 
    MUCCUOC_TB: (axios, data) => axios.get('/web-quantri/quan-ly-danh-ba/lay-danhmuc/MUCCUOC_TB', data), 
 
    ///web-hopdong/thaydoithongtinkhuyenmai/lay_ds_cacloai_danhmuc
    lay_ds_cacloai_danhmuc: async (axios) => axios.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_cacloai_danhmuc'),
    CSS_DICHVU_VT: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    //UR2.8.012_001
    //web-quantri/danhmuc-chung/CSS_LOAI_HD 
    CSS_LOAI_HD: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD'),

    //UR2.8.012_002		Lấy danh sách loại cáp CD
    //UR2.8.012_003		Lấy danh sách loại cáp ADSL
    //UR2.8.012_004		Lấy danh sách loại cáp IMS 
    //UR2.8.012_005
    //web-quantri/danhmuc-chung/CABMAN_LOAI_CAP 
    CABMAN_LOAI_CAP: (axios) => axios.get('/web-quantri/danhmuc-chung/CABMAN_LOAI_CAP'),
    
    //UR2.8.012_007		lấy danh sách đối tượng
    //web-quantri/danhmuc-chung/CSS_DOITUONG
    CSS_DOITUONG: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DOITUONG'),

    //UR2.8.012_008		Lấy danh sách khách hàng ớn
    //web-quantri/danhmuc-chung/CSS_LOAI_KHL Loại khách hàng lớn
    CSS_LOAI_KHL: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_KHL'),

    //UR2.8.012_009		Lấy danh sách ngân hàng
    //"web-quantri/danhmuc-chung/CSS_NGANHANG
    //UR2.8.012_010		Lấy danh sách Ngân hàng của Khách hàng
    CSS_NGANHANG: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_NGANHANG'),

    //UR2.8.012_011		Lấy danh sách hình thức thanh toán
    //"web-quantri/danhmuc-chung/QLTN_HINHTHUC_TT 
    QLTN_HINHTHUC_TT: (axios) => axios.get('/web-quantri/danhmuc-chung/QLTN_HINHTHUC_TT'),

    //UR2.8.012_012		Lấy đanh sách Loại KH
    //"web-quantri/danhmuc-chung/CSS_LOAI_KH
    CSS_LOAI_KH: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_KH'),

    //UR2.8.012_013		Lấy Danh sách Gói cưới ADSL
    //"web-quantri/danhmuc-chung/CSS_TOCDO_ADSL
    CSS_TOCDO_ADSL: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_ADSL'),

    //getTocDo_ADSL: async (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_tocdo_adsl', data),
    getTocDo_ADSL: async (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_tocdo_adsl_v3', data),
    //UR2.8.012_015		Lấy danh sách Mức cước
    //"web-quantri/danhmuc-chung/CSS_MUCCUOC
    CSS_MUCCUOC: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_MUCCUOC'),

    //UR2.8.012_016		Lây dánh sách Trang bị
    //"web-quantri/danhmuc-chung/CSS_TRANGBI 
    CSS_TRANGBI: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGBI'),

    //UR2.8.012_017		Lấy danh sách Đơn vị QL
    //"web-quantri/danhmuc-chung/ADMIN_DONVI_QL
    ADMIN_DONVI_QL: (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DONVI_QL'),

    
    //UR2.8.012_018		Lấy danh sách Đơn vị quản lý (tab thuê bao)
    //"web-quantri/danhmuc-chung/ADMIN_DONVI_QL 

    //UR2.8.012_019		Lấy Danh sách Loại kênh
    //"web-quantri/danhmuc-chung/CSS_LOAI_KENH 
    CSS_LOAI_KENH: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_KENH'),

    //UR2.8.012_021		Lấy danh sách tốc độ kênh
    //"web-quantri/danhmuc-chung/CSS_TOCDO_KENH 
    CSS_TOCDO_KENH: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH'),

    //UR2.8.012_024		Lấy danh sách loại Modem
    //"web-quantri/danhmuc-chung/CSS_LOAI_MODEM 
    CSS_LOAI_MODEM: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_MODEM'),

    //UR2.8.012_035		Lấy danh sách loại TB TSL
    //web-quantri/danhmuc-chung/CSS_LOAITBI_TSL
    CSS_LOAITBI_TSL: (axios) => axios.get('/web-quantri/quan-ly-danh-ba/lay-danhmuc/LOAITBI_TSL'),
    


    //UR2.8.012_030		Lấy danh sách Bras ADSL
    //"web-quantri/danhmuc-chung/CSS_BRAS
    CSS_BRAS: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_BRAS'),
    getBras: (axios, data) =>axios.post("/web-hopdong/thaydoiloaihinhtb/bras_v2", data),
    
    getDslam: (axios, data) =>axios.get(`/web-hopdong/thaydoiloaihinhtb/lay_ds_dslam?brasId=${data.brasId}&kieu=${data.kieu}`),
    //UR2.8.012_037		Lấy danh sách thời hạn MEGAWAN
    //"web-quantri/danhmuc-chung/CSS_THOIHAN 
    CSS_THOIHAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_THOIHAN'),

    //UR2.8.012_038		Lấy danh sách chủ quán TSL
    //"web-quantri/danhmuc-chung/CSS_CHUQUAN 
    CSS_CHUQUAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_CHUQUAN'),

    //TODO
    //UR2.8.012_040		Danh sách đơn vị đầu
    //UR2.8.012_042		Lấy danh sách đơn vị cuối
    //GET: web-tracuu/danhba/lay_ds_donvi_by_loaidv?donviId=32&loaidvId=5
    lay_ds_donvi_by_loaidv: (axios, params) => axios.get('/web-tracuu/danhba/lay_ds_donvi_by_loaidv', {params}),


    //UR2.8.012_025		Lấy danh sách đói tác
    //"web-quantri/danhmuc-chung/CSS_DOITAC 
    CSS_DOITAC: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DOITAC'),

    //UR2.8.012_029		Lấy danh sách loại giấy tờ cử Khách Hàng
    //"web-quantri/danhmuc-chung/CSS_LOAI_GT
    CSS_LOAI_GT: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_GT'),

    //UR2.8.012_032		Lấy danh sách dân tộc
    //"web-quantri/danhmuc-chung/CSS_DANTOC 
    CSS_DANTOC: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DANTOC'),

    //UR2.8.012_033		Lấy danh sách quốc tịch
    //"web-quantri/danhmuc-chung/CSS_QUOCTICH 
    CSS_QUOCTICH: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_QUOCTICH'),

    //UR2.8.012_034		OLaays danh sách ngành nghề
    //"web-quantri/danhmuc-chung/CSS_NGANHNGHE 
    CSS_NGANHNGHE: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_NGANHNGHE'),

    ///web-quantri/quan-ly-danh-ba/lay-danhmuc/THIETBI_IMS
    THIETBI_IMS: (axios) => axios.get('web-quantri/quan-ly-danh-ba/lay-danhmuc/THIETBI_IMS'),

    post_lay_danhmuc_lapmoi_tt_chung_v8 : async (axios, data) => axios.post('/web-danhba/lapdatmoi/lay_danhmuc_lapmoi_tt_chung_v8', data),

    //UR2.8.012_054
    // web-tracuu/danhba/lay_tt_nhanvien_quanly?kieuId=1&id=10
    lay_tt_nhanvien_quanly: (axios, params) => axios.get('web-tracuu/danhba/lay_tt_nhanvien_quanly', {params}),
    //UR2.8.012_056
    //"POST: /danhba/dbtt_theo_khid
    dbtt_theo_khid: (axios, params) => axios.get('web-tracuu/danhba/dbtt_theo_khid', {params}),

    //UR2.8.012_057
    //GET: web-tracuu/danhba/lay_ds_dbkh_theo_makh?maKh=HPG-03-720201
    lay_ds_dbkh_theo_makh: (axios, params) => axios.get('web-tracuu/danhba/lay_ds_dbkh_theo_makh', {params}),
    //UR2.8.012_066
    //web1-tracuu/danhba/lay_ds_dbkh_theo_matb?maTb=son2403&dichvuId=4
    lay_ds_dbkh_theo_matb: (axios, params) => axios.get('web-tracuu/danhba/lay_ds_dbkh_theo_matb', {params}),

    //ur2.8.12.75
    //web-tracuu/danhba/lay_ds_hienthi_dbtt?thanhtoanId=1189404&maTb=HPG-03-666387
    lay_ds_hienthi_dbtt: (axios, params) => axios.get('web-tracuu/danhba/lay_ds_hienthi_dbtt', {params}),

    //UR2.8.012_076
    //GET: web-tracuu/danhba/lay_ds_thanhtoan_theo_id?thanhtoanId=670956
    lay_ds_thanhtoan_theo_id: (axios, params) => axios.get('web-tracuu/danhba/lay_ds_thanhtoan_theo_id', {params}),

    ///web-hopdong/ThayDoiHopDong/lay_ds_danhba_tt_theo_khachhang_id  khachhang_id: this.khachhang_id
    //lấy ds thanh toán theo khachsh hang id
    lay_ds_danhba_tt_theo_khachhang_id: (axios, params) => axios.get('web-hopdong/ThayDoiHopDong/lay_ds_danhba_tt_theo_khachhang_id', {params}),
    
    ///web-hopdong/ThayDoiHopDong/lay_ds_danhba_tt_theo_khachhang_id  khachhang_id: this.khachhang_id
    //lấy ds thuê bao theo khachsh hang id
    lay_ds_danhba_tb_theo_khachhang_id: (axios, params) => axios.get('web-hopdong/ThayDoiHopDong/lay_ds_danhba_tb_theo_khachhang_id', {params}),
    
    //UR2.8.12.77
    //web-tracuu/danhba/lay_tt_dbtb_by_id?thuebaoId=2329572
    lay_tt_dbtb_by_id: (axios, params) => axios.get('web-tracuu/danhba/lay_tt_dbtb_by_id', {params}),

    //UR2.8.012_097_006_04 lấy danh sách DB_CD by ID
    //web-tracuu/danhba/lay_ds_db_cd_by_tbid?thuebaoId=123978
    lay_ds_db_cd_by_tbid: (axios, params) => axios.get('web-tracuu/danhba/lay_ds_db_cd_by_tbid', {params}),

    //web-tracuu/danhba/lay_ds_danhba_theo_dichvu_va_thuebao?thuebaoId=177311&dichvuvtId=11
    lay_ds_danhba_theo_dichvu_va_thuebao: (axios, params) => axios.get('web-tracuu/danhba/lay_ds_danhba_theo_dichvu_va_thuebao', {params}),

    //UR2.8.012_097_006_05
    //web-tracuu/danhba/lay_ds_db_dd_by_tbid?thuebaoId=1469947
    lay_ds_db_dd_by_tbid: (axios, params) => axios.get('web-tracuu/danhba/lay_ds_db_dd_by_tbid', {params}),

    //UR2.8.012_097_006_06
    //GET: web-tracuu/danhba/lay_ds_db_gp_by_tbid?thuebaoId=1449082
    lay_ds_db_gp_by_tbid: (axios, params) => axios.get('web-tracuu/danhba/lay_ds_db_gp_by_tbid', {params}),

    //UR2.8.012_097_006_07
    //GET: web-tracuu/danhba/lay_ds_db_tsl_by_tbid?thuebaoId=504109
    lay_ds_db_tsl_by_tbid: (axios, params) => axios.get('web-tracuu/danhba/lay_ds_db_tsl_by_tbid', {params}),

    //GET: web-tracuu/danhba/lay_ds_db_adsl_by_tbid?thuebaoId=415303
    //UR2.8.012_097_006_08
    lay_ds_db_adsl_by_tbid: (axios, params) => axios.get('web-tracuu/danhba/lay_ds_db_adsl_by_tbid', {params}),


    //UR2.8.012_046
    ///POST: web-tracuu/danhba/thongtin_dbtb
    get_thongtin_dbtb: (axios, data) => axios.post('/web-tracuu/danhba/thongtin_dbtb', data),

    //UR2.8.012_048		Hiển thi thông tin dịch vụ khác
    //web-tracuu/danhba/lay_tt_dichvu_khac?dvkhacId=90
    lay_tt_dichvu_khac: (axios, params) => axios.get('web-tracuu/danhba/lay_tt_dichvu_khac', {params}),

    //UR2.8.012_049_01		Lấy thông tin danh bạ theo dịch vụ và thuê bao ID
    //GET: web-tracuu/danhba/lay_tt_dichvu_codinh?thuebaoId=182702&neId=120598
    lay_tt_dichvu_codinh: (axios, params) => axios.get('web-tracuu/danhba/lay_tt_dichvu_codinh', {params}),
    //=>"data":{"donvi_id":222,"ten_dv":"Anh Dũng"}

    //GET: web-tracuu/danhba/lay_tt_dichvu_adsl?thuebaoId=3021665&congngheId=3
    lay_tt_dichvu_adsl: (axios, params) => axios.get('web-tracuu/danhba/lay_tt_dichvu_adsl?thuebaoId='+params.thuebaoId+'&congngheId='+params.congngheId),
    //=>data":{"congnghe":"GPON","donvi_id":10385}

    //UR2.8.012_050
    //"POST: web-tracuu/danhba/lay_tt_hienthi_diachi_tsl
    lay_tt_hienthi_diachi_tsl: (axios, data) => axios.post('/web-tracuu/danhba/lay_tt_hienthi_diachi_tsl', data),

    //UR2.8.012_056_01
    //"POST: web-tracuu/danhba/lay_ds_dbtb_theo_khid
    lay_ds_dbtb_theo_khid: (axios, data) => axios.post('/web-tracuu/danhba/lay_ds_dbtb_theo_khid', data),
    lay_ds_dbtb_theo_khid_v2: (axios, data) => axios.post('/web-tracuu/tracuu_th/lay_ds_dbtb_theo_khid_v2', data),
    lay_ds_dbtb_theo_khid_page: async (axios, data) => axios.post('/web-tracuu/tracuu_th/lay_ds_dbtb_theo_khid_page', data),
    lay_ds_nhanvien_dbtb_by_id: (axios, data) => axios.post('/web-tracuu/danhba/lay_ds_nhanvien_dbtb_by_id', data),
    
    ///web-quantri/quan-ly-danh-ba/thong-tin-thue-bao-mo-rong
    getDsThongTinMoRong: (axios, data) => axios.post('/web-quantri/quan-ly-danh-ba/thong-tin-thue-bao-mo-rong', data),
    //GET: web-tracuu/danhba/lay_ds_donvi_theo_loaidv?donviId=186&loaidvId=5
    lay_ds_donvi_theo_loaidv: (axios, params) => axios.get('/web-tracuu/danhba/lay_ds_donvi_theo_loaidv', {params}),
    // web-tracuu/danhba/lay_tt_donvi_theo_thuebao?thuebaoId=182702
    lay_tt_donvi_theo_thuebao: (axios, params) => axios.get('/web-tracuu/danhba/lay_tt_donvi_theo_thuebao', {params}),
    ///web-hopdong/hopdong/lay_ds_danhba_megawan {thueBaoId:this.thuebao_id}
    lay_ds_danhba_megawan: (axios, params) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_megawan', {params}),

    lay_ds_danhba_cntt: (axios, params) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_cntt', {params}),

    ///web-quantri/quan-ly-danh-ba/danhsach-thuoctinh-capnhat-danhba?ploaitbID=${loaithuebao}&pThueBaoID=${thuebao_id}&pTenBang=HDTB_CNTT&pOptList=00000

    //GET: web-tracuu/danhba/lay_tt_ca_csr_by_tbid?thuebaoId=2976143
    lay_tt_ca_csr_by_tbid: (axios, params) => axios.get('/web-tracuu/danhba/lay_tt_ca_csr_by_tbid', {params}),

    //GET: web-tracuu/danhba/lay_tt_ca_token_by_tbid?thuebaoId=2976143
    lay_tt_ca_token_by_tbid: (axios, params) => axios.get('/web-tracuu/danhba/lay_tt_ca_token_by_tbid', {params}),

    lay_ds_diachi_theo_dbtbid: async (axios, thuebao_id) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid',{
        in_thuebao_id: thuebao_id
    }),
    hienthi_ds_thietbi_idc: (axios, params) => axios.get('web-thicong/host/hienthi_ds_thietbi_idc?thuebao_id='+params.thuebao_id),

}   

