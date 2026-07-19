import API from './InstallNewSubsCrossSaleAPI'
import axios from "@/plugins/axiosPlugin";
import {
    DichVuVienThong,
  } from "../InstallNewSubs/Enums";
export default {
    async tinh_thicong(tinh_id)
    {
        var tinh = {};
        var rs = await API.post_lay_tinh_thi_cong(axios, { "tinhtc_id": tinh_id }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return [];
        });//objDB.Tinh_ThiCong(tinh_id).Tables[0];
        if (rs.length > 0)
        {
            var dr = rs[0];
            tinh.user = dr["ten_truycap"];
            tinh.pass = dr["mat_khau"];
            tinh.nhanvien_id = dr["nhanvien_id"]*1;
            tinh.donvi_id = dr["donvi_id"]*1;
            tinh.matinh = dr["matinh"];
            tinh.TNS = dr["tns_string"];
            tinh.tinhthicong_id = dr["tinh_id"]*1;
            tinh.mavung = dr["ma_vung"];
            // if (AppDomain.CurrentDomain.FriendlyName == "Shard.exe")
            //     tinh.ip_dll = string.Format("tcp://localhost:{0}/", 7900 + (tinh_id > 70 ? tinh_id - 30 : tinh_id));
            // else
            tinh.ip_dll = dr["ip_dll"];
        }
        else
        {
            tinh.user = "";
            tinh.pass = "";
            tinh.nhanvien_id = 0;
            tinh.donvi_id = 0;
            tinh.matinh = "";
            tinh.TNS = "";
            tinh.tinhthicong_id = 0;
            tinh.ip_dll = "";
        }
        return tinh;
    },
    async LAY_KHACHHANGID_THICONG(tinh, khachhangbanid) {
        if (khachhangbanid == 0)
        {
            return 0;
        }
        var rs2 = await API.post_lay_khachhangid_thicong(axios, {
            "khachhangbanid": khachhangbanid,
            "tinhthicong_id": tinh.tinhthicong_id
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return [];
        });
        if(!rs2 || rs2.length==0 || rs2=="") return 0;
        return rs2;
    },
    async GetKey(tinh,keyname) {
        return await API.post_get_key(axios, {
            "keyname": keyname,
            "tinhthicong_id": tinh.tinhthicong_id
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else throw response.data.message//return null;
        }).catch(()=> { throw `Lỗi sinh key ${keyname}`; } );
    },
    async kiemtra_thuebao_sd_madoicap(tinh,madoicap) { 

        return await API.post_kiemtra_thuebao_sd_madoicap(axios, {
            "tinhthicong_id": tinh.tinhthicong_id,
            "vmadoicap": madoicap
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return null;
        }).catch(()=> { return null; } );
        
    },
    async map_id(tinh,in_id_neo,in_table,in_dk) { 

        return await API.post_bc_map_id(axios, {
            "in_dk": in_dk,
            "in_id_neo": in_id_neo,
            "in_table": in_table,
            "tinhthicong_id": tinh.tinhthicong_id
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return null;
        }).catch(()=> { return null; } );
    },
    async sinh_pass_random(kieu) {
        return await API.get_sinh_pass_random(axios, {}, kieu).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return null;
        }).catch(()=> { return null; } );
    },
    async LAY_DS_DB_MEGAWAN_THEO_TBID(tinh, tb_tn_id) { 
        return await API.post_lay_ds_db_megawan_theo_tbid(axios,{
            "thuebao_id": tb_tn_id,
            "tinhthicong_id": tinh.tinhthicong_id
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return null;
        }).catch(()=> { return null; } );
    },
    async SINH_MATB(tinhthicong_id,vquan_id,vdichvuvt_id,vloaitb_id,vtentat) { 
        return await API.post_sinh_matb_bc(axios,{
            "tinhthicong_id": tinhthicong_id,
            "vquan_id": vquan_id,
            "vdichvuvt_id": vdichvuvt_id,
            "vloaitb_id": vloaitb_id,
            "vtentat": vtentat
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return null;
        }).catch(()=> { return null; } );
    },
    async SINH_MALT(tinhthicong_id,vquan_id,vdichvuvt_id,vloaitb_id,vtentat) { 
        return await API.post_sinh_malt_bc(axios,{
            "tinhthicong_id": tinhthicong_id,
            "vquan_id": vquan_id,
            "vdichvuvt_id": vdichvuvt_id,
            "vloaitb_id": vloaitb_id,
            "vtentat": vtentat
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return null;
        }).catch(()=> { return null; } );
    },
    async sinh_vfi_vrf_v2(tinhthicong_id,vdichvuvt_id,vloaitb_id) { 
        return await API.post_sinh_vfi_vrf_v2(axios,{
            "tinhthicong_id": tinhthicong_id,
            "vdichvuvt_id": vdichvuvt_id,
            "vloaihinh_tb": vloaitb_id?vloaitb_id:""
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return null;
        }).catch(()=> { return null; } );
    },
    async LAY_MA_KH_WEB(makh) {
        return await API.post_sp_lay_ma_kh_web(axios, {
            "vma_kh": makh
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return null;
        }).catch(()=> { return null; } );
    },
    async TRACUUNO_KHACHHANG(khachhang_id) {
        return await API.post_tracuuno_khachhang(axios,{
            "vkhachhang_id": khachhang_id
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return null;
        }).catch(()=> { return null; } );
    },
    async KIEMTRA_MATB(dichvuvt_id,ma_tb) { 
        // return await API.get_kiemtra_matb_khoso(axios,{params:{"dichvuvt_id":dichvuvt_id,"ma_tb":ma_tb}}).then((response) => {
        //     if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
        // return await API.get_kiemtra_matb_khoso(axios,{params:{"dichvuvt_id":dichvuvt_id,"ma_tb":ma_tb}}).then((response) => {            
        //     if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === '0') {
        //         return response.data.data;
        //     }
        //     else return 0;
        // }).catch(()=> { return 0; } );

        await API.post_kiemtra_matb(this.axios,{ "dichVuVtId": dichvuvt_id, "hdtbId": 0, "maTb": ma_tb }).then((response) => {
            if(response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            //   if(response.data.data!=0) {
            //     throw "Account " + this.thuebao.adsl.txtAccountADSL + " đã được cấp. Bạn hãy kiểm tra lại";
            //   } else this.thuebao.adsl.validadslacc = true; //FIXME doan nay them vao vi khi test camera no chay qua va khong thay doi gia tri nay dan den chet chuong trinh
                return response.data.data;
            } else return 0;
          }).catch(()=> { return 0; } );

    },
    async CAPNHAT_KHOSO(ma_tb_moi,ma_tb_cu,dichvuvt_id,donvi_id) {
        /*
            input.get("ma_tb_moi"),
            input.get("ma_tb_cu"),
            input.get("dichvuvt_id"),
            input.get("donvi_id"),
        */
        return await API.post_capnhat_khoso_v2(axios,{
            "ma_tb_moi": ma_tb_moi,
            "ma_tb_cu": ma_tb_cu,
            "dichvuvt_id": dichvuvt_id,
            "donvi_id": donvi_id
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return 0;
        }).catch(()=> { return 0; } );
    },
    async KIEMTRA_MATB_KHOSO_AO(ma_tb_ao_ht) {
        return await API.post_kiemtra_matb_khoso_ao(axios,{ "vma_tb": ma_tb_ao_ht }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data==1;
            }
            else return false;
        }).catch(()=> { return false; } );
    },
    async LAY_DS_THUEBAO_THEO_MALT(vma_lt,vhdtb_id) {
        return await API.post_lay_ds_thuebao_theo_malt(axios,{ "vma_lt": vma_lt, "vhdtb_id": vhdtb_id }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return [];
        }).catch(()=> { return []; } );
    },
    async LayHuongGiao_TiepNhan(tinhthicong_id,loaihd_id,loaitb_id,tocdo_id,muccuoc_id,kieuld_id,dichvuvt_id,loaikenh_id) {
        //return await API.post_layhuonggiao_tiepnhan(axios,{
        return await API.post_layhuonggiao_tiepnhan_v4(axios,{
            "tinhthicong_id": tinhthicong_id,
            "loaihd_id": loaihd_id,
            "loaitb_id": loaitb_id,
            "tocdo_id": tocdo_id,
            "muccuoc_id": muccuoc_id?muccuoc_id:0,
            "kieuld_id": kieuld_id,
            "loaikenh_id": loaikenh_id, 
            "dichvuvt_id": dichvuvt_id,
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return [];
        }).catch(()=> { return []; } );
    },
    async KIEMTRA_MATB_KHOSO(tinhthicong_id,vma_tb,vdichvuvt_id) { 
        return await API.post_kiemtra_matb_khoso(axios,{ 
            "tinhthicong_id": tinhthicong_id,
            "vma_tb": vma_tb,
            "vdichvuvt_id": vdichvuvt_id
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data==1;
            }
            else return false;
        }).catch(()=> { return false; } );
    },
    KiemTraTBTrongHD(ma_tb, lvwThueBao, tt)
    {
        var key = tt == 3 ? "ma_tb":"";
        var result = false;
        for (var i = 0; i < lvwThueBao.length; i++)
        {
            if (!lvwThueBao[i][tt] || lvwThueBao[i][tt].trim().toLowerCase() == ma_tb.trim().toLowerCase())
            {
                result = true;
                break;
            }
            else
                result = false;
        }
        return result;
    },
    async Gan_thongso_bancheo_hdtb(vhdtb, data)
    {
        var thongso_ban = data.thongso_ban;
        var thongso_tc = data.thongso_tc;
        
        var drTHONGSO = {};

        var ds = await API.post_lay_tt_gan_thongso_bancheo_hdtb(axios,{ "ds_para": "{\"HDTB_ID\":"+vhdtb+"}" }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                // return response.data.data.RESULT.DS_TS_TB;
                var apidata = response.data.data;//JSON.parse(response.data.data);
                if(apidata['ERROR_CODE']!=1) {
                    this.$toast.error(apidata['MESSAGE']);
                    return [];
                }
                if(apidata.RESULT && apidata.RESULT.DS_TS_TB)
                    return apidata.RESULT.DS_TS_TB
                else return [];
            }
            else return [];
        }).catch(()=> { return []; } );//bcbasefacade.LAY_THONGSO_THUEBAO_BANCHEO_HDTBID(Convert.ToInt64(vhdtb));
        //cmUlt.CopyRow(ds.Tables[0].Rows[0], drTHONGSO);
        function UpperCasePropertyList(obj) {
            return obj.map( function( item ){
                for(var key in item){
                    var upper = key.toUpperCase();
                    // check if it already wasn't uppercase
                    if( upper !== key ){
                        item[ upper ] = item[key];
                        delete item[key];
                    }
                }
                return item;
            });
        }
        if (ds && ds.length > 0)
        {
            UpperCasePropertyList(ds);
            drTHONGSO = ds[0];
            var dvvt_id = ds[0]["DICHVUVT_ID"];
            if (dvvt_id != DichVuVienThong.HOINGHI_TRUYENHINH &&
                dvvt_id != DichVuVienThong.ANTOAN_BAOMAT_TT &&
                dvvt_id != DichVuVienThong.DICHVU_CNTT &&
                dvvt_id != DichVuVienThong.TRUNGTAM_DULIEU)
            {
                thongso_ban.chuquanld_id = drTHONGSO.CHUQUANLD_BAN;
                thongso_ban.congnghe_id = drTHONGSO.CONGNGHE_BAN;
                thongso_ban.loaikenh_id = drTHONGSO.LOAIKENH_BAN;
                thongso_ban.loaimd_id = drTHONGSO.LOAIMD_BAN;
                thongso_ban.ma_doicap = drTHONGSO.MADOICAP_BAN;
                thongso_ban.chuquan_id = drTHONGSO.CHUQUAN_BAN;
                thongso_ban.ma_tn = drTHONGSO.MATN_BAN;
                thongso_ban.thietbidc_id = drTHONGSO.THIETBIDAU_BAN;
                thongso_ban.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_BAN;
                thongso_ban.thoihan_id = drTHONGSO.THOIHAN_BAN;
                thongso_ban.tocdo_id = drTHONGSO.TOCDO_BAN;
                thongso_ban.tocdo_isp = drTHONGSO.TOCDOISP_BAN;
                thongso_ban.tocdo_nix = drTHONGSO.TOCDONIX_BAN;
                try
                {
                    thongso_ban.tocdo_pir_id = drTHONGSO.TOCDOPIR_BAN;
                    thongso_ban.phanloai_id = drTHONGSO.PHANLOAI_ID;
                    thongso_ban.phantach_id = drTHONGSO.PHANTACH_TD;
                }
                catch (e) { }
                thongso_ban.tocdothuc_id = drTHONGSO.TOCDOTHUC_BAN;
                thongso_ban.trangbi_id = drTHONGSO.TRANGBI_BAN;


                thongso_tc.chuquan_id = drTHONGSO.CHUQUAN_TC;
                thongso_tc.chuquanld_id = drTHONGSO.CHUQUANLD_TC;
                thongso_tc.congnghe_id = drTHONGSO.CONGNGHE_TC;
                thongso_tc.loaikenh_id = drTHONGSO.LOAIKENH_TC;
                thongso_tc.loaimd_id = drTHONGSO.LOAIMD_TC;
                thongso_tc.ma_doicap = drTHONGSO.MADOICAP_TC;
                thongso_tc.ma_tn = drTHONGSO.MATN_TC;

                thongso_tc.thietbidc_id = drTHONGSO.THIETBIDAU_TC;
                thongso_tc.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC;
                try
                {
                    thongso_tc.thoihan_id = drTHONGSO.THOIHAN_TC;
                }
                catch (e) { }
                thongso_tc.tocdo_id = drTHONGSO.TOCDO_TC;
                thongso_tc.tocdo_isp = drTHONGSO.TOCDOISP_TC;
                thongso_tc.tocdo_nix = drTHONGSO.TOCDONIX_TC;
                try
                {
                    thongso_tc.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC;
                }
                catch (e) { }
                thongso_tc.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC;
                thongso_tc.trangbi_id = drTHONGSO.TRANGBI_TC;
            }
            thongso_ban.muccuoc_id = drTHONGSO.MUCCUOC_BAN;
            thongso_ban.muccuoctb_id = drTHONGSO.MUCCUOCTB_BAN;
            thongso_ban.kieuld_id = drTHONGSO.KIEULD_BAN;
            thongso_ban.doituong_id = drTHONGSO.DOITUONG_BAN;
            thongso_tc.kieuld_id = drTHONGSO.KIEULD_TC;
            thongso_tc.doituong_id = drTHONGSO.DOITUONG_TC;
            thongso_tc.muccuoc_id = drTHONGSO.MUCCUOC_TC;
            thongso_tc.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC;

            thongso_tc.kieutra_id = drTHONGSO.KIEUTRA_TC;
            thongso_ban.kieutra_id = drTHONGSO.KIEUTRA_BAN;
            if (dvvt_id == DichVuVienThong.MEGAWAN || dvvt_id == DichVuVienThong.METRONET || dvvt_id == DichVuVienThong.TSL)
            {
                //gan tskt rieng cho kenh qt
                if (drTHONGSO.MHKN_ID != null)
                    thongso_ban.mhkn_id = drTHONGSO.MHKN_ID;
                if (drTHONGSO.GDKN_ID != null)
                    thongso_ban.gdkn_id = drTHONGSO.GDKN_ID;
                if (drTHONGSO.YC_BAOVE != null)
                    thongso_ban.yc_baove = drTHONGSO.YC_BAOVE;
                if (drTHONGSO.TOCDO_POP != null)
                    thongso_ban.tocdo_pop = drTHONGSO.TOCDO_POP;
                if (drTHONGSO.GDKN_POP_ID != null)
                    thongso_ban.gdkn_pop_id = drTHONGSO.GDKN_POP_ID;
                if (drTHONGSO.CAUHINH_DT_ID != null)
                    thongso_ban.cauhinh_dt_id = drTHONGSO.CAUHINH_DT_ID;
                if (drTHONGSO.KIEU_DUPLEX_ID != null)
                    thongso_ban.kieu_duplex_id = drTHONGSO.KIEU_DUPLEX_ID;
                if (drTHONGSO.KIEUKN_ID != null)
                    thongso_ban.kieukn_id = drTHONGSO.KIEUKN_ID;

                thongso_ban.mtu_size = drTHONGSO.MTU_SIZE;
                thongso_ban.auto_nego = drTHONGSO.AUTO_NEGO;
                thongso_ban.sncp = drTHONGSO.SNCP;
                thongso_ban.cap_chinh = drTHONGSO.CAP_CHINH;
                thongso_ban.cap_duphong = drTHONGSO.CAP_DUPHONG;
                thongso_ban.diem_pop = drTHONGSO.DIEM_POP;
                thongso_ban.wan_ip = drTHONGSO.WAN_IP;
                thongso_ban.lan_ip = drTHONGSO.LAN_IP;
                thongso_ban.ce_ip = drTHONGSO.CE_IP;
                thongso_ban.tt_ip = drTHONGSO.TT_IP;
                thongso_ban.sl_ip = drTHONGSO.SL_IP;
                thongso_ban.ten_trunk_nni = drTHONGSO.TEN_TRUNK_NNI;
                thongso_ban.so_as = drTHONGSO.SO_AS;
                thongso_ban.hqos = drTHONGSO.HQOS;
                thongso_ban.cos = drTHONGSO.COS;
                thongso_ban.svlan = drTHONGSO.SVLAN;
                thongso_ban.cvlan = drTHONGSO.CVLAN;
                thongso_ban.vlan_transparent = drTHONGSO.VLAN_TRANSPARENT;
                thongso_ban.tt_port = drTHONGSO.TT_PORT;


                if (drTHONGSO.MHKN_ID != null)
                    thongso_tc.mhkn_id = drTHONGSO.MHKN_ID;
                if (drTHONGSO.GDKN_ID != null)
                    thongso_tc.gdkn_id = drTHONGSO.GDKN_ID;
                if (drTHONGSO.YC_BAOVE != null)
                    thongso_tc.yc_baove = drTHONGSO.YC_BAOVE;
                if (drTHONGSO.TOCDO_POP != null)
                    thongso_tc.tocdo_pop = drTHONGSO.TOCDO_POP;
                if (drTHONGSO.GDKN_POP_ID != null)
                    thongso_tc.gdkn_pop_id = drTHONGSO.GDKN_POP_ID;
                if (drTHONGSO.CAUHINH_DT_ID != null)
                    thongso_tc.cauhinh_dt_id = drTHONGSO.CAUHINH_DT_ID;
                if (drTHONGSO.KIEU_DUPLEX_ID != null)
                    thongso_tc.kieu_duplex_id = drTHONGSO.KIEU_DUPLEX_ID;
                if (drTHONGSO.KIEUKN_ID != null)
                    thongso_tc.kieukn_id = drTHONGSO.KIEUKN_ID;

                thongso_tc.mtu_size = drTHONGSO.MTU_SIZE;
                thongso_tc.auto_nego = drTHONGSO.AUTO_NEGO;
                thongso_tc.sncp = drTHONGSO.SNCP;
                thongso_tc.cap_chinh = drTHONGSO.CAP_CHINH;
                thongso_tc.cap_duphong = drTHONGSO.CAP_DUPHONG;
                thongso_tc.diem_pop = drTHONGSO.DIEM_POP;
                thongso_tc.wan_ip = drTHONGSO.WAN_IP;
                thongso_tc.lan_ip = drTHONGSO.LAN_IP;
                thongso_tc.ce_ip = drTHONGSO.CE_IP;
                thongso_tc.tt_ip = drTHONGSO.TT_IP;
                thongso_tc.sl_ip = drTHONGSO.SL_IP;
                thongso_tc.ten_trunk_nni = drTHONGSO.TEN_TRUNK_NNI;
                thongso_tc.so_as = drTHONGSO.SO_AS;
                thongso_tc.hqos = drTHONGSO.HQOS;
                thongso_tc.cos = drTHONGSO.COS;
                thongso_tc.svlan = drTHONGSO.SVLAN;
                thongso_tc.cvlan = drTHONGSO.CVLAN;
                thongso_tc.vlan_transparent = drTHONGSO.VLAN_TRANSPARENT;
                thongso_tc.tt_port = drTHONGSO.TT_PORT;
                thongso_ban.loai_ip = await API.get_lay_thongtin_ds_hdtb_loaiip(axios,{params:{hdtb_id:vhdtb}}).then((response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.DS_TS_TB) {
                        return response.data.data[0].loaiip_id;
                    }
                    else return null;
                }).catch(()=> { return null; } ); //bcbasefacade.GET_DATA_SQL("select hdtb_id,loaiip_id from {?DB2}.hdtb_loaiip  where hdtb_id= " + vhdtb);
                thongso_tc.loai_ip = thongso_ban.loai_ip;
                thongso_ban.kieu_nghiemthu = await API.get_lay_thongtin_ds_hdtb_kieu_nt(axios,{params:{hdtb_id:vhdtb}}).then((response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.DS_TS_TB) {
                        return response.data.data[0].kieunt_id;
                    }
                    else return null;
                }).catch(()=> { return null; } );//bcbasefacade.GET_DATA_SQL("select hdtb_id,kieunt_id from {?DB2}.hdtb_kieu_nt  where hdtb_id= " + vhdtb);
                thongso_tc.kieu_nghiemthu = thongso_ban.kieu_nghiemthu;
            }
        }
        return {thongso_ban:thongso_ban, thongso_tc:thongso_tc}
    },

    async Gan_thongso_bancheo_hdtb_tsl(vhdtb, data)
    {
        var thongso_ban = data.thongso_ban;
        var thongso_tc_dau = data.thongso_tc_dau;
        var thongso_tc_cuoi = data.thongso_tc_cuoi;
        
        var drTHONGSO = {};

        var ds = await API.post_lay_tt_gan_thongso_bancheo_hdtb(axios,{ "ds_para": "{\"HDTB_ID\":"+vhdtb+"}" }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                // return response.data.data.DS_TS_TB;
                var apidata = response.data.data;//JSON.parse(response.data.data);
                if(apidata['ERROR_CODE']!=1) {
                    this.$toast.error(apidata['MESSAGE']);
                    return [];
                }
                if(apidata.RESULT && apidata.RESULT.DS_TS_TB)
                    return apidata.RESULT.DS_TS_TB
                else return [];
            }
            else return [];
        }).catch(()=> { return []; } );//bcbasefacade.LAY_THONGSO_THUEBAO_BANCHEO_HDTBID(Convert.ToInt64(vhdtb));
        //cmUlt.CopyRow(ds.Tables[0].Rows[0], drTHONGSO);
        function UpperCasePropertyList(obj) {
            return obj.map( function( item ){
                for(var key in item){
                    var upper = key.toUpperCase();
                    // check if it already wasn't uppercase
                    if( upper !== key ){
                        item[ upper ] = item[key];
                        delete item[key];
                    }
                }
                return item;
            });
        }
        console.info('lay_tt_gan_thongso_bancheo_hdtb 1',JSON.stringify(ds),JSON.stringify(thongso_ban));
        if (ds && ds.length > 0)
        {
            UpperCasePropertyList(ds);
            for (var i = 0; i < ds.length; i++)
            {
                var drTHONGSO = ds[i];// cmUlt.CopyRow(ds.Tables[0].Rows[i], drTHONGSO);
                console.info('lay_tt_gan_thongso_bancheo_hdtb 2',JSON.stringify(drTHONGSO),JSON.stringify(thongso_ban));
                thongso_ban.kieuld_id = drTHONGSO.KIEULD_BAN;
                thongso_ban.doituong_id = drTHONGSO.DOITUONG_BAN;
                thongso_ban.chuquan_id = drTHONGSO.CHUQUAN_BAN;
                thongso_ban.chuquanld_id = drTHONGSO.CHUQUANLD_BAN;
                thongso_ban.chuquanld_id_cuoi = drTHONGSO.CHUQUANLD_BAN_CUOI;

                thongso_ban.congnghe_id = drTHONGSO.CONGNGHE_BAN;
                thongso_ban.kieutra_id = drTHONGSO.KIEUTRA_BAN;
                thongso_ban.loaikenh_id = drTHONGSO.LOAIKENH_BAN;
                thongso_ban.loaimd_id = drTHONGSO.LOAIMD_BAN;
                thongso_ban.ma_doicap = drTHONGSO.MADOICAP_BAN;

                thongso_ban.ma_tn = drTHONGSO.MATN_BAN;

                thongso_ban.muccuoc_id = drTHONGSO.MUCCUOC_BAN;
                thongso_ban.muccuoctb_id = drTHONGSO.MUCCUOCTB_BAN;
                thongso_ban.thietbidc_id = drTHONGSO.THIETBIDAU_BAN;
                thongso_ban.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_BAN;
                thongso_ban.thoihan_id = drTHONGSO.THOIHAN_BAN;
                thongso_ban.tocdo_id = drTHONGSO.TOCDO_BAN;
                thongso_ban.tocdo_isp = drTHONGSO.TOCDOISP_BAN;
                thongso_ban.tocdo_nix = drTHONGSO.TOCDONIX_BAN;
                try
                {
                    thongso_ban.tocdo_pir_id = drTHONGSO.TOCDOPIR_BAN;
                }
                catch (e) { }
                thongso_ban.tocdothuc_id = drTHONGSO.TOCDOTHUC_BAN;
                thongso_ban.trangbi_id = drTHONGSO.TRANGBI_BAN;

                // Thông số thi công
                if (ds[i]["tinh_dc_id".toUpperCase()] == "1") // Nếu row của tỉnh đầu
                {
                    thongso_tc_dau.kieuld_id = drTHONGSO.KIEULD_TC;
                    thongso_tc_dau.doituong_id = drTHONGSO.DOITUONG_TC;
                    thongso_tc_dau.chuquan_id = drTHONGSO.CHUQUAN_TC;
                    thongso_tc_dau.chuquanld_id = drTHONGSO.CHUQUANLD_TC;
                    thongso_tc_dau.congnghe_id = drTHONGSO.CONGNGHE_TC;
                    thongso_tc_dau.kieutra_id = drTHONGSO.KIEUTRA_TC;
                    thongso_tc_dau.loaikenh_id = drTHONGSO.LOAIKENH_TC;
                    thongso_tc_dau.loaimd_id = drTHONGSO.LOAIMD_TC;
                    thongso_tc_dau.ma_doicap = drTHONGSO.MADOICAP_TC;
                    thongso_tc_dau.ma_tn = drTHONGSO.MATN_TC;
                    thongso_tc_dau.muccuoc_id = drTHONGSO.MUCCUOC_TC;
                    thongso_tc_dau.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC;
                    thongso_tc_dau.thietbidc_id = drTHONGSO.THIETBIDAU_TC;
                    thongso_tc_dau.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC;
                    thongso_tc_dau.thoihan_id = drTHONGSO.THOIHAN_TC;
                    thongso_tc_dau.tocdo_id = drTHONGSO.TOCDO_TC;
                    thongso_tc_dau.tocdo_isp = drTHONGSO.TOCDOISP_TC;
                    thongso_tc_dau.tocdo_nix = drTHONGSO.TOCDONIX_TC;
                    try
                    {
                        thongso_tc_dau.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC;
                    }
                    catch (e) { }
                    thongso_tc_dau.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC;
                    thongso_tc_dau.trangbi_id = drTHONGSO.TRANGBI_TC;
                }
                else
                {
                    thongso_tc_cuoi.kieuld_id = drTHONGSO.KIEULD_TC;
                    thongso_tc_cuoi.doituong_id = drTHONGSO.DOITUONG_TC;
                    thongso_tc_cuoi.chuquan_id = drTHONGSO.CHUQUAN_TC;
                    thongso_tc_cuoi.chuquanld_id = drTHONGSO.CHUQUANLD_TC;
                    thongso_tc_cuoi.congnghe_id = drTHONGSO.CONGNGHE_TC;
                    thongso_tc_cuoi.kieutra_id = drTHONGSO.KIEUTRA_TC;
                    thongso_tc_cuoi.loaikenh_id = drTHONGSO.LOAIKENH_TC;
                    thongso_tc_cuoi.loaimd_id = drTHONGSO.LOAIMD_TC;
                    thongso_tc_cuoi.ma_doicap = drTHONGSO.MADOICAP_TC;
                    thongso_tc_cuoi.ma_tn = drTHONGSO.MATN_TC;
                    thongso_tc_cuoi.muccuoc_id = drTHONGSO.MUCCUOC_TC;
                    thongso_tc_cuoi.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC;
                    thongso_tc_cuoi.thietbidc_id = drTHONGSO.THIETBIDAU_TC;
                    thongso_tc_cuoi.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC;
                    thongso_tc_cuoi.thoihan_id = drTHONGSO.THOIHAN_TC;
                    thongso_tc_cuoi.tocdo_id = drTHONGSO.TOCDO_TC;
                    thongso_tc_cuoi.tocdo_isp = drTHONGSO.TOCDOISP_TC;
                    thongso_tc_cuoi.tocdo_nix = drTHONGSO.TOCDONIX_TC;
                    try
                    {
                        thongso_tc_cuoi.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC;
                    }
                    catch (e) { }
                    thongso_tc_cuoi.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC;
                    thongso_tc_cuoi.trangbi_id = drTHONGSO.TRANGBI_TC;
                }
            }
        }
        console.info('lay_tt_gan_thongso_bancheo_hdtb 3',JSON.stringify(thongso_ban),JSON.stringify(thongso_tc_dau),JSON.stringify(thongso_tc_cuoi));
        return {thongso_ban:thongso_ban, thongso_tc_dau:thongso_tc_dau,thongso_tc_cuoi:thongso_tc_cuoi}
    },


    //fix map_id
    lay_id_thuebao_chia_vlan: async (axios, { tinhthicong_id, vma_tb}) => axios.post('web-bancheo/danhmuc/lay_id_thuebao_chia_vlan',{ tinhthicong_id, vma_tb}),


    async LAY_DS_DONVIQL(tinh, vphuong_id, vpho_id, vap_id, vkhu_id, vkieu)
    {
        return this.LAY_DS_DONVIQL_2(tinh.tinhthicong_id,vphuong_id, vpho_id, vap_id, vkhu_id, vkieu);
    },
    async LAY_DS_DONVIQL_2(tinhtc_id,vphuong_id, vpho_id, vap_id, vkhu_id, vkieu)
    {
        var ds = await API.post_lay_ds_donviql(axios,{
            "tinhthicong_id": tinhtc_id,
            "vap_id": vap_id?vap_id:0,
            "vkieu": vkieu,
            "vkhu_id": vkhu_id?vkhu_id:0,
            "vpho_id": vpho_id?vpho_id:0,
            "vphuong_id": vphuong_id,
        }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            }
            else return [];
        }).catch(()=> { return []; } );
        return ds;
        // //kieu 1: quan ly dia chi thanh toan, 2 quan ly dia chi lap dat
        // string str = "";
        // str = DatabaseConstants.DB2 + ".LAP_HOP_DONG.LAY_DS_DONVIQL";
        // OracleParameter[] parms;
        // parms = new OracleParameter[] 
        //                 {
        //                     new OracleParameter("vphuong_id", OracleDbType.Int64),                          
        //                     new OracleParameter("vpho_id", OracleDbType.Int64),
        //                     new OracleParameter("vap_id", OracleDbType.Int64),
        //                     new OracleParameter("vkhu_id", OracleDbType.Int64),
        //                     new OracleParameter("vkieu", OracleDbType.Int32),                                
        //                     new OracleParameter("returnds",OracleDbType.RefCursor,ParameterDirection.Output),
        // };
        // parms[0].Value = vphuong_id;
        // parms[1].Value = vpho_id;
        // parms[2].Value = vap_id;
        // parms[3].Value = vkhu_id;
        // parms[4].Value = vkieu;
        // //return new OracleDataBanCheo().getDataFromProcedure(tinh, str, "DanhSach", parms);
        // return new OracleDataAccess2().getDataFromProcedure(str, "DanhSach", parms);
    }
}