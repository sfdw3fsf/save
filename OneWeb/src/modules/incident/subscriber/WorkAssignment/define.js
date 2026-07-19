import { BIconTextCenter } from "bootstrap-vue"
import moment from "moment"
export default {     
    searchDate: {        
        ngay_tn: moment(new Date()).add(-7, 'days').format('DD/MM/yyyy'),
        den_ngay: moment(new Date()).format('DD/MM/yyyy'),
        ckb_ngay_tn:false,        
        dateconfig: {
            altFormat: "d/m/Y",
            altInput: true,
            dateFormat: "d/m/Y",
            allowInput: true,
        },
    },
    dateFormat:'DD/MM/YYYY HH:mm',
    dateFormat1:'DD/MM/YYYY',
    actions: [
        {
          id: 'laytt',
          name: 'Lấy TT',
          active: true,
          icon_class: 'one-reload1 ',
          visible: true
        },
        {
            id: 'nhapmoi',
            name: 'Nhập mới',
            active: true,
            icon_class: 'one-file-edit2',
            visible: true
        },
        {
          id: 'capnhat',
          name: 'Cập nhật',
          active: true,
          icon_class: 'one-file-attach',
          visible: true
        },        
        {
          id: 'huybo',
          name: 'Hủy bỏ',
          active: true,
          icon_class: 'one-file-back',
          visible: true
        },
        {
            id: 'xoa',
            name: 'Xóa công việc',
            active: true,
            icon_class: 'one-file-download',
            visible: true
        },        
      ],
    isDisableForm:false,
    ip_cn:'',
    may_cn:'',
    gvDSNVNghi_selectItems:[],
    gvDSNVChuaGan_selectItems:[],
    options:{
        cbo_nhanviennhan: [],
    },    
    formTT: {
        qlcv_id:0,
        ngaytao:moment(new Date()).format('DD/MM/yyyy'),
        nhanviennhan_id:0,
        tungay:'',
        denngay:'',
        ghichu:'',
    },    
    
    gvDSCV: {
        tableCollumns: [
            { fieldName: 'qlcv_id', headerText: 'Số hiệu', textAlign:'Center',mallowFiltering: true, allowSorting: true,freeze:'Left' },
            { fieldName: 'nvthay', headerText: 'Nhân viên nhận', allowFiltering: true, allowSorting: true ,freeze:'Left'},
            { fieldName: 'tu_ngay', headerText: 'Từ ngày', textAlign:'Right',format: {type: 'date', format: 'dd/MM/yyyy HH:mm'},type: 'date',allowFiltering: true, allowSorting: true },
            { fieldName: 'den_ngay', headerText: 'Đến ngày', textAlign:'Right',format: {type: 'date', format: 'dd/MM/yyyy HH:mm'},type: 'date',allowFiltering: true, allowSorting: true },
            { fieldName: 'ngaytao', headerText: 'Ngày lập', textAlign:'Right',format: {type: 'date', format: 'dd/MM/yyyy'},type: 'date',allowFiltering: true, allowSorting: true },
            { fieldName: 'nguoi_cn', headerText: 'Người tạo', allowFiltering: true, allowSorting: true,freeze:'Right' },
        ],
        tableData: []
    },
    gvDSNVNghi: {
        tableCollumns: [
            
            { fieldName: 'ma_nv', headerText: 'Mã NV', allowFiltering: true, allowSorting: true, freeze: 'Left' },
            { fieldName: 'ten_nv', headerText: 'Tên NV', allowFiltering: true, allowSorting: true },
            { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, allowSorting: true,width: 300 },
        ],
        tableData: []
    },
    gvDSNVChuaGan: {
        tableCollumns: [
            
            { fieldName: 'ma_nv', headerText: 'Mã NV', allowFiltering: true, allowSorting: true, freeze: 'Left' },
            { fieldName: 'ten_nv', headerText: 'Tên NV', allowFiltering: true, allowSorting: true },
            { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, allowSorting: true,width: 300 },
        ],
        tableData: []
    },
    gvLichSuNVNghi: {
        tableCollumns: [
            { fieldName: 'tu_ngay', headerText: 'Từ ngày', allowFiltering: true, allowSorting: true,textAlign:'Right',format: {type: 'date', format: 'dd/MM/yyyy HH:mm'},type: 'date' },
            { fieldName: 'den_ngay', headerText: 'Đến ngày', allowFiltering: true, allowSorting: true,textAlign:'Right',format: {type: 'date', format: 'dd/MM/yyyy HH:mm'},type: 'date' },
            { fieldName: 'nv_thay', headerText: 'Nhân viên thay', allowFiltering: true, allowSorting: true ,width: 300},
        ],
        tableData: []
    },
    
}