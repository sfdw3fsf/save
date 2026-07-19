import api from './api'
export default{

     async ts_laphd_hoamangmoi_ds_loaianh(axios){
        var data = [];
        var req = {};
        await  api.ts_laphd_hoamangmoi_ds_loaianh(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }

          }).catch(function (res) {
            console.error(res);
            throw `${res.data.message} (lỗi gọi CCBS: ts_laphd_hoamangmoi_ds_loaianh)`;
          });
         return data;
     },

     async ts_hoanthien_hd_ds_loaianh(axios, loai_kh){
        var data = [];
        var req = {
            "loai_kh": loai_kh
        };
        await  api.ts_hoanthien_hd_ds_loaianh(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }

          }).catch(function (res) {
            console.error(res);
            throw `${res.data.message} (lỗi gọi CCBS: ts_hoanthien_hd_ds_loaianh)`;
          });
         return data;
     },

     async ts_laphd_thanhly_gui_catmo(axios, ma_tinh, ma_hd, user_id){
        var data = {
            IssSuccessed: false,
            Message: ""
        };
        var req = {
            "ma_tinh": ma_tinh,
            "ma_hd": ma_hd,
            "userid": user_id
        };
        await  api.ts_laphd_thanhly_gui_catmo(axios, req).then(res=>{
          console.log("ts_laphd_thanhly_gui_catmo", res.data.message);
          if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data.IssSuccessed = true;
            }else{
              // data.IssSuccessed = false;
              // data.Message = res.data.message;
              throw res.data.message;
            }
          }).catch(function (res) {
            console.error(res);
            throw res;
          });
         return data;
     },

     async ts_hoanthien_hd_thanhly_danhsach_hoanthanh(axios, ma_tinh, ma_hd, ngay_ht, user_id){
        var data = {
            IssSuccessed: false,
            Message: ""
        };
        var req = {
            "ma_tinh": ma_tinh,
            "ma_hd": ma_hd,
            "ngay_hoan_thanh": ngay_ht, //dd/MM/yyyy
            "userid": user_id
        };
        await  api.ts_hoanthien_hd_thanhly_danhsach_hoanthanh(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data.IssSuccessed = true;
            }else{
              data.IssSuccessed = false;
              data.Message = res.data.message;
              throw res.data.message;
            }

          }).catch(function (res) {
            console.error(res);
            //throw `${res.data.message} (lỗi gọi CCBS: ts_hoanthien_hd_thanhly_danhsach_hoanthanh)`;
            throw res;
          });
         return data;
     },

     async ts_laphd_chuyenquyen_sudung_ds_thuebao(axios, ma_tinh, ma_hd){
        var data = [];
        var req = {
            "ma_tinh": ma_tinh,
            "ma_hd": ma_hd,
        };
        await  api.ts_laphd_chuyenquyen_sudung_ds_thuebao(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }

          }).catch(function (res) {
            console.error(res);
            throw `${res.data.message} (lỗi gọi CCBS: ts_laphd_chuyenquyen_sudung_ds_thuebao)`;
          });
         return data;
     },

     async ts_laphd_chuyenquyen_sudung_hoanthienhd(axios, ngay_hoan_thien, ly_do_anh, ma_tinh, ghi_chu, ma_tinh_nguoi_dung,
        ma_hd, danh_sach_thue_bao, userid){
        var data = [];
        var req = {
            "ngay_hoan_thien": ngay_hoan_thien,
            "ly_do_anh": ly_do_anh,
            "ma_tinh": ma_tinh,
            "ghi_chu": ghi_chu,
            "ma_tinh_nguoi_dung": ma_tinh_nguoi_dung,
            "ma_hd": ma_hd,
            "danh_sach_thue_bao": danh_sach_thue_bao,
            "userid": userid
        };

        return this.GetData(await api.ts_laphd_chuyenquyen_sudung_hoanthienhd(axios, req))
     },
     async khoamo_ic_oc(axios, ma_tb, product, note, userid, userip, ma_ldo, ma_tinh, hdtb_id){
        var data = [];
        var req = {
            "ma_ldo": ma_ldo,
            "note": note,
            "product": product,
            "so_tb": ma_tb,
            hdtb_id
        };
        return this.GetData(await api.khoamo_ic_oc(axios, req))
     },
     async dong_mo_dv(axios, ma_tb, product, note, userid, userip, ma_tinh, hdtb_id){
        var data = [];
        var req = {
            "ghichu": note,
            "ma_dv": product,
            "so_tb": ma_tb,
            "hdtb_id" : hdtb_id
        };
        return this.GetData(await api.dong_mo_dv(axios, req))
     },
     async ts_laphd_chuyentinh_laytt_tb(axios, tracuu_hd, ma_tinh, so_tb, tinh_cu){
        var req = {
            "tracuu_hd": tracuu_hd,
            "ma_tinh": ma_tinh,
            "so_tb": so_tb,
            "tinh_cu": tinh_cu
          };
         return this.GetData(await api.ts_laphd_chuyentinh_laytt_tb(axios, req))
     },
     async ts_laphd_chuyentinh_hoanthanh(axios, ma_tinh, thuebao_id, userid, ip){
        var data = {
            IssSuccessed: false,
            Message: ""
        };
        var req = {
            "ma_tinh": ma_tinh,
            "thuebao_id": thuebao_id,
            "ip": ip,
            "userid": userid
        };

        await api.ts_laphd_chuyentinh_hoanthanh(axios, req).then(res=>{
          if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
              data.IssSuccessed = true;
              data.Message = res.data.data;
          }else{
            data.IssSuccessed = false;
            data.Message = res.data.message;
          }

        })
       return data;
     },
     async ts_hoanthien_hd_hoamang_ds_tb(axios, ma_tinh, ma_hd){
        var data = [];
        var req = {
            "ma_tinh": ma_tinh,
            "ma_hd": ma_hd
          };

         return this.GetData(await api.ts_hoanthien_hd_hoamang_ds_tb(axios, req))
     },
     async  ts_hoanthien_hd_hoamang_hoanthanh(axios, ma_tinh, ngay_ht, ghi_chu, ma_hd, userid, ds_thuebao_id, ma_nv, loaikhachhang_1_2, ma_kh, lydo_khonganh){
        var data = {
            IssSuccessed: false,
            Message: "Lỗi hoàn hoàn thiện"
        };
        console.info('ds_thuebao_id',ds_thuebao_id);
        var req = {
            "ma_tinh": ma_tinh,
            "ngay_ht": ngay_ht,
            "ghi_chu": ghi_chu,
            "ma_hd": ma_hd,
            "userid": userid,
            "ds_thuebao_id": ((typeof ds_thuebao_id == "string")?(ds_thuebao_id+'').split(","):ds_thuebao_id),
            "ma_nv": ma_nv,
            "loaikhachhang_1_2": loaikhachhang_1_2
            ,"ma_kh": ma_kh
            ,"lydo_khonganh": lydo_khonganh
        };
        await  api.ts_hoanthien_hd_hoamang_hoanthanh(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data.IssSuccessed = true;
                data.Message = res.data.data;
            }else{
              data.IssSuccessed = false;
              data.Message = res.data.message;
            }

          })
         return data;
     },
     async tt_3_dktt(axios, vma_tb, vten_kh, vngay_sinh, vcmt, vpassport, vcustomer_type, vdiachi_kh, vcongty, vnghenghiep,
                                vgioitinh, vquoctich, vemail, vloai_gt, vnoicap,
                                vghichu, vngay_cap, nam_so_sim, msisdn,
                                vprovince, vsub_type, vdiachi_giaodich, vso_daily, vcontract_type){
        var data = [];
        // var req = {
        //     ma_tb: vma_tb,
        //     ten_kh: vten_kh,
        //     ngay_sinh: vngay_sinh,
        //     cmt: vcmt,
        //     passport: vpassport,
        //     customer_type: vcustomer_type,
        //     diachi_kh: vdiachi_kh,
        //     congty: vcongty,
        //     nghenghiep: vnghenghiep,
        //     gioitinh: vgioitinh,
        //     quoctich: vquoctich,
        //     email: vemail,
        //     loai_gt: vloai_gt,
        //     noicap: vnoicap,
        //     ghi_chu: vghichu,
        //     ngay_cap: vngay_cap,
        //     nam_so_sim: nam_so_sim,
        //     msisdn: msisdn,
        //     province: vprovince,
        //     sub_type: vsub_type,
        //     diachi_giaodich: vdiachi_giaodich,
        //     so_daily: vso_daily,
        //     contract_type: vcontract_type
        // };

        var req = {
            "dia_chi": vdiachi_kh,
            "diachi_coquan": null,
            "diachi_pgd": vdiachi_giaodich,
            "dienthoai_pgd": vso_daily,
            "doituong_sd": null,
            "email": vemail,
            "ghi_chu": vghichu,
            "ghichu_anh": null,
            "gioi_tinh": vgioitinh,
            "ho_chieu": null,
            "loai_gt": vloai_gt,
            "loai_kh": null,
            "loai_kt": null,
            "ma_nuoc": vquoctich,
            "ma_xac_thuc": null,
            "nganhghe": vnghenghiep,
            "ngay_cap_gt": vngay_cap,
            "ngay_sinh": vngay_sinh,
            "noi_cap": vnoicap,
            "so_cmt": vcmt,
            "so_tb": vma_tb,
            "ten_coquan": vcongty,
            "ten_kh": vten_kh,
            "visa": null,
            "visa_hh": null
        }

        await  api.tt_3_dktt(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }

          }).catch(function (res) {
            console.error(res);
            throw `${res.data.message} (lỗi gọi CCBS: tt_3_dktt)`;
          });
        return data;
     },
     async ts_chuyentinh_thuebao_cts_query(axios, so_tb){
        var req = {
            "so_tb": so_tb,
        };

        return this.GetData(await api.ts_chuyentinh_thuebao_cts_query(axios, req));;
     },

     async ts_thongtin_dangky_goicuoc_tb(axios, loai_goi, ds_may, ma_tinh, chu_nhom, ghi_chu, ngay_dk, userid, ip){
        var data = [];
        var req = {
            "loai_goi": loai_goi,
            "ds_may": ds_may,
            "ma_tinh": ma_tinh,
            "chu_nhom": chu_nhom,
            "ghi_chu": ghi_chu,
            "ip": ip,
            "ngay_dk": ngay_dk,
            "userid": userid
        };
        return this.GetData(await  api.ts_thongtin_dangky_goicuoc_tb(axios, req));
     },

     async ts_thongtin_danhmuc_goicuoc_dk_tb(axios, ma_tinh, goicuoc, so_tb){
        var data = [];
        var req = {
            "ma_tinh": ma_tinh,
            "goicuoc": goicuoc,
            "so_tb": so_tb
        };
        await  api.ts_thongtin_danhmuc_goicuoc_dk_tb(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
          }).catch(function (res) {
            console.error(res);
            throw `${res.data.message} (lỗi gọi CCBS: ts_thongtin_danhmuc_goicuoc_dk_tb)`;
          });
         return data;
     },

     async TS_THUPHI_HOAMANGMOI_LAYDS_TB_LD(axios, ma_tinh, ma_hd, userid){
        var data = [];
        var req = {
            "ma_tinh": ma_tinh,
            "ma_hd": ma_hd,
            "userid": userid
          };
        await  api.ts_thuphi_hoamangmoi_layds_tb_ld(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data = res.data.data;
            }
          }).catch(function (res) {
            console.error(res);
            throw `${res.data.message} (lỗi gọi CCBS: ts_thuphi_hoamangmoi_layds_tb_ld)`;
          });
         return data;
     },
     async TS_THUPHI_HOAMANGMOI_GUI_CMDV_KHOITAO_AC(axios, ma_tinh, ghi_chu, so_tb, userid, hdtb_id){
        var data = {
            IssSuccessed: false,
            Message: ""
        };
        var req = {
            "ma_tinh": ma_tinh,
            "ghi_chu": ghi_chu,
            "so_tb": so_tb,
            "userid": userid,
            hdtb_id
          };
        await  api.ts_thuphi_hoamangmoi_gui_cmdv_khoitao_ac(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data.IssSuccessed = true;
            }else{
              data.IssSuccessed = false;
              data.Message = res.data.message;
            }

          }).catch(function (res) {
            console.error(res);
            throw `${res.data.message} (lỗi gọi CCBS: ts_thuphi_hoamangmoi_gui_cmdv_khoitao_ac)`;
          });
         return data;
     },

     async TS_THUPHI_HOAMANGMOI_GUI_CMDV_KHOITAO_LAI(axios, ma_tinh, ghi_chu, so_tb, userid){
        var data = {
            IssSuccessed: false,
            Message: ""
        };
        var req = {
            "ma_tinh": ma_tinh,
            "ghi_chu": ghi_chu,
            "so_tb": so_tb,
            "userid": userid
          };
        await  api.ts_thuphi_hoamangmoi_gui_cmdv_khoitao_lai(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data.IssSuccessed = true;
            }else{
              data.IssSuccessed = false;
              data.Message = res.data.message;
            }

          }).catch(function (res) {
              console.error(res);
            throw `${res.data.message} (lỗi gọi CCBS: ts_thuphi_hoamangmoi_gui_cmdv_khoitao_lai)`;
          });
         return data;
     },

     async TS_THUPHI_HOAMANGMOI_GUI_CMDV_KHOITAO_CMDV(axios, ds_tb_id, ma_tinh, ds_so_tb, ds_nhan_tai_khoan_c2p, ma_hd, userid, hdtb_id){
        var data = {
            IssSuccessed: false,
            Message: ""
        };
        var req = {
            "ds_tb_id": ds_tb_id,
            "ma_tinh": ma_tinh,
            "ds_so_tb": ds_so_tb,
            "ds_nhan_tai_khoan_c2p": ds_nhan_tai_khoan_c2p,
            "ma_hd": ma_hd,
            "userid": userid,
            hdtb_id
        };

        await  api.ts_thuphi_hoamangmoi_gui_cmdv_khoitao_cmdv(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000' && res.data.data && res.data.data.result && res.data.data.result == '0') {
                data.IssSuccessed = true;
            }else{
              data.IssSuccessed = false;
              data.Message = res.data.message;
            }

          }).catch(function (res) {
            console.error(res);
            throw `${res.data.message} (lỗi gọi CCBS: ts_thuphi_hoamangmoi_gui_cmdv_khoitao_cmdv)`;
          });
         return data;
     },

     async upload_img_dktt(axios, ma_tb, img1, create_date1, img2, create_date2, img3, img4, create_date4){
        var data = {
            IssSuccessed: false,
            Message: ""
        };
        var req = {
            "ma_tb": ma_tb,
            "img1": img1,
            "create_date1": create_date1,
            "img2": img2,
            "create_date2": create_date2,
            "img3": img3,
            "img4": img4,
            "create_date4": create_date4,
        };
        await  api.upload_img_dktt(axios, req).then(res=>{
            if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
                data.IssSuccessed = true;
            } else{
              data.IssSuccessed = false;
              data.Message = res.data.message;
            }

          }).catch(function (res) {
            console.error(res);
            throw `${res.data.message} (lỗi gọi CCBS: upload_img_dktt)`;
          });
         return data;
     },

     async ts_upload_img_tt_khcn(axios, so_tb, ly_do, gt_img_1, gt_img_2, gt_img_3, gt_img_4){
        var data = {
            IssSuccessed: false,
            Message: ""
        };
        var req = {
            "gt_img_1": gt_img_1,
            "gt_img_2": gt_img_2,
            "gt_img_3": gt_img_3,
            "gt_img_4": gt_img_4,
            "ly_do": ly_do,
            "so_tb": so_tb
        };

        return this.GetData(await  api.ts_upload_img_tt_khcn(axios, req))
     },

     GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        if (response.data.error_code != 'BSS-00000000'){
          throw response.data.message
        }
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return null
    },

}


