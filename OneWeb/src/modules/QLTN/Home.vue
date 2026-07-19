<template>
  <div style="height: 100vh; overflow: auto; padding: 10px 10px 80px 10px">
    <h3>Home Page</h3>
    <div class="row">
      <ul>
        <li>
          <router-link active-class="" to="/qltn/TraCuuPhieuDaGiao">QLTN - Tra cứu phiếu đã giao</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuCuocPhatSinh">QLTN - Tra cứu cước phát sinh</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuPhieuTraTheoNgay">QLTN - Tra cứu phiếu trả trong ngày
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuPhieuTraTheoUser">QLTN - Tra cứu phiếu trả theo user
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/QuanLyNhanVienNopTien">[UR3.2.021] - Quản lý nhân viên nộp tiền
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XacDinhCongNoTheoDanhSach">QLTN - Xác định công nợ theo danh sách
          </router-link>
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('formYeuCauMoKhoa')">
            <span class="icon one-calendar-sync"></span> Tra cứu phiếu yêu cầu
            mở khóa mở
          </a>
          <app-tra-cuu-phieu-yeu-cau-mo-khoa ref="formYeuCauMoKhoa"></app-tra-cuu-phieu-yeu-cau-mo-khoa>
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('formKhachHangTreoNo')">
            <span class="icon one-calendar-sync"></span> Tra cứu khách hàng treo
            nợ
          </a>
          <app-tra-cuu-khach-hang-treo-no ref="formKhachHangTreoNo"></app-tra-cuu-khach-hang-treo-no>
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('formKhachHangKhatNo')">
            <span class="icon one-calendar-sync"></span> Tra cứu khách hàng khất
            nợ
          </a>
          <app-tra-cuu-k-h-khat-no ref="formKhachHangKhatNo"></app-tra-cuu-k-h-khat-no>
        </li>
        <li>
          <a v-b-modal.popup-capnhatnhomcongno>
            <span class="icon one-calendar-sync"></span> Cập nhật nhóm công nợ
          </a>
          <CapNhatNhomCongNo />
        </li>

        <li>
          <a v-b-modal.popup-docfilexacnhancongno>
            <span class="icon one-calendar-sync"></span> QLTN - Xác định công nợ
            theo danh sách - đọc từ file
          </a>
          <DocFileXacNhanCongNo />
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuTongNoKhachHang">QLTN - Tra cứu tổng nợ của khách hàng
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuHoaDonChuyenKhoan">QLTN - Tra cứu biên nhận/hóa đơn chuyển khoản
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TCTinhHinhThuNo">QLTN - Tra cứu tình hình thu nợ nhân viên thu cước
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuNopTienCuaKhachHang">[UR3.8.013] - Tra cứu nộp tiền của khách
            hàng</router-link>
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('dlgTongHopKhoaMoMay')">
            <span class="icon one-calendar-sync"></span> QLTN - Tra cứu tổng hợp
            khóa mở máy
          </a>
          <TraCuuTongHopKhoaMoMay ref="dlgTongHopKhoaMoMay" />
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('dlgTraCuuSeriHoaDon')">
            <span class="icon one-calendar-sync"></span> [UR3.8.017] - Tra cứu
            Seri Hóa đơn thanh toán
          </a>
          <TraCuuSeriHoaDonThanhToan ref="dlgTraCuuSeriHoaDon"/>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuXuLyNo">QLTN - Tra cứu xử lý nợ</router-link>
        </li>

        <li>
          <router-link active-class="" to="/qltn/TraCuuNoKhachHang">QLTN - Tra cứu nợ khách hàng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuNoTongHop">Tra cứu nợ tổng hợp</router-link>
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('dlgQuanLyThuTuIn')">
            <span class="icon one-calendar-sync"></span> [UR3.2.002] - Quản lý thứ tự in
          </a>
          <QuanLyThuTuIn ref="dlgQuanLyThuTuIn" />
        </li>
        <li>
          <router-link active-class="" to="/qltn/PhanChiaTuyenThu">QLTN - Phân chia tuyến thu</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuLichSuNhacNo">QLTN - Tra cứu lịch sử nhắc nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuKhoaMoMay">QLTN - Tra cứu khóa mở máy</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuTongHopPhieuGiao">QLTN - Tra cứu tổng hợp phiếu giao
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuTheoLichSuThanhToan">QLTN - Tra cứu theo lịch sử thanh toán
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GiaoHoaDonChuyenKhoan">[UR3.2.007] Giao hóa đơn chuyển khoản
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/InLeXuLyNo">QLTN - In lẻ xử lý nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/ChuyenPhieuLacHuong">QLTN - Chuyển phiếu lạc hướng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/ChuyenPhieuXLNLacHuong">QLTN - Chuyển phiếu XLN lạc hướng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuChuyenPhieuLacHuongPhanChiaKH">QLTN - Tra cứu chuyển phiếu lạc
            hướng - Phân chia khách
            hàng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuDoiChieuTraCuocVinaphoneVTT">QLTN - Tra cứu đối chiếu trả cước
            Vinaphone - Viễn thông
            tỉnh</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/LayDanhSachNo">QLTN - Lấy danh sách nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/tra-cuu-dich-vu">QLTN - Tra cứu dịch vụ sử dụng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/khat-no">QLTN - Khất nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/nhan-phieu">
            QLTN - Nhận phiếu
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/in-hoa-don-chuyen-khoan">
            QLTN - In hóa đơn chuyển khoản
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/thay-doi-tt-khachhang">
            QLTN - Thay đổi thông tin khách hàng
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/xltb-giao-phieu">
            QLTN - Giao phiếu
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/han-nop-tien">
            QLTN - Hạn nộp tiền
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/xac-nhan-nn-no-cuoc">
            QLTN - Xác nhận nguyên nhân nợ cước
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/khoa-mo-no-cuoc">
            QLTN - Hoàn thiện khóa mở nợ cước
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/qldl-giao-phieu">
            QLTN - Giao phiếu(Quản lý đại lý)
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/ds-ban-ke">
            QLTN - Danh sách bản kê
          </router-link>
        </li>
        <li>
          <a href="javascript:void(0)" v-b-modal.popup-taodulieuGBC>
            <span class="icon one-calendar-sync"></span>
            QLTN - Tạo dữ liệu in GBC
          </a>
          <TaoDuLieuInGBC />
        </li>
        <li>
          <a href="javascript:void(0)" v-b-modal.popup-inhoadonchuyendoi>
            <span class="icon one-calendar-sync"></span>
            QLTN - In hóa đơn chuyển đổi
          </a>
          <InHoaDonChuyenDoi />
        </li>
        <li>
          <router-link active-class="" to="/qltn/in-phieu-tt-hop-dong">
            QLTN - In phiếu TT hợp đồng
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TiepNhanYeuCauKhoaMo">[UR3.3.015] - Tiếp nhận yêu cầu khóa mở
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/KhoaMayThueBaoNoCuoc">QLTN - Khóa máy theo danh sách - mới xác minh
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TiepNhanThongTinKhoaMoNoCuoc">QLTN - Tiếp nhận thông tin khóa/mở nợ
            cước</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TaoDanhSachNangGoi">QLTN - Tạo danh sách nâng gói</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TaoDanhSachHaGoi">QLTN - Tạo danh sách ha gói</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuKhachHangLon">QLTN - Tra cứu khách hàng lớn</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/MoMayTheoTungSoMayAcc">QLTN - Mở máy theo từng số máy/Acc</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TaoDanhSachThanhLyThueBaoTamDung">QLTN - Tạo danh sách thanh lý thuê
            bao tạm dừng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/NhacNoThueBao">QLTN - Nhắc nợ thuê bao</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TaoDuLieuXacNhanCongNo">QLTN - Tạo dữ liệu xác nhận công nợ
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GanNhomCongNo">QLTN - Gán nhóm công nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XacNhanCongNoTheoDanhSach">QLTN - Xác nhận công nợ theo danh sách
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XacNhanHoaDonChuyenKhoan">QLTN - Xác nhận hóa đơn chuyển khoản
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TreoNo">[UR3.3.010] - Treo nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/NhacNoThanhToan">QLTN - Nhắc nợ thanh toán</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/KhachHangDacBietThang">QLTN - Khách hàng đặc biệt tháng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/InThongBaoCuoc">QLTN - In thông báo cước</router-link>
        </li>

        <li>
          <a href="#" v-on:click.prevent="openDialog('dlgInThongBaoNo')">
            <span class="icon one-calendar-sync"></span> QLTN - In thông báo nợ
          </a>
          <InThongBaoNo ref="dlgInThongBaoNo" />
        </li>
        <li>

          <router-link active-class="" to="/qltn/GiaoPhieuTon">QLTN - Giao phiếu tồn</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/KhoaMayNoCuocTheoTT">QLTN - Khóa máy nợ cước theo thanh toán
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/KhoaMayTheoSoMayAcc">[UR3.3.022] - Khóa máy theo số máy/Acc
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TaoDuLieuXuLyNo">QLTN - Tạo dữ liệu xử lý nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/MoMayTheoDanhSach">QLTN - Mở máy theo danh sách</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XacNhanYeuCauMoKhoa">[UR3.3.014] - Xác nhận yêu cầu khóa mở
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TaoDanhSachNhacNo">[UR3.3.004] - Tạo danh sách nhắc nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/DanhSachThueBaoKhongKhoaMo">QLTN - Danh sách thuê bao không khóa mở
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XacNhanChuyenTuyen">[UR3.2.016] - Xác nhận chuyển tuyến</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/CapNhatHoSoCongNo">QLTN - Cập nhật hồ sơ công nợ</router-link>
        </li>
        <li>
          <a v-b-modal.popup-nhomcongnonhanvien>
            <span class="icon one-calendar-sync"></span> Gán nhóm công nợ -
            nhân viên
          </a>
          <NhomCongNoNhanVien />
        </li>
        <li>
          <router-link active-class="" to="/qltn/KetXuatChiTietCongNo">QLTN - Kết xuất chi tiết công nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/ThayDoiTuyenThuNVTCFile2">QLTN - Thay đổi tuyến thu, NVTC theo file 2
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XacMinhDiaChiThanhToanDiDong">QLTN - Xác minh địa chỉ thanh toán di
            động</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XacNhanCongNoTheoMaVach">QLTN - Xác nhận công nợ theo mã vạch
          </router-link>
        </li>
        <li>
          <router-link active-class="" :to="{
            name: 'CapNhatKetQuaKhoaMayNoCuoc',
            query: { tag: '27;2070,3059,3060,3069,3070,3079;NEW' },
          }">QLTN - NVKD-Cập nhật kết quả khóa máy nợ cước</router-link>
        </li>
        <li>
          <router-link active-class="" :to="{
            name: 'TrungTamVienThongXacMinhNoCuoc',
            query: { tag: '14;3060;1,4,11' },
          }">QLTN - Trung tâm viễn thông xác minh nợ cước</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XacNhanCongNoTheoMaThanhToan">QLTN - Xác nhận công nợ theo mã thanh
            toán</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/DaiLyNopNganHang">QLTN - Đại lý nộp ngân hàng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GiaoNhanVienXacNhan">QLTN - Giao nhân viên xác nhận</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNoKhachHangThanhLy">QLTN - Gạch nợ khách hàng thanh lý
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNoDiDong">QLTN - Gạch nợ di động </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNo/1">QLTN - Gạch nợ tiền mặt </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNo/46">QLTN - Gạch nợ đại lý </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNo/3">QLTN - Gạch nợ chuyển khoản</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNo/7">QLTN - Điều chỉnh nợ </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNo/7?canbang=1">QLTN - Điều chỉnh nợ cân bằng </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNo/10">QLTN - Hoàn trả tiền tạm thu</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNo/5">QLTN - Thoái trả</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNo/5?tamthu=0">QLTN - Thoái trả (Không có tạm thu)</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNo/8">QLTN - Thanh lý nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNoKhachHangViPhamCamKet">QLTN - Gạch nợ khách hàng vi phạm cam kết
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNoMaVach">QLTN - Gạch nợ mã vạch </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNoTuyenThu">QLTN - Gạch nợ tuyến thu </router-link>
        </li>
        <li>
          <router-link active-class="" :to="{ name: 'DuyetKetQuaXuLyNoCuoc', query: { tag: '15;4060' } }">QLTN - Duyệt
            kết quả xử lý nợ cước</router-link>
        </li>
        <li>
          <router-link active-class="" :to="{ path: '/qltn/thay-doi-tt-khachhang-am', query: { AM: 1 } }">
            QLTN - Thay đổi thông tin khách hàng - AM
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/DieuChinhNoKhieuNai">QLTN - Điều chỉnh nợ khiếu nại</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNoFileChuyenKhoan">QLTN - Gạch nợ file chuyển khoản</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/CapNhatTienKyQuy">QLTN - Cập nhât tiền ký quỹ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuHoaDonDienTuKH">QLTN - Tra cứu hóa đơn điện tử khách hàng
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/ThayDoiTuyenThuNVTCFile">QLTN - Thay đổi tuyến thu, NVTC theo file
          </router-link>
        </li>

        <li>
          <router-link active-class="" to="/qltn/GiaoPhieuXuLyNo">QLTN - Giao phiếu xử lý nợ</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/InHoaDonDienTu">QLTN - In hóa đơn điện tử chuyển đổi
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/InThongBaoCuoc">QLTN - In hóa thông báo cước
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/HuyHoaDon">QLTN - Hủy hóa đơn
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/ThayTheHoaDon">QLTN - Thay thế hóa đơn
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuLichSuDieuChinhHoaDon">QLTN - Tra cứu lịch sử điều chỉnh hóa đơn
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/DieuChinhHoaDon">QLTN - Điều chỉnh hóa đơn
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XuatDuLieuChiTietCuoc">QLTN - Xuất dữ liệu chi tiết cước
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GanDinhDangChoBanKe">QLTN - Gán định dạng cho bản kê
          </router-link>
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('dlgKhoaMoMayMoiXacMinh')">
            <span class="icon one-calendar-sync"></span> Khóa máy theo số máy/Acc - mới xác minh
          </a>
          <KhoaMayTheoSoMayACCMoiXacMinh ref="dlgKhoaMoMayMoiXacMinh" />
        </li>

        <li>
          <router-link active-class="" to="/qltn/XuatFileGachNoVNP">QLTN - Xuất file gạch nợ Vinaphone
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TCCuocGoi">QLTN - Tra cứu cước cuộc gọi
          </router-link>
        </li>

        <li>
          <router-link active-class="" to="/qltn/DongBoThanhLyInternet">QLTN - Đồng bộ thanh lý Internet
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/DoiSoatTienTraNganHang">QLTN - Đối soát tiền trả ngân hàng
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/ThayDoiKenhThu">QLTN - Thay đổi kênh thu
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNoDiDongTienMat">QLTN - Gạch nợ di động - đồng bộ phiếu trả tiền
            mặt
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuKhieuNaiDaiLy">QLTN -Tra cứu khiếu nại đại lý
          </router-link>
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('formGiaoSanLuongNVTC')">
            <span class="icon one-calendar-sync"></span> Giao sản lượng NVTC
          </a>
          <app-giao-san-luong ref="formGiaoSanLuongNVTC"></app-giao-san-luong>
        </li>
        <li>
          <router-link active-class="" to="/qltn/ThayDoiTrangThaiHDDT">QLTN - Thay đổi trạng thái hóa đơn điện tử
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/InUyNhiem">QLTN - In phiếu ủy nhiệm thu
          </router-link>
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('dlgCapNhatDinhDang')">
            <span class="icon one-calendar-sync"></span> QLTN - Cập nhật định
            dạng
          </a>
          <CapNhatDinhDang ref="dlgCapNhatDinhDang" />
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('formInHoaDon')">
            <span class="icon one-calendar-sync"></span> In hóa đơn
          </a>
          <app-in-hoa-don ref="formInHoaDon" tag="ShowDialog+123;124;125"></app-in-hoa-don>
        </li>
        <li>
          <router-link active-class="" to="/qltn/InThuXacNhanCongNo">QLTN - In thư xác nhận công nợ </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GachNoThueBaoDiDongCCBS">QLTN - Gạch nợ di động CCBS</router-link>
        </li>
        <li>
          <router-link active-class="" :to="{ path: '/qltn/XacMinhNNNoCuoc', query: { TTHD_ID: 27 } }">QLTN - Xác minh
            nguyên nhân nợ cước (NKTN) - Nhân viên địa bàn </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/KhoaMayTheoSoMayAcc-ver3">QLTN - Khóa máy theo mã thuê bao
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/QuanLyQuayVong">QLTN - Quản lý thu quay vòng trả trước </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/KhoaMayTheoDanhSach">QLTN - Khóa máy theo danh sách (cũ) </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/BanGoiCuoc_DD">QLTN - Cập nhật kết quả bán gói cước di động
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GiaoViecPSNVTC">Giao việc phát sinh cho NVTC</router-link>
        </li>

        <li>
          <router-link active-class="" to="/qltn/QuanLyNhomCK">Danh sách nhóm chuyển khoản thanh toán</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GiaoKHChamSocKH">Giao kế hoạch CSKH</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/DuyetNoChuyenTTVT">Duyệt danh sách nợ chuyển TTVT</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuDBKH">Tra cứu danh bạ khách hàng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/KhoaMayTheoDanhSach_ver3">Khóa máy theo danh sách (v3)</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/CapNhatTTZalo">Cập nhật thông tin Zalo</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/NhanVienNopTienTraTruocV2">Nhân viên nộp tiền trả trước v2
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/NhanPhieu3">
            Nhận phiếu 3
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuPhieuTraVinaPhone">
            Tra cứu phiếu trả Vinaphone
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/PhanQuyen_CQCD">Cấp quyền chuyển dịch, chuyển quyền thuê bao
          </router-link>
        </li>
        <li>
          <router-link active-class="" :to="{ path: '/qltn/XuLyTBKhoaNoCuoc', query: { HUONGGIAO_ID: 3010 } }">Xử lý
            thuê bao khóa nợ cước</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/InThuXacNhanCongNoMoi">In thư xác nhận công nợ (mới)</router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XacNhanNguyenNhanNoCuoc2">
            Xác nhận nguyên nhân nợ cước (theo Nhật ký thu nợ)
          </router-link>
        </li>

        <li>
          <router-link active-class="" to="/qltn/GDVNhapLyDo">
            GDV cập nhật thông tin nợ
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TCThongTinNo">
            Tra cứu thông tin nợ
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/InChiTiet">
            In chi tiết cước
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/XacNhanNNKhongGiaHan">
            Xác nhận nguyên nhân không gia hạn trước
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/ThietLapTBC">
            Thiết lập gửi thông báo cước
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/GiaoKeHoachCSKHFile">
            Giao kế hoạch CSKH theo File
          </router-link>
        </li>
        <li>
          <router-link active-class="" to="/qltn/TraCuuNoVinaphone">
            Tra cứu thông tin nợ Vinaphone
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import CapNhatNhomCongNo from "./components/CapNhatNhomCongNo/CapNhatNhomCongNo";
import TraCuuPhieuYeuCauMoKhoa from "./TraCuuPhieuYeuCauMoKhoa/TraCuuPhieuYeuCauMoKhoa";
import DocFileXacNhanCongNo from "./components/XacDinhCongNoTheoDanhSach/popup/DocFileXacNhanCongNo";
import TraCuuSeriHoaDonThanhToan from "./TraCuuSeriHoaDonThanhToan/TraCuuSeriHoaDonThanhToan.vue";
import TraCuuKhachHangTreoNo from "./TraCuuKhachHangTreoNo/TraCuuKhachHangTreoNo.vue";
import TraCuuTongHopKhoaMoMay from "./TraCuuTongHopKhoaMoMay/TraCuuTongHopKhoaMoMay.vue";
import QuanLyThuTuIn from "./QuanLyThuTuIn";
import TraCuuKHKhatNo from "./TraCuuKHKhatNo/TraCuuKHKhatNo.vue";
import InThongBaoNo from "./InThongBaoNo";
import InThongBaoCuoc from "./InThongBaoCuoc";
import NhomCongNoNhanVien from "./components/NhomCongNoNhanVien";
import TaoDuLieuInGBC from "./TaoDuLieuInGBC";
import InHoaDonChuyenDoi from "./InHoaDonChuyenDoi";
import KhoaMayTheoSoMayACCMoiXacMinh from "./KhoaMayTheoSoMayACCMoiXacMinh";
import GiaoSanLuongNVTC from "./GiaoSanLuongNVTC";
import CapNhatDinhDang from "./CapNhatDinhDang";
import InHoaDon from "./InHoaDon/InHoaDon.vue";
export default {
  name: "Home",
  components: {
    CapNhatNhomCongNo,
    DocFileXacNhanCongNo,
    TraCuuSeriHoaDonThanhToan,
    appTraCuuPhieuYeuCauMoKhoa: TraCuuPhieuYeuCauMoKhoa,
    appTraCuuKhachHangTreoNo: TraCuuKhachHangTreoNo,
    TraCuuTongHopKhoaMoMay,
    QuanLyThuTuIn,
    appTraCuuKHKhatNo: TraCuuKHKhatNo,
    InThongBaoNo, InThongBaoCuoc,
    NhomCongNoNhanVien,
    TaoDuLieuInGBC,
    InHoaDonChuyenDoi,
    KhoaMayTheoSoMayACCMoiXacMinh,
    appGiaoSanLuong: GiaoSanLuongNVTC,
    CapNhatDinhDang,
    appInHoaDon: InHoaDon,
  },
  data() {
    return {
      header: {
        title: "PHÂN HỆ QUẢN LÝ THU NỢ",
        list: [],
      },
      contenttemplateVue: function () {
        return { template: TraCuuKHKhatNo };
      },
      dlgButtons: [
        {
          click: this.dlgBtnClick,
          buttonModel: { isPrimary: "true", content: "OK" },
        },
        { click: this.dlgBtnClick, buttonModel: { content: "Cancel" } },
      ],
      animationSettings: { effect: "Zoom" },
      OpenBtn: false,
    };
  },
  mounted() { },
  methods: {
    ...mapActions("user", ["clearCurrentUser"]),
    openDialog(obj) {
      // this.$refs.dialogObj.show()
      console.log(this.$refs[obj]);
      this.$refs[obj].openDialog();
    },
    dialogOpen() { },
    dialogClose() { },
    clearData() {
      // TODO clear data
    },
    async logout() {
      await this.$store.dispatch("user/clearCurrentUser").then((response) => {
        console.log(response);
        this.$router.push("Login");
      });
    },
  },
};
</script>
