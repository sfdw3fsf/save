let root = {}
export default  {
  setupRoot:function(_root){
    root = _root
  },
    
  lay_dulieu_huy_vcc: async function (hdtb_id) {
    try {
      let result = await root.context.get(`/web-hopdong/hopdong/lay_dulieu_huy_vcc?hdtb_id=${hdtb_id}`, {})
      if (result && result.error_code && (result.error_code == 'BSS-00000000' || result.error_code == 'BSS-00000204')) {
        return result.data ? result.data : null
      }
      root.toastError(result.message_detail ? result.message_detail : result.message)
      return null
    } catch (e) {
      console.log(e)
      root.toastError(JSON.stringify(e))
      return null
    }
  },

  DangKy: async function(data){
    try {
      let result = await root.context.post('/tichhop/vcc/DangKy', {
        TimeStamp: data.TimeStamp, //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
        ActionType: 12, //* Value: 12 (đăng ký hotline cố định)
        FeeNumber: data.FeeNumber, //* Số đại diện trừ tiền (Số Hotline)
        CustID: data.CustID, //* ID của khách hàng - MA_KH
        CustName: data.CustName, //* Tên Khách Hàng
        CustAddress: data.CustAddress, //* Địa chỉ đăng ký kinh doanh
        CustManager: data.CustManager, //* Đại diện khách hàng
        Position: data.Position, //* Chức vụ
        LicenseNo: data.LicenseNo, //* Giấy phép kinh Doanh
        DateOfIssue: data.DateOfIssue, //* Ngày cấp (yyyy:mm:dd)
        IssuedBy: data.IssuedBy, //* Đơn vị cấp
        TaxCode: data.TaxCode, //* Mã số thuế
        CustTelNum: data.CustTelNum, //* Số Hotline tổng đài của khách hàng
        CustEmail: data.CustEmail, //* Email
        AdminTel: data.AdminTel, //* Số điên thoại quản trị
        AdminEmail: data.AdminEmail, //* Email quản trị
        WarningRange: data.WarningRange, // Mức cảnh báo cước 
        AMID: data.AMID, // Mã AM 
        AMName: data.AMName, // Tên AM
        AgencyID: data.AgencyID, // Mã Đơn vị kinh doanh
        AgencyName: data.AgencyName, // Tên Đơn vị Kinh doanh
        RegionID: data.RegionID, // Mã Khu vực
        RegionName: data.RegionName, // Tên Khu vực
        PackageName: data.PackageName, //* Tên gói cước: VCC_CALL, VCC_CALLCENTER, VCC_CONTACT_CENTER, VCC_DEMO, VCC_SIPCONNECT
        UserNumber: data.UserNumber, //* Số lượng Agent
        ExtNumber: data.ExtNumber, //* Số lượng extension (máy lẻ)
        AppNumber: data.AppNumber ? data.AppNumber : 0, //* Số lượng softphone (mặc định=0)
        jsonParams: {
          SmartDialer: data.SmartDialer, //* Thể hiện hotline có được đăng ký sử dụng gói cước SmartDialer hay không: NONE: không đăng ký, REG: đăng ký
          SmartDialerDeduce: data.SmartDialerDeduce, //* Tỷ lệ giảm giá của cho gói cước SmartDialer trên ĐHXSKD
          AI_CallBot: data.AI_CallBot, //* Đăng ký sử dụng gói cước AI Callbot hay không: NONE: không đăng ký, REG: đăng ký
          AI_CallBotDeduce: data.AI_CallBotDeduce, //* Tỷ lệ giảm giá của cho gói cước AI_Callbot trên ĐHXSKD
          Voice_OTP: data.Voice_OTP, //* Đăng ký sử dụng gói cước Voice OTP hay không: NONE: không đăng ký, REG: đăng ký
          Voice_OTPDeduce: data.Voice_OTPDeduce, //* Tỷ lệ giảm giá của cho gói cước Voice_OTP trên ĐHXSKD
        },
        HotlineDeduce: data.HotlineDeduce, //* Tỷ lệ giảm giá cho cước thuê bao hotline
        UserDeduce: data.UserDeduce, //* Tỷ lệ giảm giá cho cước thuê bao User
        ExtensionDeduce: data.ExtensionDeduce, //* Tỷ lệ giảm giá cho cước thuê bao User
        InternDeduce: data.InternDeduce, //* Tỷ lệ giảm giá cho cước thoại nội mạng
        ExternDeduce: data.ExternDeduce, //* Tỷ lệ giảm giá cho cước thuê bao Extension 
        CallPackage: data.CallPackage, //* Các gói cước thoại nội mạng và ngoại mạng
        DateExpiration: data.DateExpiration, //* Thời hạn kêt thúc hợp đồng dịch vụ 2023:05:04 00:00:00
      })
      if (result.errorCode != 0){
        root.toastError(result.faultString)
      }
      return result.errorCode
    } catch (e) {
      console.log(e)
    }
  },

  SuaDoiDichVu: async function (data) {
    try {
      let result = await root.context.post('/tichhop/vcc/SuaDoiDichVu', {
        TimeStamp: data.TimeStamp, //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
        ActionType: 3, //* Value: 3
        FeeNumber: data.FeeNumber, //* Số đại diện trừ tiền (Số Hotline)
        CustID: data.CustID, //* ID của khách hàng - MA_KH
        CustName: data.CustName, // Tên Khách Hàng
        CustAddress: data.CustAddress, // Địa chỉ đăng ký kinh doanh
        CustManager: data.CustManager, // Đại diện khách hàng
        Position: data.Position, // Chức vụ
        LicenseNo: data.LicenseNo, // Giấy phép kinh Doanh
        DateOfIssue: data.DateOfIssue, // Ngày cấp (yyyy:mm:dd)
        IssuedBy: data.IssuedBy, // Đơn vị cấp
        TaxCode: data.TaxCode, // Mã số thuế
        CustTelNum: data.CustTelNum, //* Số Hotline tổng đài của khách hàng
        CustEmail: data.CustEmail, // Email
        AdminTel: data.AdminTel, // Số điên thoại quản trị
        AdminEmail: data.AdminEmail, // Email quản trị
        WarningRange: data.WarningRange, // Mức cảnh báo cước 
        AMID: data.AMID, // Mã AM 
        AMName: data.AMName, // Tên AM
        AgencyID: data.AgencyID, // Mã Đơn vị kinh doanh
        AgencyName: data.AgencyName, // Tên Đơn vị Kinh doanh
        RegionID: data.RegionID, // Mã Khu vực
        RegionName: data.RegionName, // Tên Khu vực
        PackageName: data.PackageName, //* Tên gói cước: VCC_CALL, VCC_CALLCENTER, VCC_CONTACT_CENTER, VCC_DEMO, VCC_SIPCONNECT
        UserNumber: data.UserNumber, //* Số lượng Agent
        ExtNumber: data.ExtNumber, //* Số lượng extension (máy lẻ)
        AppNumber: data.AppNumber ? data.AppNumber : 0, //* Số lượng softphone (mặc định=0)
        jsonParams: {
          SmartDialer: data.SmartDialer, //* Thể hiện hotline có được đăng ký sử dụng gói cước SmartDialer hay không: NONE: không đăng ký, REG: đăng ký
          SmartDialerDeduce: data.SmartDialerDeduce, //* Tỷ lệ giảm giá của cho gói cước SmartDialer trên ĐHXSKD
          AI_CallBot: data.AI_CallBot, //* Đăng ký sử dụng gói cước AI Callbot hay không: NONE: không đăng ký, REG: đăng ký
          AI_CallBotDeduce: data.AI_CallBotDeduce, //* Tỷ lệ giảm giá của cho gói cước AI_Callbot trên ĐHXSKD
          Voice_OTP: data.Voice_OTP, //* Đăng ký sử dụng gói cước Voice OTP hay không: NONE: không đăng ký, REG: đăng ký
          Voice_OTPDeduce: data.Voice_OTPDeduce, //* Tỷ lệ giảm giá của cho gói cước Voice_OTP trên ĐHXSKD
        },
        HotlineDeduce: data.HotlineDeduce, // Tỷ lệ giảm giá cho cước thuê bao hotline
        UserDeduce: data.UserDeduce, // Tỷ lệ giảm giá cho cước thuê bao User
        ExtensionDeduce: data.ExtensionDeduce, // Tỷ lệ giảm giá cho cước thuê bao User
        InternDeduce: data.InternDeduce, // Tỷ lệ giảm giá cho cước thoại nội mạng
        ExternDeduce: data.ExternDeduce, // Tỷ lệ giảm giá cho cước thuê bao Extension 
        CallPackage: data.CallPackage, // Các gói cước thoại nội mạng và ngoại mạng
        DateExpiration: data.DateExpiration, //* Thời hạn kêt thúc hợp đồng dịch vụ 2023:05:04 00:00:00
      })
      if (result.errorCode != 0) {
        root.toastError(result.faultString)
      }
      return result.errorCode
    } catch (e) {
      console.log(e)
    }
  },

  HuyDichVu: async function (data) {
    try {
      let result = await root.context.post('/tichhop/vcc/HuyDichVu', {
        TimeStamp: data.TimeStamp, //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
        ActionType: 2, //* Value: 2
        FeeNumber: data.FeeNumber, //* Số đại diện trừ tiền (Số Hotline)
        CustID: data.CustID, //* ID của khách hàng - MA_KH
        CustTelNum: data.CustTelNum, //* Số Hotline tổng đài của khách hàng
      })
      if (result.errorCode != 0) {
        root.toastError(result.faultString)
      }
      return result.errorCode
    } catch (e) {
      console.log(e)
    }
  },

  TamNgungDichVu: async function (data) {
    try {
      let result = await root.context.post('/tichhop/vcc/TamNgungDichVu', {
        TimeStamp: data.TimeStamp, //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
        ActionType: 4, //* Value: 4
        FeeNumber: data.FeeNumber, //* Số đại diện trừ tiền (Số Hotline)
        CustID: data.CustID, //* ID của khách hàng - MA_KH
        CustTelNum: data.CustTelNum, //* Số Hotline tổng đài của khách hàng
      })
      if (result.errorCode != 0) {
        root.toastError(result.faultString)
      }
      return result.errorCode
    } catch (e) {
      console.log(e)
    }
  },

  HuyTamNgungDichVu: async function (data) {
    try {
      let result = await root.context.post('/tichhop/vcc/HuyTamNgungDichVu', {
        TimeStamp: data.TimeStamp, //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
        ActionType: 5, //* Value: 5
        FeeNumber: data.FeeNumber, //* Số đại diện trừ tiền (Số Hotline)
        CustID: data.CustID, //* ID của khách hàng - MA_KH
        CustTelNum: data.CustTelNum, //* Số Hotline tổng đài của khách hàng
      })
      if (result.errorCode != 0) {
        root.toastError(result.faultString)
      }
      return result.errorCode
    } catch (e) {
      console.log(e)
    }
  },

  XoaHotline: async function (data) {
    try {
      let result = await root.context.post('/tichhop/vcc/XoaHotline', {
        TimeStamp: data.TimeStamp, //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
        ActionType: 7, //* Value: 7
        FeeNumber: data.FeeNumber, //* Số đại diện trừ tiền (Số Hotline)
        CustID: data.CustID, //* ID của khách hàng - MA_KH
        CustTelNum: data.CustTelNum, //* Số Hotline tổng đài của khách hàng
      })
      if (result.errorCode != 0) {
        root.toastError(result.faultString)
      }
      return result.errorCode
    } catch (e) {
      console.log(e)
    }
  },

  // 1. AddonID là mã dvgt
  // 2. AddonPrice: cuoc_sd + vatsd
  // 3. AddonCount: noidung
  // 4. để mặc định 2
  DangKyCapNhatDichVuBoSung: async function (data) {
    try {
      let result = await root.context.post('/tichhop/vcc/DangKyCapNhatDichVuBoSung', {
        TimeStamp: data.TimeStamp, //* Thời gian truyền bản tin - Yyyy:mm:dd hh24:mi:ss - 2022:05:04 04:31:02,
        ActionType: 10, //* Value: 10
        FeeNumber: data.FeeNumber, //* Số đại diện trừ tiền (Số Hotline)
        CustID: data.CustID, //* ID của khách hàng - MA_KH
        CustTelNum: data.CustTelNum, //* Số Hotline tổng đài của khách hàng
        AddonID: data.AddonID, //* Loại phí dịch vụ bổ sung: 1: Phí tích hợp dịch vụ VCC, 2: Phí lưu trữ dữ liệu, 3: Phí xây dựng kịch bản AI
        AddonPrice: data.AddonPrice, //* Giá tiền dịch vụ bổ sung(do TTKD nhập theo giá thực tế)
        AddonCount: data.AddonCount, //* Số lượng dịch vụ bổ sung
        AddonInt: 2, //* Kỳ cước thu phí: 1: Một lần, 2: Hàng tháng
      })
      if (result.errorCode != 0) {
        root.toastError(result.faultString)
      }
      return result.errorCode
    } catch (e) {
      console.log(e)
    }
  },

  HuyDangKyDichVuBoSung: async function (data) {
    try {
      let result = await root.context.post('/tichhop/vcc/HuyDangKyDichVuBoSung', {
        TimeStamp: data.TimeStamp, //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
        ActionType: 11, //* Value: 11
        FeeNumber: data.FeeNumber, //* Số đại diện trừ tiền (Số Hotline)
        CustID: data.CustID, //* ID của khách hàng - MA_KH
        CustTelNum: data.CustTelNum, //* Số Hotline tổng đài của khách hàng
        AddonID: data.AddonID, //* Loại phí dịch vụ bổ sung: 1: Phí tích hợp dịch vụ VCC, 2: Phí lưu trữ dữ liệu, 3: Phí xây dựng kịch bản AI
      })
      if (result.errorCode != 0) {
        root.toastError(result.faultString)
      }
      return result.errorCode
    } catch (e) {
      console.log(e)
    }
  },

  CapNhatTinhTrangThiCongHotline: async function (data) {
    try {
      let result = await root.context.post('/tichhop/vcc/CapNhatTinhTrangThiCongHotline', {
        TimeStamp: data.TimeStamp, //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
        ActionType: 13, //* Value: 13
        FeeNumber: data.FeeNumber, //* Số đại diện trừ tiền (Số Hotline)
        CustID: data.CustID, //* ID của khách hàng - MA_KH
        CustTelNum: data.CustTelNum, //* Số Hotline tổng đài của khách hàng
        Task: data.Task, //* Nội dung thi công:”Đăng ký”, “Tạm dừng”, “Huy tạm dừng”
        WorkStatus: data.WorkStatus, //* Tình trạng thi công số hotline của NET: “Đang thi công”, “Hoàn công”
      })
      if (result.errorCode != 0) {
        root.toastError(result.faultString)
      }
      return result.errorCode
    } catch (e) {
      console.log(e)
    }
  },

  CapNhatBaoCao: async function (data) {
    try {
      let result = await root.context.post('/tichhop/vcc/CapNhatBaoCao', {
        TimeStamp: data.TimeStamp, //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
        ActionType: 14, //* Value: 14
        FeeNumber: data.FeeNumber, //* Số đại diện trừ tiền (Số Hotline)
        CustID: data.CustID, //* ID của khách hàng - MA_KH
        CustTelNum: data.CustTelNum, //* Số Hotline tổng đài của khách hàng
        DateReport: data.DateReport, //* Thời gian phát sinh của báo cáo
        Report: data.Report, //* Nội dung báo cáo định dạng CSV hoặc đường link tới file tải về.
      })
      if (result.errorCode != 0) {
        root.toastError(result.faultString)
      }
      return result.errorCode
    } catch (e) {
      console.log(e)
    }
  },
}
