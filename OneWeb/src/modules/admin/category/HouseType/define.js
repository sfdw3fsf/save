import moment from "moment"
export default {    
      actions: [
        {
          id: 'laytt',
          name: 'Lấy TT',
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
        cbo_nhomln:[],
    },
    may_cn:"",
    ip_cn:"",
    is_enable_nhapmoi:false,
    formTT:{
        loainha_id:0,
        ma_loainha:"",
        nhomln_id:0,
        ten_loainha:"",
        sotang_tu:1,
        sotang_den:1,
        dientich_tu:1,
        dientich_den:1,
        sophong_tu:1,
        sophong_den:1        
    },
     
    gvDanhSach: {
        tableCollumns: [
            {
                fieldName: "stt",
                headerText: "STT",
                allowFiltering: true,
                allowSorting: false,
                width: 70,
                textAlign:'Center',
            },
            {
                fieldName: "ma_loainha",
                headerText: "Mã loại nhà",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
            },
            {
                fieldName: "ten_loainha",
                headerText: "Tên loại nhà",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
            },
            {
                fieldName: "dientich_tu",
                headerText: "Diện tích từ",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                textAlign:'Right',
            },
            {
                fieldName: "dientich_den",
                headerText: "Diện tích đến",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                textAlign:'Right',
            },
            {
                fieldName: "sotang_tu",
                headerText: "Số tầng từ",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                textAlign:'Right',
            },
            {
                fieldName: "sotang_den",
                headerText: "Số tầng đến",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                textAlign:'Right',
            },
            {
                fieldName: "sophong_tu",
                headerText: "Số phòng từ",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                textAlign:'Right',
            },
            {
                fieldName: "sophong_den",
                headerText: "Số phòng đến",
                allowFiltering: true,
                allowSorting: false,
                width: 100,
                textAlign:'Right',
            },
        ],
        tableData:[],
    },
    
}