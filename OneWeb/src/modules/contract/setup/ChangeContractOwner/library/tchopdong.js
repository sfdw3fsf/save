var axios
export default{
    khoitao: (a => {axios = a}),
    KIEMTRA_TBDD_CUNG_MATT_HDTB: (thuebao_id, ma_gd) => {
            return new Promise(async (resolve, reject) => {
                try {
                    let rs = await axios.get(
                        "web-hopdong/hopdong/kiemtra_hopdong_thuebao",{
                            params:
                            {
                                thueBaoId: thuebao_id,
                                maGd: ma_gd
                            }
                        }
                    );
                    resolve(rs.data.data)
                } catch (e) {
                    console.log("KIEMTRA_TBDD_CUNG_MATT_HDTB loi:" + e)
                    reject("")
                }
            })
        }    
    ,
    LAY_DS_HOPDONG_THEO_MA_GD: ((ma_gd, loaihd_id, donvi_id, tthd_id, nhanvien_id, donvi_dl_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.post(
                    "web-hopdong/dichvu-ca/lay_ds_hopdong_theo_ma_gd",
                    {
                      in_ma_gd: ma_gd,
                      in_loaihd_id: loaihd_id, //Đặt cọc mới
                      in_donvi_id: donvi_id, //truyền ID đơn vị của người dùng,
                      in_tthd_id: tthd_id,
                      in_nhanvien_id: nhanvien_id, //truyền ID nhân viên của người dùng,
                      in_donvi_dl_id: donvi_dl_id,
                    }
                  );
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_HOPDONG_THEO_MA_GD loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_HDTB_ADSL_THEO_HDTB_ID: ((hdtb_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId=" + hdtb_id
                  );
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_HOPDONG_THEO_MA_GD loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_DIACHI_THEO_HDKH_ID: ((hdkh_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "/web-hopdong/hopdong/lay_ds_diachi_theo_hdkh?hdKhId=" + hdkh_id
                  );
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_HOPDONG_THEO_MA_GD loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_HOPDONG_TT_THEO_HDKH_ID: ((hdkh_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh?hdKhId=" + hdkh_id
                  );
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_HOPDONG_TT_THEO_HDKH_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_HOPDONG_TB_THEO_HDKH_ID: ((hdkh_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh?hdKhId=" + hdkh_id
                  );
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_HOPDONG_TB_THEO_HDKH_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_DIACHI_THEO_HDTB_ID: ((hdtbId) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "web-hopdong/hopdong/lay_ds_diachi_theo_hdtb?hdtbId=" + hdtbId
                  );
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_DIACHI_THEO_HDTB_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_HOPDONG_TT_THEO_HDTT_ID: ((hdtt_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.post(
                    "web-hopdong/tachnhapthuebao/lay_ds_hopdong_tt_theo_hdtt_id", {
                        hdtt_id: hdtt_id
                    }
                  );
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_DIACHI_THEO_HDTB_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_DIACHI_THEO_HDTT_ID: ((hdtt_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "web-hopdong/hopdong/lay_ds_diachi_theo_hdtt?hdttId=" + hdtt_id
                  );
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_DIACHI_THEO_HDTB_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_THUEBAO_DICHVU: ((hdtb_id,
        thuebao_id,
        loaitb_id,
        kieu) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.post(
                    "web-hopdong/hopdong/lay_ds_thuebao_dichvu",
                    {
                     "hdtbId": hdtb_id,
                     "thueBaoId": thuebao_id,
                     "loaiTbId": loaitb_id,
                     "kieuId": kieu
                    }
                  )
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_DIACHI_THEO_HDTB_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_BIENDONG_THEO_HDTB_ID: ((hdtb_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "web-hopdong/hopdong/lay_biendong_thongtin_thuebao",
                    {params :{
                        hdtbId: hdtb_id
                    }}
                  )
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_BIENDONG_THEO_HDTB_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_HDTB_CD_THEO_HDTB_ID: ((hdtbId) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "web-hopdong/hopdong/lay_ds_hopdong_thuebao_codinh",
                    {params :{
                        hdtbId: hdtbId
                    }}
                  )
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_HDTB_CD_THEO_HDTB_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_HDTB_DD_THEO_HDTB_ID: ((hdtbId) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "web-ccdv/hoanthienhschuyenquyen/lay_ds_hdtb_dd_theo_hdtb_id",
                    {params :{
                        hdtb_id: hdtbId
                    }}
                  )
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_HDTB_CD_THEO_HDTB_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_TIENHD_KM_THEO_HDTB : ((hdtb_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.post(
                    "web-hopdong/khoiphucthanhly/lay_tienhd_km_theo_hdtb",
                    {
                        hdtb_id: hdtb_id
                    }
                  )
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_TIENHD_KM_THEO_HDTB loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_HOPDONG_TB_THEO_HDTB_ID : ((hdtb_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.post(
                    "/web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id",
                    {
                        in_hdtb_id: hdtb_id
                    }
                  )
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_HOPDONG_TB_THEO_HDTB_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_HDTB_TSL_THEO_HDTB_ID : ((hdtb_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "/web-hopdong/hopdong/lay_ds_hopdong_thuebao_tls",
                    { params : {
                        hdtbId : hdtb_id
                        }
                    }
                  )
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_HDTB_TSL_THEO_HDTB_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_HDTB_CNTT_THEO_HDTB_ID : ((hdtb_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.get(
                    "/web-hopdong/hopdong/lay_ds_hopdong_thuebao_cntt",
                        {
                            params: {
                                hdtbId : hdtb_id
                            }
                        }
                  )
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_HDTB_TSL_THEO_HDTB_ID loi:" + e)
                reject("")
            }
        })
    })
}
