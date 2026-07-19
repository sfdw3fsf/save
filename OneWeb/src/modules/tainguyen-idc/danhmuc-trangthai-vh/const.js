export const importConfig = {
    title: 'Import dữ liệu trạng thái vận hành',
    templateFileName: 'Template_Import_TrangThaiVanHanh.xlsx',
    gridColumns: [
        { key: 'trangthai_vh', label: 'Tên trạng thái' },
        { key: 'ghichu', label: 'Ghi chú' },
        { key: 'sudung', label: 'Hiệu lực' }
    ],
    tableColumns: [
        { key: 'trangthai_vh', label: 'Tên trạng thái' },
        { key: 'ghichu', label: 'Ghi chú' },
        { key: 'sudung', label: 'Hiệu lực (0: Không hiệu lực, 1: Có hiệu lực)' }
    ],
    validationRules: [
        { field: 'trangthai_vh', validator: null },
        { field: 'trangthai_vh', required: true, message: 'Tên trạng thái không được để trống' },
        { field: 'trangthai_vh', maxLength: 200, message: 'Tên trạng thái không được vượt quá 200 ký tự' },
        { field: 'sudung', required: true, type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1' },
        { field: 'ghichu', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' }
    ],
    checkDataResult: true
}
// for controls
export const listOfFields = [
    'ttvh_id',
    'trangthai_vh',
    'ghichu',
    'sudung',
]
// for grid
export const columnsOfGrid = [
    {fieldName: 'ttvh_id', headerText: 'ID', textAlign: 'Left', width: '100px'},
    {fieldName: 'trangthai_vh', headerText: 'Tên trạng thái', textAlign: 'Left'},
    {fieldName: 'ghichu', headerText: 'Ghi chú', textAlign: 'Left'},
    {fieldName: 'hieuluc', headerText: 'Hiệu lực', textAlign: 'Center', width: '100px'},
]
export const defaultItem = {
    ttvh_id: null,
    trangthai_vh: '',
    ghichu: '',
    sudung: 1,
}