export default {
  autoAssignIcon: (name) => {
    const displayName = name.toLowerCase();
    const iconMappings = {
      "mật khẩu": "ic:baseline-password",
      "xóa": "ic:baseline-delete",
      "menu": "ep:menu",
      "cập nhật": "dashicons:update-alt",
      "đổi": "uil:exchange",
      "in": "material-symbols:print-outline",
      "lắp đặt": "mdi:network-outline",
      "thi công": "mdi:network-outline",
      "hỏng": "mdi:close-network-outline",
      "tra cứu": "uil:file-search-alt",
      "chi trả": "solar:hand-money-linear",
      "đại lý": "majesticons:users-line",
      "cskh": "fluent:person-support-16-filled",
      "thanh toán": "solar:hand-money-linear",
      "gia hạn": "solar:hand-money-linear",
      "khai báo": "tdesign:file-add",
      "vật tư": "material-symbols:cable",
      "thuê bao": "fluent:sim-24-regular",
      "cấu hình": "tabler:settings-cog",
      "chăm sóc": "fluent:person-support-16-filled",
      "thống kê": "icon-park:ranking",
      "giao việc": "tdesign:undertake-delivery",
      "quản lý": "material-symbols:admin-panel-settings-outline",
      "danh sách": "clarity:list-line",
      "bản đồ": "lucide:map-pinned",
    }
  
    let icon = "uil:favorite";
  
    for (const keyword in iconMappings) {
      if (displayName.includes(keyword)) {
        icon = iconMappings[keyword];
        break;
      }
    }
  
    return icon;
  
  }
} 
