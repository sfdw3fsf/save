export default {
    /**
     * Lấy danh sách các tỉnh/thành phố
     * @param {*} axios được truyền từ global (this.axios)
     */
    get_ds_Tinh: async (axios) => axios.post('/web-hopdong/bss_group1/sp_lay_ds_tinh_diadanh', {}),

   /**
    * Lấy danh sách quận/huyện của một tỉnh.
    * @param {*} axios được truyền từ global (this.axios)
    * @param {*} body { p_tinh_id: "tinh_id" }
    */
    get_ds_QuanHuyen: async (axios, body) => axios.post('/web-hopdong/bss_group1/sp_lay_ds_quan_theo_tinh', body),

    /**
     * Lấy danh sách phường/xã của một quận/huyện
     * @param {*} axios được truyền từ global (this.axios) 
     * @param {*} body {p_quan_id: "quan_id"}
     */
    get_ds_PhuongXa: async (axios, body) => axios.post('/web-hopdong/bss_group1/sp_lay_ds_phuong_theo_quan', body),

    /**
     * Lấy danh sách đường/phố/Ấp/Khu của một phường/xã
     * @param {*} axios được truyền từ global (this.axios)  
     * @param {*} body {p_phuong_id: "phuong_id", p_nhom_id: "Nhóm_ID"}
     * Trong đó: Nhóm_ID -- 1: Phố, 2: Ấp, 3: Khu }
     */
    get_ds_PhoApKhu: async (axios, body) => axios.post('/web-hopdong/bss_group1/sp_lay_ds_pho_ap_khu', body),

    // Lấy danh sách Đặc điểm
    /**
     * Lấy danh sách Đặc điểm của một phường và quận
     * @param {*} axios được truyền từ global (this.axios)   
     * @param {*} body {p_phuong_id: "phuong_id", p_quan_id: "quan_id"}
     */
    get_ds_Dacdiem: async (axios, body) => axios.post('/web-hopdong/bss_group1/lay_ds_dacdiem', body),

    /**
     * Lấy danh sách loại đơn vị
     * @param {*} axios được truyền từ global (this.axios)  
     */
    get_ds_LoaiDonvi: async (axios) => axios.post('/web-hopdong/bss_group1/sp_lay_ds_loaidvi_px', {}),

    /**
     * Tra cứu danh sách khu vực
     * @param {*} axios được truyền từ global (this.axios) 
     * @param {*} body { vap_id: "ap_id", vdacdiem_id: "dacdiem_id", vkhu_id: "khu_id", vpho_id: "pho_id", vphuong_id: "phuong_id" } 
     */
    get_tracuu_KV: async (axios, body) => axios.post('/web-hopdong/bss_group1/lay_ds_khuvuc_px', body),

    /**
     * Lấy danh sách loại khu vực đã gán. 
     * @param {*} axios được truyền từ global (this.axios) 
     * @param {*} body {vkhuvuc_id: "khuvuc_id", vkieu: "kieu"} Trong đó: --kieu == 1 : đã gán, kieu == 2 : chưa gán
     */
    get_ds_loaiKV: async (axios, body) => axios.post('/web-hopdong/bss_group1/sp_lay_ds_loai_kv', body),

    /**
     * Lấy danh sách các đơn vị
     * @param {*} axios được truyền từ global (this.axios) 
     * @param {*} body {vap_id: "ap_id", vdacdiem_id: "dacdiem_id", vkhu_id: "khu_id", vloaidv_id" "loaidv_id", vpho_id: "pho_id", vphuong_id: "phuong_id"}
     */
    get_tracuu_DV: async (axios, body) => axios.post('/web-hopdong/bss_group1/lay_ds_donvi_px', body),

      


}