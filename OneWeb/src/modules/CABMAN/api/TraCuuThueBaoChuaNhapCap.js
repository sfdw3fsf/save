/* eslint-disable */ 
export default function (apiCaller) {
    return {
        layDsDonVi: () => apiCaller.get('/web-cabman/mang-truyen-dan/cay-donvi') ,
        layDsThueBaoChuaNhapCap: (donvi_id) => apiCaller.get('/web-cabman/mang-truyen-dan/ds-thuebao-chua-nhapcap',{donvi_id : donvi_id}),  
        layLsTacDongCapThueBao: (data) => apiCaller.get('/web-cabman/mang-truyen-dan/ls-tacdong-cap-thuebao',data)         
    }
}