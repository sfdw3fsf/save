export default {
  header: {
    title: "THÔNG BÁO TỪ QUẢN TRỊ",
    list: [{
        name: "Lập hợp đồng",
        link: {
          name: "Ui.cards"
        }
      },
      {
        name: "Lắp đặt mới",
        link: {
          name: "Ui.buttons"
        },
      },
    ],
  },
  txtThongBao: null, 
  chkFlag: null,
  pageSize: 10,
  pageIndex: 0,
  totalRecords: 0,
  current: 0,  
  options: {
    danh_sach_phien_ban: []
  },
  button: {   
    ghilai:true,
    thoat:true   
  },
    collumns: {
    danh_sach_phien_ban: [
      {
        fieldName: "PHIENBAN_ID",
        headerText: "STT",
        allowSorting: true,              
        width: "50",
        textAlign: "center"      
      },
      {
        fieldName: "PHIENBAN",
        headerText: "Phiên bản",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "NGAY_TAO",
        headerText: "Ngày tạo",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "TRANGTHAI",
        headerText: "Trạng thái",
        allowSorting: true,
        autoFit: "true",
        width: "80"
      }
    ]
  },
  rowselected: null
}
