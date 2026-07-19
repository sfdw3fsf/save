// for controls
export const listOfFields = {
    'id': 'ID VLAN',
    'ten' : 'Tên VLAN',
    'idc_id' : 'IDC',
    'thietbi_id' : 'Thiết bị mạng',
    'hatang_id' : 'Hạ tầng mạng',
    'vrf_id' : 'VRF',
    'zone_id' : 'Zone',
    'ghi_chu' : 'Ghi chú',
    'hieu_luc' : 'Hiệu lực',
}
export const defaultItem = {
    id: null,
    ten: '',
    idc_id: null,
    thietbi_id: [],
    hatang_id: [],
    vrf_id: [],
    zone_id: [],
    ghi_chu: '',
    hieu_luc: 1,
}
export const searchParams = {
    ten: '',
    idc_id: null,
    thietbi_id: null,
    hatang_id: null,
    vrf_id: null,
    zone_id: null
}

export const defaultDropDownNames = {
    thietbi: 'Vui lòng chọn Hạ tầng mạng trước',
    hatang: 'Vui lòng chọn IDC trước',
    vrf: 'Vui lòng chọn Hạ tầng mạng trước',
    zone: 'Vui lòng chọn VRF trước'
}