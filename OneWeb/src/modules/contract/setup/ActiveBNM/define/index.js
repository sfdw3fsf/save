export default {
    header: {
        title: "KÍCH HOẠT BNM NHÂN CÔNG",
        list: [{
            name: "Cập nhật KQTC",
            link: {
                name: "Ui.cards"
            }
        },
        {
            name: "Thi công mới",
            link: {
                name: "Ui.buttons"
            },
        },
            {
                name: "Kích hoạt BNM",
                link: {
                    name: "Ui.buttons"
                },
            },
        ],
    },
    loading: false,
    nhanvien_id: null,
    donvi_id: null,
    Tag: null,
    ThHdId: null,
    initLoaiHdArr: null,
    initDVVTArr: null,
    position: { X: 'center', Y: 'top' },
    // cb 
    cbTrangThai: [],
    cbTinh: [],
    cbDichVu: [],
    cbLoaiTBI: [],
    cbLoaiHD: [],
    cbHost: [],
    placeholderHost: 'Lựa chọn host',
    placeholderHD: 'Lựa chọn loại hợp đồng',
    placeholderTBI: 'Lựa chọn loại thiết bị',
    placeholderDVVT: 'Lựa chọn dịch vụ viễn thông',
    placeholderTT: 'Lựa chọn trạng thái kích hoạt',

    form: {
        // THONG TIN TIM KIẾM
        chkLenhXN: false,
        radioBtn: 0,
        radioBtnTD: false,
        radioBtnNC: false,
        chkTrangThaiKH: false,
        TrangThaiKH: null,
        chkTuNgayDenNgay: true,
        TuNgayDenNgay: '',
        chkDenNgay: false,
        DenNgay: '',
        chkTinh: false,
        Tinh: null,
        chkDichVuVT: false,
        DichVuVT: 0,
        chkLoaiTBI: false,
        LoaiTBI: 0,
        chkLoaiHD: false,
        disLoaiTBI: false,
        LoaiHD: 0,
        chkMaTB: false,
        MaTB: '',
        chkHost: false,
        Host: 0,
        disHost: false,
    },
    
    button: {
        layTT: true,
        kich_hoat: true,
        gui_lai_lenh: true,
        xuat_file: true,
        gui_yc_kich_hoat: true,
        cap_nhat_serial: true,
        cap_nhat_NE: true,
        khoa_cong: true,
        mo_cong: true,
    },
    selectionOptions: { type: 'Multiple' },
    model: {
        hopdong: {
        },
        
    },
    loadingtable: {
        hopdong: false,
    },
    isLoad: true,
    options: {
        hopdong: [
            
        ]
    },
    config: {
        hopdong: [
            {
                headerText: 'Mã TB',
                allowSorting: true,
                fieldName: 'ma_tb'
            , autoFit: "true" },
            {
                headerText: 'Số ảo',
                allowSorting: true,
                fieldName: 'ma_lt'
            , autoFit: "true" },
            {
                headerText: 'Loại hình',
                allowSorting: true,
                fieldName: 'loaihinh_tb'
            , autoFit: "true" },
            {
                headerText: 'Tự động kích hoạt',
                allowSorting: true,
                fieldName: 'auto_active'
            , autoFit: "true" },
            {
                headerText: 'Trạng thái thuê bao',
                allowSorting: true,
                fieldName: 'trangthai_tb'
            , autoFit: "true" },
            {
                headerText: 'Loại hợp đồng',
                allowSorting: true,
                fieldName: 'loai_td'
            , autoFit: "true" },
            {
                headerText: 'Loại thao tác',
                allowSorting: true,
                fieldName: 'loaithaotac'
            , autoFit: "true" },
            {
                headerText: 'Thao tác',
                allowSorting: true,
                fieldName: 'thaotac'
            , autoFit: "true" },
            {
                headerText: 'Lệnh EWSD',
                allowSorting: true,
                fieldName: 'lenh'
            , autoFit: "true" },
            {
                headerText: 'Người yêu cầu',
                allowSorting: true,
                fieldName: 'nguoi_kh'
            , autoFit: "true" },
            {
                headerText: 'Ngày yêu cầu',
                allowSorting: true,
                fieldName: 'ngay_kh'
            , autoFit: "true" },
            {
                headerText: 'Ngày xác nhận tự động',
                allowSorting: true,
                fieldName: 'ngay_td'
            , autoFit: "true" },
            {
                headerText: 'Ngày xác nhận nhân công',
                allowSorting: true,
                fieldName: 'ngay_kh_nc'
            , autoFit: "true" },
            {
                headerText: 'Nhân viên xác nhận nhân công',
                allowSorting: true,
                fieldName: 'nguoi_kh_nc'
            , autoFit: "true" },
            {
                headerText: 'Lệnh KH',
                allowSorting: true,
                fieldName: 'lenh_kh'
            , autoFit: "true" },
            {
                headerText: 'Kết quả KH',
                allowSorting: true,
                fieldName: 'ketqua_kh'
            , autoFit: "true" },
            {
                headerText: 'Trạng thái',
                allowSorting: true,
                fieldName: 'trangthai_kh'
            , autoFit: "true" },
            {
                headerText: 'Kiểu kích hoạt',
                allowSorting: true,
                fieldName: 'kieu_kh'
            , autoFit: "true" },
            {
                headerText: 'Thương hiệu',
                allowSorting: true,
                fieldName: 'thuonghieu'
            , autoFit: "true" },
            {
                headerText: 'Chủng loại TBI',
                allowSorting: true,
                fieldName: 'loai_tbi'
            , autoFit: "true" },
            {
                headerText: 'Port',
                allowSorting: true,
                fieldName: 'port'
            , autoFit: "true" },
            {
                headerText: 'VCI',
                allowSorting: true,
                fieldName: 'vci'
            , autoFit: "true" },
            {
                headerText: 'VPI',
                allowSorting: true,
                fieldName: 'vpi'
            , autoFit: "true" },
            {
                headerText: 'System',
                allowSorting: true,
                fieldName: 'systemid'
            , autoFit: "true" },
            {
                headerText: 'Rack',
                allowSorting: true,
                fieldName: 'rack'
            , autoFit: "true" },
            {
                headerText: 'Shelf',
                allowSorting: true,
                fieldName: 'shelf'
            , autoFit: "true" },
            {
                headerText: 'Slot',
                allowSorting: true,
                fieldName: 'slot'
            , autoFit: "true" },
            {
                headerText: 'ONT ID',
                allowSorting: true,
                fieldName: 'ont_id'
            , autoFit: "true" },
            {
                headerText: 'IP',
                allowSorting: true,
                fieldName: 'ip'
            , autoFit: "true" },
            {
                headerText: 'Mật khẩu',
                allowSorting: true,
                fieldName: 'matkhau'
            , autoFit: "true" },
            {
                headerText: 'Frame',
                allowSorting: true,
                fieldName: 'frame'
            , autoFit: "true" },
            {
                headerText: 'Đóng/mở',
                allowSorting: true,
                fieldName: 'dongmo'
            , autoFit: "true" },
            {
                headerText: 'ProfileDSL ID',
                allowSorting: true,
                fieldName: 'profiledsl_id'
            , autoFit: "true" },
            {
                headerText: 'Tên Profile',
                allowSorting: true,
                fieldName: 'profilename'
            , autoFit: "true" },
            {
                headerText: 'Port Index',
                allowSorting: true,
                fieldName: 'portindex'
            , autoFit: "true" },
            {
                headerText: 'Poinid',
                allowSorting: true,
                fieldName: 'ponid'
            , autoFit: "true" },
            {
                headerText: 'Community Switch',
                allowSorting: true,
                fieldName: 'community'
            , autoFit: "true" },
            {
                headerText: 'Tên Bras',
                allowSorting: true,
                fieldName: 'brasname'
            , autoFit: "true" },
            {
                headerText: 'Profile ID',
                allowSorting: true,
                fieldName: 'profile_id'
            , autoFit: "true" },
            {
                headerText: 'Ne',
                allowSorting: true,
                fieldName: 'sone'
            , autoFit: "true" },
            {
                headerText: 'Port mdf',
                allowSorting: true,
                fieldName: 'port'
            , autoFit: "true" },
            {
                headerText: 'Số con',
                allowSorting: true,
                fieldName: 'so_con'
            , autoFit: "true" },
            {
                headerText: 'Dịch vụ GT',
                allowSorting: true,
                fieldName: 'ten_dvgt'
            , autoFit: "true" },
            {
                headerText: 'IPPC',
                allowSorting: true,
                fieldName: 'ippc'
            , autoFit: "true" },
            {
                headerText: 'Host',
                allowSorting: true,
                fieldName: 'host'
            , autoFit: "true" },
            {
                headerText: 'Hướng mở',
                allowSorting: true,
                fieldName: 'huongmo'
            , autoFit: "true" }
        ],
    },
    
}
