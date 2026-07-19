import moment from "moment"
export default {    
      actions: [
        {
          id: 'naplai',
          name: 'Lấy DS',
          active: true,
          icon_class: 'one-reload1',
          visible: true
        },
        {
          id: 'nhapmoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-attach ',
          visible: true
        },
        {
          id: 'ghilai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-file-edit2',
          visible: true
        },
        {
            id: 'xoa',
            name: 'Xóa',
            active: true,
            icon_class: 'one-file-arrow-up',
            visible: true
        },
        
      ],
      
    options:{
        cbo_tinhtrang:{
            list:[],
            value:0
        },
        cbo_thietbi: {
            list:[],
            value:0
        },  
        cbo_donvinhan:{
            list:[],
            value:0
        },
    },
    idgan:0,
    ghichu:"",
    may_cn:"",
    ip_cn:"",     
    gvDanhSach: {
        tableCollumns: [            
            {
                fieldName: "tinhtrang_sc",
                headerText: "Tình trạng",
                allowFiltering: true,
                allowSorting: false,
                width: 70,
                textAlign:'Center',
            },
            {
                fieldName: "ten_dv",
                headerText: "Đơn vị",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
            },
            {
                fieldName: "tendslam",
                headerText: "Thiết bị",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
            },
            {
                fieldName: "ghichu",
                headerText: "Ghi chú",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                textAlign:'Left',
            },            
        ],
        tableData:[],
    },
    
}