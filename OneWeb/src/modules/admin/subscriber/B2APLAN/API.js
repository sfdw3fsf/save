export default {
    SP_LAY_DS_DANHMUC_B2A: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/lay_ds_kenh_tiepcan', {
        vphanvung_id: data.phanvung_id,
        vkieu: data.kieu
    }),

    SP_LAY_DS_KENH_TIEPNHAN_B2A: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/lay_ds_kenh_tiepcan'),
    SP_LAY_DS_TINHTRANG_B2A: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/lay_ds_tinhtrang'),

    SP_LAY_DS_CHIENDICH_B2A: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/lay_ds_chien_dich_b2a', {
        vphanvung_id: data.phanvung_id,
        vnhanvien_id: data.nhanvien_id
    }),

    SP_CAPNHAT_CHIENDICH_B2A: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/capnhat_chien_dich_b2a', {
        js_chiendich_b2a: data.json_chiendich,
        vnhanvien_id: data.nhanvien_id
    }),

    SP_XOA_CHIENDICH_B2A: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/xoa_chien_dich_b2a', {
        vcd_id: data.cd_id,
        vnhanvien_id: data.nhanvien_id
    }),
    
    SP_LAY_DS_TINH: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/lay_ds_tinh'),
    SP_LAY_DS_DICHVU: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/lay_ds_dichvu_vt'),
    SP_LAY_DS_LOAIHINH_TB: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/lay_ds_loaihinh_tb',{ 
        vdichvuvt_id: data.dichvu_id
    }),
    SP_LAY_DS_THANHVIEN_KENH: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/lay_ds_loainv'),

    JS_TINH: [{"TINH_ID":2,"TENTINH":"Bà Rịa Vũng Tàu"},{"TINH_ID":3,"TENTINH":"Bắc Giang"},{"TINH_ID":4,"TENTINH":"Bắc Cạn"}],
    JS_LOAITB: [
        {
          "loaihinh_tb": "Điện thoại cố định",
          "loaitb_id": 1
        },
        {
          "loaihinh_tb": "Máy FAX",
          "loaitb_id": 2
        },
        {
          "loaihinh_tb": "Trung kế thường",
          "loaitb_id": 3
        },
        {
          "loaihinh_tb": "Trung kế tương tự",
          "loaitb_id": 4
        },
        {
          "loaihinh_tb": "Trung kế 2M",
          "loaitb_id": 5
        },
        {
          "loaihinh_tb": "Tổng đài",
          "loaitb_id": 6
        },
        {
          "loaihinh_tb": "Điện thoại thẻ",
          "loaitb_id": 7
        },
        {
          "loaihinh_tb": "VPabx",
          "loaitb_id": 8
        },
        {
          "loaihinh_tb": "ISDN 2B+D cáp quang",
          "loaitb_id": 14
        },
        {
          "loaihinh_tb": "ISDN 30B+D cáp đồng",
          "loaitb_id": 15
        },
        {
          "loaihinh_tb": "ISDN 2B+D cáp đồng",
          "loaitb_id": 16
        },
        {
          "loaihinh_tb": "ISDN 30B+D cáp quang",
          "loaitb_id": 17
        },
        {
          "loaihinh_tb": "Cố định tỉnh khác",
          "loaitb_id": 36
        },
        {
          "loaihinh_tb": "Centrex",
          "loaitb_id": 60
        },
        {
          "loaihinh_tb": "Điện thoại vô tuyến",
          "loaitb_id": 108
        }
      ],
    JS_KENH: [{"LOAINV_ID":20,"TEN":"Giải quyết khiếu nại"},{"LOAINV_ID":44,"TEN":"Giao BSC"},{"LOAINV_ID":84,"TEN":"Giao dịch khách hàng"},{"LOAINV_ID":110,"TEN":"Freedoo"}],
}