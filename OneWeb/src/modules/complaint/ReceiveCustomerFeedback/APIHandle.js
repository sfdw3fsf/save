import API from "./API";

export default {
  async sp_ds_khieunai_gc_theo_khieunai_id(axios, khieuNaiId) {
    try {
      let rs = await API.sp_ds_khieunai_gc_theo_khieunai_id(axios, khieuNaiId);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async getKyHoaDon(axios) {
    try {
      let rs = await API.get_ky_hoa_don(axios);
      let data = this.getDataFromAPI(rs) || [];
      if (data != null) {
        return data[0].KYCUOC;
      }
    } catch (e) {
      console.log('Khong load duoc ky hoa don');
    }

    return null;
  },

  async lay_chukyno_theo_kyhoadon(axios, namthang) {
    try {
      let rs = await API.lay_chukyno_theo_kyhoadon(axios, namthang);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log('Khong load duoc ky hoa don');
    }

    return '';
  },

  async CSS_DICHVU_VT(axios) {
    try {
      let rs = await API.CSS_DICHVU_VT(axios);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async CSS_DOITUONG(axios) {
    try {
      let rs = await API.CSS_DOITUONG(axios);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async sp_ds_loai_chude_khieunai(axios) {
    try {
      let rs = await API.sp_ds_loai_chude_khieunai(axios);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async sp_lay_nhanvien_theo_nhanvien_id(axios, nhanVienID) {
    try {
      let rs = await API.sp_lay_nhanvien_theo_nhanvien_id(axios, nhanVienID);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async QLTN_HINHTHUC_KN(axios) {
    try {
      let rs = await API.QLTN_HINHTHUC_KN(axios);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async CSS_LOAI_KH(axios) {
    try {
      let rs = await API.CSS_LOAI_KH(axios);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async QLTN_KETQUA_KN(axios) {
    try {
      let rs = await API.QLTN_KETQUA_KN(axios);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async LAY_DS_THAMSO_MD(axios) {
    try {
      let rs = await API.sp_lay_ds_thamso_md(axios);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  getDataFromAPI(rs) {
    if (rs.data != null && rs.data.data != null && (typeof rs.data.data == 'number' || rs.data.data.length > 0)) {
      return rs.data.data;
    }

    return null;
  },

  async Lay_ds_tb_phkh(axios, vdonvi_id, vnhanvien_id, vttkn_id, vhuonggiao_id, vkyhoadon) {
    try {
      let rs = await API.lay_ds_tb_phkh(axios, {
        vdonvi_id, vnhanvien_id, vttkn_id, vhuonggiao_id, vkyhoadon
      });
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async Lay_thongtin_tt_theo_matb_ht(axios, vma_tb, vdichvuvt_id) {
    try {
      let rs = await API.lay_thongtin_tt_theo_matb_ht(axios, {
        vma_tb, vdichvuvt_id
      });
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async sp_dsthang_tiepnhan_phkh(axios, vkyhd, vma_tt, vma_tb, vkyhd_next, vky_kn) {
    try {
      let rs = await API.sp_dsthang_tiepnhan_phkh(axios, {
        vkyhd, vma_tt, vma_tb, vkyhd_next, vky_kn
      });
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async sp_dskyap_tiepnhan_phkh(axios, vky_kn, vkyhd_next) {
    try {
      let rs = await API.sp_dskyap_tiepnhan_phkh(axios, {
        vky_kn, vkyhd_next
      });
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async LAY_DS_DBKH_THEO_KHID(axios, vkhachhang_id) {
    try {
      let rs = await API.lay_ds_dbkh_theo_khid(axios, {
        ID: vkhachhang_id
      });
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async LAY_DS_KHIEUNAI_HT(axios, vthuebao_id, vkhieunai_id) {
    try {
      let rs = await API.sp_lay_ds_khieunai_ht(axios, {
        vthuebao_id, vkhieunai_id
      });
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async LAY_THONGTIN_TT_TB_THEO_MATT(axios, vkyhoadon, vma_tt) {
    try {
      let rs = await API.lay_thongtin_tt_tb_theo_matt(axios, {
        vkyhoadon, vma_tt
      });
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async CSS_LOAIHINH_TB(axios) {
    try {
      let rs = await API.CSS_LOAIHINH_TB(axios);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async Lay_ds_chude_kn(axios) {
    try {
      let rs = await API.sp_lay_ds_chude_kn(axios);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async mapData(axios, id_neo, table, dk) {
    try {
      let rs = await API.map_id(axios, {id_neo, table, dk});
      return this.getDataFromAPI(rs) || '';
    } catch (e) {
      return '';
    }
  },

  async sp_ds_baohong_thuebao_id_ttbh_id(axios, thuebao_id) {
    try {
      let rs = await API.sp_ds_baohong_thuebao_id_ttbh_id(axios, thuebao_id);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async LAY_THONGTIN_TT_TB_THEO_TB(axios, vkyhoadon, vma_tb) {
    try {
      let rs = await API.lay_thongtin_tt_tb_theo_tb(axios, {vkyhoadon, vma_tb});
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async KIEMTRA_TT_MaTB(axios, thuebao_id) {
    try {
      let rs = await API.sp_kiemtra_tt_matb(axios, thuebao_id);
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async LayHuongGiao_TiepNhan_XLKN_DD(axios, vchudekn_id, vloaitb_id) {
    try {
      let rs = await API.sp_layhuonggiao_tiepnhan_xlkn_dd(axios, {vchudekn_id, vloaitb_id});
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async lay_ds_khieunai_cungqt_chuaht(axios, vthuebao_id, vquytrinh_id) {
    try {
      let rs = await API.lay_ds_khieunai_cungqt_chuaht(axios, {vthuebao_id, vquytrinh_id});
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async POST_GET_KEYS(axios, keyname) {
    let key = ''
    await API.POST_GET_KEYS(axios, {
      keyname: keyname
    }).then(response => {
      if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
        key = response.data.data
      }
    })

    return key
  },

  async sp_lay_huonggiao_ldv_quytrinh_id(axios, p_quytrinh_id) {
    try {
      let rs = await API.sp_lay_huonggiao_ldv_quytrinh_id(axios, {p_quytrinh_id});
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async LAY_DONVI_NGUOIDUNG_THEOBANG(axios, vtenbang, vgiatri, vloaidv_id, vloainv_id) {
    try {
      let rs = await API.lay_donvi_nguoidung_theobang(axios, {vtenbang, vgiatri, vloaidv_id, vloainv_id});
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  async FN_HDTBDV_TIEPNHAN_PHKH(axios, vthuebao_id, vloaidv_id, vdaily_id) {
    try {
      let rs = await API.fn_hdtbdv_tiepnhan_phkh(axios, {vthuebao_id, vloaidv_id, vdaily_id});
      return this.getDataFromAPI(rs) || '';
    } catch (e) {
      console.log(e);
    }

    return '';
  },

  async fn_capnhat_tiepnhan_phkh(axios, dskhieunai, dskhieunai_dv, vhuonggiao_tn, vnhapmoi) {
    try {
      let rs = await API.fn_capnhat_tiepnhan_phkh(axios, {
        dskhieunai: JSON.stringify(dskhieunai),
        dskhieunai_dv: JSON.stringify(dskhieunai_dv),
        vhuonggiao_tn,
        vnhapmoi: vnhapmoi ? 1 : 0
      });
      return this.getDataFromAPI(rs) || '-1';
    } catch (e) {
      console.log(e);
    }

    return '-1';
  },

  async fn_capnhat_rdokhongxlduoc_tiepnhan_phkh(axios, KHIEUNAI_ID, PHIEUKN_ID, NHAPMOI, RDO_KHONGXLDUOC, LYDO, GIAMCUOC_DT, VAT_DT, GIAMCUOC_CP, VAT_CP) {
    try {
      let rs = await API.fn_capnhat_rdokhongxlduoc_tiepnhan_phkh(axios, {
        vds: JSON.stringify({
          KHIEUNAI_ID,
          PHIEUKN_ID,
          NHAPMOI: NHAPMOI ? 1 : 0,
          RDO_KHONGXLDUOC,
          LYDO,
          GIAMCUOC_DT,
          VAT_DT,
          GIAMCUOC_CP,
          VAT_CP
        })
      });
      return this.getDataFromAPI(rs) || '-1';
    } catch (e) {
      console.log(e);
    }

    return '-1';
  },

  async sp_update_khieunai_email(axios, vkhieunai_id, vemail) {
    try {
      let rs = await API.sp_update_khieunai_email(axios, {vkhieunai_id, vemail});
      return this.getDataFromAPI(rs) || rs.data.message_detail;
    } catch (e) {
      console.log(e);
    }

    return 'Không cập nhật được email';
  },

  async PHAILAM(axios, tthd_id, quytrinh_id, code) {
    if (code === null || code === '') {
      return false;
    }

    code = code.toUpperCase();
    try {
      let rs = await API.ds_huonggiao(axios, tthd_id, quytrinh_id);
      let data = this.getDataFromAPI(rs) || [];
      if (data.length > 0) {
        let luong_id = data[0].luong_id;

        rs = await API.phailam(axios, {
          luong_id,
          code
        });
        data = this.getDataFromAPI(rs) || '0';
        return data === '1';
      }
    } catch (e) {
      console.log(e);
    }

    return false;
  },

  async Lay_ds_phanhoi_chuagiao(axios, vkhieunai_id, vhuonggiaotn_id) {
    try {
      let rs = await API.lay_ds_phanhoi_chuagiao(axios, {
        vkhieunai_id, vhuonggiaotn_id
      });
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  GiaoPhieu_KhieuNai(axios, dsGiaoPhieu) {
    API.fn_giaophieu_khieunai_tiepnhan_phkh(axios, {
      dsgiaophieu: JSON.stringify(dsGiaoPhieu)
    });
  },

  Tudong_giao_nv_phkh(axios, vkhieunai_id, vphieu_id, vnhanvien_id, vnoidung) {
    API.tudong_giao_nv_phkh(axios, {
      vkhieunai_id, vphieu_id, vnhanvien_id, vnoidung
    });
  },

  xoa_khieunai(axios, vkhieunai_id) {
    API.xoa_khieunai(axios, {vkhieunai_id});
  },

  async fn_khoaphieu_tiepnhan_phkh(axios, KHIEUNAI_ID, PHIEUKN_ID, QUYTRINH_ID, DONVI_ID, NHANVIEN_ID, NGUOI_CN, MAY_CN, NGAYXL,
                                   NHANVIEN_ID_CBO, NOIDUNGXL, LOI_ID, NGUYENNHAN,
                                   GIAMCUOC_DT, VAT_DT, GIAMCUOC_CP, VAT_CP) {
    try {
      let rs = await API.fn_khoaphieu_tiepnhan_phkh(axios,
        {
          vds: JSON.stringify({
            KHIEUNAI_ID, PHIEUKN_ID, QUYTRINH_ID, DONVI_ID, NHANVIEN_ID, NGUOI_CN, MAY_CN, NGAYXL,
            NHANVIEN_ID_CBO, NOIDUNGXL, LOI_ID, NGUYENNHAN,
            GIAMCUOC_DT, VAT_DT, GIAMCUOC_CP, VAT_CP
          })
        });
      return rs.data.error === '200' ? '1' : rs.data.message_detail;
    } catch (e) {
      console.log(e);
      return 'Không thể khóa phiếu ' + e;
    }
  },

  async Lay_chukyno_theotb(axios, vkyhoadon, vma_tb, vma_tt, vdichvuvt_id) {
    try {
      let rs = await API.sp_lay_chukyno_theotb(axios, {
        vkyhoadon, vma_tb, vma_tt, vdichvuvt_id
      });
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  },

  sp_taodulieu_tiengc(axios, vkhieunai_id, vds, vkieu) {
    API.sp_taodulieu_tiengc(axios, {
      vkhieunai_id, vds, vkieu
    })
  },

  async Lay_HuongGiao_QuyTrinh(axios, quytrinh_id, thutu_id) {
    try {
      let rs = await API.fn_lay_huonggiao_quytrinh(axios, {
        quytrinh_id, thutu_id
      });
      return this.getDataFromAPI(rs) || null;
    } catch (e) {
      console.log(e);
    }

    return null;
  },

  async Lay_DK_Cho_Bang(axios, vnguoidung_id, vphanvung_id, vtenbang) {
    try {
      let rs = await API.lay_dk_cho_bang(axios, {
        vnguoidung_id, vphanvung_id, vtenbang
      });
      return this.getDataFromAPI(rs) || '';
    } catch (e) {
      console.log(e);
    }

    return '';
  },

  async LAY_DS_HUONGGIAO(axios, nhom_qt_id, quytrinh_id, loaihd_id, dichvuvt_id, quyen_huonggiao) {
    try {
      let rs = await API.lay_ds_huonggiao(axios, {
        dichvuvt_id,
        loaihd_id,
        nhom_qt_id,
        quytrinh_id,
        quyen_huonggiao
      });
      return this.getDataFromAPI(rs) || [];
    } catch (e) {
      console.log(e);
    }

    return [];
  }
}
