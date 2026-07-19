export default {
    async ts_prepayment_laytt_goicuoc_hieuluc(context, so_tb) {
        try {
            let response = await context.$root.context.post(`/ccbs/executor/ts_prepayment_laytt_goicuoc_hieuluc`, { so_tb })
            return response.data
        } catch (err) {
            return []
        }
    },
    async ts_prepayment_huy(context, ghichu, kieu_huy, so_tb, id_package_data) {
        try {
            let response = await context.$root.context.post(`/ccbs/executor/ts_prepayment_huy`, { ghichu, kieu_huy, so_tb, id_package_data })
            return response.data
        } catch (err) {
            return []
        }
    },
    async ts_tttien_ckchonso_checktb(context, so_tb) {
        
        let rs = {
            IssSuccessed: "",
            Message: ""
        } 

        try {
            let response = await context.$root.context.post(`/ccbs/executor/ts_tttien_ckchonso_checktb`, { so_tb })
            let rsObj = response.data
            rs.IssSuccessed = rsObj.ResultDesc == "Success" && rsObj.Result == "1" 

            if (!rs.IssSuccessed)
            {
                if (rsObj.Result == "2")
                    rs.Message = "Thuê bao không tồn tại";
                else if (rsObj.Result == "3")
                    rs.Message = "Thuê bao không thuộc tỉnh quản lý";
                else if (rsObj.Result == "4")
                    rs.Message = "Thuê bao khởi tạo tài khoản không được phép đăng ký";
                else if (rsObj.Result == "5")
                    rs.Message = "Thuê bao không là trả sau";
                else if (rsObj.Result == "6")
                    rs.Message = "Thuê bao nằm thuộc kiểu số không được thay đổi cam kết";
                else if (rsObj.Result == "7")
                    rs.Message = "Thuê bao thuộc kho số toàn quốc không được phép thay đổi cam kết";
                else if (rsObj.Result == "8")
                    rs.Message = "Thuê bao không có cam kết";
                else if (rsObj.Result == "9")
                    rs.Message = "Thuê bao 088 không được phép thay đổi cam kết!";
                else
                    rs.Message = rsObj.Result;
            }
        } catch (err) {
            rs.IssSuccessed = false;
            rs.Message = err.toString();
        }

        return rs
    },
    async ts_tttien_ckchonso_laykieuso(context, so_tb) {
        try {
            let response = await context.$root.context.post(`/ccbs/executor/ts_tttien_ckchonso_laykieuso`, { so_tb })
            return response.data
        } catch (err) {
            return []
        }
    },
    async ts_tttien_ckchonso_huy(context, ghi_chu, so_tb, id) {

        let rs = {
            IssSuccessed: "",
            Message: ""
        }

        try {
            let response = await context.$root.context.post(`/ccbs/executor/ts_tttien_ckchonso_huy`, { ghi_chu, so_tb, id })
            let rsObj = response.data;
            rs.IssSuccessed = rsObj.ResultDesc == "Success" && rsObj.Result == "1";

            if (!rs.IssSuccessed)
            {
                if (rsObj.Result == "2")
                    rs.Message = "Thuê bao không tồn tại";
                else if (rsObj.Result == "3")
                    rs.Message = "Thuê bao không thuộc tỉnh quản lý";
                else if (rsObj.Result == "4")
                    rs.Message = "Thuê bao chưa đăng ký thay đổi cam kết";
                else
                    rs.Message = rsObj.Result;
            }
        } catch (err) {
            rs.IssSuccessed = false;
            rs.Message = err.toString();
        }

        return rs
    },
    async ts_dongtruoc_cuoctb_huy(so_tb, kieu_huy) {
        try {
            let response = await context.$root.context.post(`/ccbs/executor/ts_dongtruoc_cuoctb_huy`, { so_tb, kieu_huy })
            return response.data
        } catch (err) {
            return []
        }
    },
    async ts_tamthu_cuocnong_them_phieu(tongtien, ma_kh, ghichu, phieu_id, ma_tn, type, ma_tinh_cu, so_tb, chukyno, httt_id, ngaynhap, ip) {
        
        let rs = {
            IssSuccessed: "",
            Message: ""
        }

        try {
            let response = await context.$root.context.post(`/ccbs/executor/ts_tamthu_cuocnong_them_phieu`, { tongtien, ma_kh, ghichu, phieu_id, ma_tn, type, ma_tinh_cu, so_tb, chukyno, httt_id, ngaynhap, ip })
            let rsObj = response.data
            rs.IssSuccessed = rsObj.Result == "Th&#234;m m&#7899;i th&#224;nh c&#244;ng.";
            rs.Message = rsObj.Result;
        } catch (err) {
            rs.IssSuccessed = false;
            rs.Message = err.toString();
        }

        return rs;
    }
}