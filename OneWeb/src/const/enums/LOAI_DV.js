const LOAI_DV = Object.freeze(
    {
        DONVI_QL : 1,
        DONVIQL_TT : 2,
        DONVIQL_LD : 3,
        GIAODICH : 4,
        TRAM_VT : 5,                  //Trạm thực hiện thi công
        VETINH : 5,
        HOST : 6,                     // hoangpkn : 27/04/2012
        DONVIQL_TT_BUUDIEN : 7,
        KHACHHANG : 8,
        TRAM_ATM : 9,
        TRAM_MINI : 10,
        DHTC_QLDL : 11,               //Hoàng : 05/02/2012 : Điều hành TC và QLDL
        DONVI_TSL : 12,
        HOST_VATLY : 16,              // Tổng đài xử lý
        QUAYTHU : 18,
        XULY_NO_KHODOI : 19,
        GIAIQUYET_KHIEUNAI : 20,      // hoangpkn : 20/08/2012
        TRAM_IP : 21,                 // Hoangpkn : 28/09/2012
        TO_QUANLY : 22,               //Chientq: 30/06/2011 tổ quản lý kết cuối bên cab
        HOANTHIEN_HOSO : 23,          // Hoangpkn : 26/03/2014
        TRAM_VETINH : 25,             //Vệ tinh cố định
        TRAM_IP_SWITCH : 26,          // Trạm Ip Switch : phát triển FiberVNN
        CONGTACVIEN : 30,             //Lamnt: Đơn vị quản lý cộng tác viên
        PHONGBAN_XM_KHIEUNAI : 31,    // Hoangpkn : 07/06/2013
        TO_THU_CUOC : 32,             //Tổ thu cước
        DAI_BR : 37,                  //Đài Băng Rộng
        TRAM_PORT : 39,               // Hoangpkn : 18/07/2014
        DAILY_XLKN : 45,              // Hoangpkn : 06/09/2014
        PHONG_BANHANG_KV : 46,        //Minhnt thêm loại đơn vị Phòng bán hàng khu vực
        XULY_YCKM : 49,               // TuanNA : 09/02/2015
        BANHANG_TRUCTIEP : 54,        // Hoangpkn : thêm cho HCM 03/10/2016
        GD_NHAN_BANGKE : 61,          // Hoangpkn : 13/07/2015
        KETOAN_NHAN_BANGKE : 62,      // Hoangpkn : 13/07/2015
        // DIEUHANH_TRUNGTAM_VT : 63,    // vinhpv
        CUA_HANG : 63,                // hoangpkn : 14/09/2016
        TTDH_NGHIEMTHU : 64,          // Hoangpkn : 18/03/2016
        TTKD_NGHIEMTHU : 65,          // Hoangpkn : 18/03/2016
        DONVI_LAPHOPDONG : 72,        // Hoangpkn : 10/10/2016
        DONVI_LAPHOPDONG_CHA : 73,    // Hoangpkn : 17/10/2016
        KHO_NHAN_TBI_BAOHANH : 80,    // vinhpv : 08/06/2016
        TO_BHSC : 81,                 // vinhpv : 08/06/2016
        KHO_NHAN_TBI_BAOHANH_XONG : 82,          // vinhpv : 08/06/2016
        HOANTHIEN_BAOHANH : 83,       // vinhpv : 08/06/2016
        KENHBAN_THICONG_DV : 98,       // hoangpkn : 25/02/2021
        TONGDAI_CABMAN : 100,         // Lamnt: 27/10/2015
        TIEPNHAN_BAOHONG : 66,         // hoangpkn : 10/07/2017
        TOKD_XLKN : 427,               // hoangpkn : 02/11/2017
        TRAM_CAP_PORT : 504,            // hoangpkn : 22/11/2018
        CSHT_NHATRAM : 555,               // vinhpv : 20180821
        NHOM_XLSC : 620, //Nhóm xử lý sự cố --> minhnt -> dành riêng cho Hà Lội
        BH_QLDH : 635, //Nhóm xử lý sự cố --> minhnt -> dành riêng cho HCM
        BH_DVXLDH : 636, //Nhóm xử lý sự cố --> minhnt -> dành riêng cho HCM
        TT_BANHANG_MIEN : 890, //Trung tâm bán hàng miền (VIP)
        TTKD_BAOHANH_VT : 998, //TTKD - Bảo hành vật tư
        NET_SNOC1 : 432,
        PBH_KHAIBAO_DV : 598,
    }
)
export default LOAI_DV
