var axios
export default{
    khoitao: (a => {axios = a}),
    Lay_Ma_GD_V2: ((donvi_id, loaihd_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.post(
                    "/web-thicong/hoinghi_truyenhinh/lay_ds_quytrinh_v2",
                    {
                        "donvi_id": donvi_id,
                        "loaihd_id": loaihd_id
                    }
                  );
                resolve(rs.data.data)
            } catch (e) {
                console.log("Lay_Ma_GD_V2 loi:" + e)
                reject("")
            }
        })
    }),
    LAY_TIEN_KHOANMUC_LD: ((loaihd_id, ngay_yc, dichvuvt_id, loaitb_id, kieuld_id, phuongld_id,tocdo_id,muccuoc_id,ltb_adsl_id,ltb_adsl_moi_id,ltb_adsl_cu_id
        , tocdo_moi_id, tocdo_cu_id, kh_cd, phold_id, apld_id, khuld_id, doituong_id, trangbi_id
        ) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.post(
                    "web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld",
                    {
                        loaihd_id:loaihd_id,
                        ngay_yc: ngay_yc,
                        dichvuvt_id: dichvuvt_id,
                        loaitb_id: loaitb_id,
                        kieuld_id: kieuld_id,
                        phuongld_id: phuongld_id,
                        tocdo_id: tocdo_id,
                        muccuoc_id: muccuoc_id,
                        ltb_adsl_id: ltb_adsl_id,
                        ltb_adsl_moi_id: ltb_adsl_moi_id,
                        ltb_adsl_cu_id: ltb_adsl_cu_id,
                        tocdo_moi_id: tocdo_moi_id,
                        tocdo_cu_id: tocdo_cu_id,
                        kh_cd: kh_cd,
                        phold_id: phold_id,
                        apld_id: apld_id,
                        khuld_id: khuld_id,
                        doituong_id: doituong_id,
                        trangbi_id: trangbi_id,
                    }
                  );
                console.log(rs, "dkahkdjashkdahkj")
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_TIEN_KHOANMUC_LD loi:" + e)
                reject(false)
            }
        })
    }),
    
}
