## Sử dụng dạng push route thì dùng **HandoverTicket.vue**
    this.$router.push({ 
        path: 'HandoverTicket', 
        query: { 
            magd: this.txtMaGD, 
            huonggiao: huonggiao, 
            quytrinh: quytrinh, 
            madv: this.DichVuVtId, 
            loaihd: 14, 
            trangthaiphieu: '0' 
        } 
    })

## Sử dụng dạng nhúng vào popup thì dùng **HandoverTicket_content.vue**
#### .vue ####
    this.$refs.HandoverTicketContent.showPopup({
        trangthaiphieu: 1, 
        loaihd: LoaiHopDong.CHAMDUT_SD, 
        madv: DichVuVienThong.ANTOAN_BAOMAT_TT, 
        quytrinh: this.quyTrinhId, 
        huonggiao: parseInt(huongGiao), 
        magd: this.frm.maGd
    })
    await this.$refs.HandoverTicket.show(true)

#### .html ####
    <ejs-dialog 
        :enableResize='true' 
        :visible="false" 
        ref="HandoverTicket" :animationSettings="animationSettings"
        :header='"GIAO PHIẾU ĐIỀU HÀNH THI CÔNG"' 
        showCloseIcon=true target=".main-wrapper" 
        @close="onCloseGiaoPhieu"
    >
        <HandoverTicketContent ref="HandoverTicketContent" :isPopup="true"></HandoverTicketContent>
    </ejs-dialog>

*Các xử lý trong hàm showPopup tương ứng xử lý trong hàm mounted*