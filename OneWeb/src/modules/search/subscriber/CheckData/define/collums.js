const collumns = {
    columns_ds_dulieu_chuadongbo: [
        {
          fieldName: "TRANGTHAI_XL",
          headerText: "Trạng thái xử lý",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "MA_TB_TINH_DAU",
          headerText: "Mã TB tỉnh đầu",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "MA_TB_TINH_CUOI",
          headerText: "Mã TB tỉnh cuối",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "MA_TINH_CQ",
          headerText: "Mã chỉnh chủ quản",
          allowFiltering: true,
          allowSorting: true,
        }
       ],
       columns_ds_dulieu_dadongbo: [
        {
            fieldName: "MA_TB_TINH_DAU",
            headerText: "Mã TB tỉnh đầu",
            allowFiltering: true,
            allowSorting: true,
            
          },
          {
            fieldName: "MA_TB_TINH_CUOI",
            headerText: "Mã TB tỉnh cuối",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "MA_TINH_CQ",
            headerText: "Mã chỉnh chủ quản",
            allowFiltering: true,
            allowSorting: true,
          },
          {fieldName: 'THAOTAC', headerText: 'Xóa', allowFiltering: false,width:'6%',commands:[{type:'Xóa',buttonOption:{cssClass: 'btn btn-second button-not-bg lh14 pad4',iconCss: '-ap icon-close'}}],cssClass:'text-center'},
       ],
}

export default collumns
