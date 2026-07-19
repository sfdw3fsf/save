const config = {
  UI: {
    header: {
        title: 'ĐỒNG BỘ DỮ LIỆU DANH BẠ VINAPHONE VỀ TỈNH',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Đồng bộ dữ liệu',
            link: { name: 'Ui.buttons' }
          }
        ]
    }
  },
  popup: {
    DongBoTTDanhbaVNP: {
      header: `<span class="icon one-change"></span><span>&nbsp;Đồng bộ trạng thái danh bạ VNP</span>`
    },
    DsHDDaKy: {
      header: `<span class="icon one-notepad"></span><span>&nbsp;Danh sách hợp đồng đã ký</span>`
    },
    animationSetting: { effect: 'Zoom', duration: 400}
  }
  
}

export default config;