const GRID_COLUMS = {
  1:  [
          {fieldName: 'MA_LOAIKH', headerText: 'Mã loại', allowFiltering: true, allowSorting:false},
          {fieldName: 'TEN_LOAIKH', headerText: 'loại khách hàng', allowFiltering: true, allowSorting:false}
      ],
  2:   [
      {
        fieldName: "MANGANH",
        headerText: "Mã ngành",
        allowFiltering: true,
        allowSorting: false
      },
      {
        fieldName: "TENNGANHNGHE",
        headerText: "Tên ngành nghề",
        allowFiltering: true,
        allowSorting: false
      }],
  3:    [
          {
            fieldName: "TEN_QUAN",
            headerText: "Quận",
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: "TEN_PHUONG",
            headerText: "Phường",
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: "TEN_PHO",
            headerText: "Phố",
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: "TEN_KHU",
            headerText: "Khu",
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: "TEN_AP",
            headerText: "Ấp",
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: "DACDIEM",
            headerText: "Đặc điểm",
            allowFiltering: true,
            allowSorting: false
          }
        ],
          4:    [
              {
                fieldName: "DOANHTHU_TU",
                headerText: "Doanh thu từ",
                allowFiltering: true,
                allowSorting: false
              },
              {
                fieldName: "DOANHTHU_DEN",
                headerText: "Doanh thu đến",
                allowFiltering: true,
                allowSorting: false
              }],
              5:    [
                  {
                    fieldName: "DIEMTN_TU",
                    headerText: "Điểm tín nhiệm từ",
                    allowFiltering: true,
                    allowSorting: false
                  },
                  {
                    fieldName: "DIEMTN_DEN",
                    headerText: "Điểm tín nhiệm đến",
                    allowFiltering: true,
                    allowSorting: false
                  }]                            
      }
      export default GRID_COLUMS;