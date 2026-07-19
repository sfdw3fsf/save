export default{
    // Combo Box
    Danhsach_dichvu: (axios,data) => axios.post("web-net/tracuuKenhLuong/danhSachDVVienThong",data),
    Danhsach_loaihinhtb: (axios,data) => axios.post("web-net/tracuuKenhLuong/danhSachLoaiHinhTB",data),
    Danhsach_loaikenh: (axios,data) => axios.post("web-net/tracuuKenhLuong/dsLoaiKenh",data),
    Danhsach_tinh: (axios,data) => axios.post("web-net/tracuuKenhLuong/dsTinh",data),
    Danhsach_donvi: (axios,data) => axios.post("web-net/tracuuKenhLuong/dsDonVi",data),
    Danhsach_dieukientimkiem: (axios,data) => axios.post("web-net/tracuuKenhLuong/dsDKTK",data),

    //Sự kiện
    Lay_Danhsach_thuebao: (axios,data) => axios.post("web-net/tracuuKenhLuong/dsThueBao",data),
    Lay_Danhsach_tracuu_thuebao_theodk: (axios,data) => axios.post("web-net/tracuuKenhLuong/dsTraCuuDBTBDK",data),
    
}