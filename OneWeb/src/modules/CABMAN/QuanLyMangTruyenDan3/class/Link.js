export default function Link () {
  this.id = null
  this.sourceID = null
  this.targetID = null
  this.Group = null
  this.Name = null
  this.DisplayName = null
  this.ShowCaption = false
  this.Width = null
  this.SelectedWidth = null
  this.RootId = null
  this.DashStyle = null
  this.SelectedDashStyle = null
  this.BackColor = null
  this.SelectedBackColor = null
  this.STYLE_KIEU_DC = (kieudc_id) => {
    switch (kieudc_id) {
      case 1: // Không xác định
        this.DashStyle = 'Solid'
        this.SelectedDashStyle = 'Solid'
        break
      case 2: // Kéo cống
        this.DashStyle = 'Dash'
        this.SelectedDashStyle = 'Dash'
        break
      case 3: // Kéo treo
        this.DashStyle = 'Solid'
        this.SelectedDashStyle = 'Solid'
        break
      case 4: // Chôn
        this.DashStyle = 'DashDotDot'
        this.SelectedDashStyle = 'DashDotDot'
        break
      case 5: // Đề xuất kéo mới
        this.DashStyle = 'Dot'
        this.SelectedDashStyle = 'Dot'
        break
    }
  }
  this.STYLE_NHOM_LC = (nhomlc_id) => {
    switch (nhomlc_id) {
      case 0: // Cáp đồng
        this.BackColor = '#cd853f'
        this.SelectedBackColor = '#8b4513'
        break
      case 1: // Cáp quang
        this.BackColor = '#1E90FF'
        this.SelectedBackColor = '#0000FF'
        break
    }
  }
  this.STYLE_KIEU_CAP = (kieucap_id) => {
    switch (kieucap_id) {
      case 0: // Cáp thuê bao
        this.Width = 1
        this.SelectedWidth = 2
        break
      case 1: // Cáp truyền dẫn
        this.Width = 3
        this.SelectedWidth = 5
        break
    }
  }
}
