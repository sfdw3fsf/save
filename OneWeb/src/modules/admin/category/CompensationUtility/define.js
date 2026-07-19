export default {
    header: {
      title: "Chuyển thông tin thù lao đại lý sang kỳ hóa đơn mới",
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

    cbothongtin:[],
    cbokyhopdongcu: [],
    cbokyhopdongmoi:[],
    gridloainhanvien: [],
    gridkhuvuc:[],
    options: {
        gridnoidung: []
    },
    form: {
        thongtin: null,
        kyhopdongcu: null,
        kyhopdongmoi: null
    },
    // button: {
    //   ghilai:false,
    //   xoa:false
    // },
    collumns_noidung: [
        {
          fieldName: "tyle_tps",
          headerText: "Tỷ lệ thu cước",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "tyle_tu",
          headerText: "Tỷ lệ PS từ",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
            fieldName: "tyle_den",
            headerText: "Tỷ lệ PS đến",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
        },
        {
            fieldName: "heso_tps",
            headerText: "Hệ số",
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
          }
      ],
  }
  