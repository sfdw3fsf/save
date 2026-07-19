export default {
    header: {
      title: "XUẤT HOÁ ĐƠN GIÁ TRỊ GIA TĂNG",
      list: [{
          name: "Thanh toán",
          link: {
            name: "Ui.cards"
          }
        },
        {
          name: "Xuất hóa đơn giá trị gia tăng",
          link: {
            name: "Ui.buttons"
          },
        },
      ],
    },
    button: {
      nhapmoi: true,
      capnhat: true,
      khoaphieu: true,
      sendmail:false
    },
    DONGBO_TEST:{
      /// false: Đồng bộ lên VASC, VISA, Tổng đài; 
      ////true: Không đồng bộ
      TEST : false
    },
    grid: {
      thanhtien: true,
      thanhtien_sauthue: true,
      vat: true,
      thanhtien_truocthue:true,
      tienthue:true,
    },
  }