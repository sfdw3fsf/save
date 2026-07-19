import axios from "axios";

export default {
    getDanhSachVersion: (axios) => axios.get('/web-thuno/api/thu-no/bao-cao/version'),
    getDanhSachInv: (axios, data) => axios.get('/web-thuno/api/thu-no/bao-cao/inv-list/' + data),
    getDanhSachGroup: (axios, data) => axios.get('/web-thuno/api/thu-no/bao-cao/arlg-groups', {params: data}),
    getDanhSachFormat: (axios) => axios.get('/web-thuno/api/thu-no/bao-cao/formats'),
    getDanhSachMasterLayout: (axios, data) => axios.get('/web-thuno/api/thu-no/bao-cao/master-layout', {params: data}),
    getDanhSachDataLayout: (axios, data) => axios.get('/web-thuno/api/thu-no/bao-cao/data-layout', {params: data}),
    capNhapDinhDangBanKe: (axios, data) => axios.put('/web-thuno/api/thu-no/bao-cao/dinh-dang-bk', data),
    xoaDinhDangBanKe: (axios, data) => axios.delete('/web-thuno/api/thu-no/bao-cao/dinh-dang-bk', {params: data}),
}