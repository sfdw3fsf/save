export default {
  header: {
    title: "Cập nhật server mail",
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

  txtServer: null,
  txtUsername: null,
  txtPassword: null,
  txtPort: null,
  txtGhiChu: null,
  MAIL_SERVER_ID: null,
  current: {
    server: null,
    username: null,
    password: null,
    port: null,
    ghichu: null,
    id: null
  },
  options: {
    danh_sach_mail_server: []
  },

  button: {
    nhapmoi: false,
    huybo: false,
    xoa: false,
    ghilai: false
  },

  columns_danh_sach_mail_server: [
    {
      fieldName: "stt",
      headerText: "Stt",
      allowFiltering: true,
      allowSorting: true,
      width: '70'
    },
    {
      fieldName: "server",
      headerText: "Server",
      allowSorting: true,
      allowFiltering: true,
      width: 'auto'
    },
    {
      fieldName: "username",
      headerText: "Username",
      allowSorting: true,
      allowFiltering: true,
      width: 'auto'
    },
    {
      fieldName: "password",
      headerText: "Password",
      allowFiltering: true,
      allowSorting: true,
      width: 'auto'
    },
    {
        fieldName: "port",
        headerText: "Port",
        allowFiltering: true,
        allowSorting: true,
        width: 'auto'
    }
  ],

}