<template src="./index.html">
</template>

<script>
var portTemplate = function () {
  return {
    template: {
      template: `
      <div>
        <div class="key vcenter padt0 w30" v-if="data.TT_PORT_ID == 0">
          <span class="shape inline w17 h17" style="border-radius: 2px;background-color: #219653;margin-right: 100px;
    margin-left: 20px;"></span>
          <span>{{data.VITRI}}</span>
        </div>
        <div class="key vcenter padt0 w30" v-else-if="data.TT_PORT_ID == 1">
          <span class="shape inline w17 h17" style="border-radius: 2px;background-color: #0176FF;margin-right: 100px;
    margin-left: 20px;"></span>
          <span>{{data.VITRI}}</span>
        </div>
        <div class="key vcenter padt0 w30" v-if="data.TT_PORT_ID == 3">
          <span class="shape inline w17 h17" style="border-radius: 2px;background-color: #219223;margin-right: 100px;
    margin-left: 20px;"></span>
          <span>{{data.VITRI}}</span>
        </div>
        <div class="key vcenter padt0 w30" v-else-if="data.TT_PORT_ID == 4">
          <span class="shape inline w17 h17" style="border-radius: 2px;background-color: #0176BF;margin-right: 100px;
    margin-left: 20px;"></span>
          <span>{{data.VITRI}}</span>
        </div>
        <div class="key vcenter padt0 w30" v-if="data.TT_PORT_ID == 5">
          <span class="shape inline w17 h17" style="border-radius: 2px;background-color: #2196A3;margin-right: 100px;
    margin-left: 20px;"></span>
          <span>{{data.VITRI}}</span>
        </div>
        
      </div>`,
      data() {
        return {
          data: { }
        }
      },
      
      // computed: {
      //   parent() {
      //     return this.$parent.$parent.$parent.$parent.$parent
      //   },
      //   id(){
      //     return `${this.data.CONG_VAO}`
      //   }
      // },
      // methods:{
      //   onClick() {
      //     this.parent.xemPopupQuanLyCap(this.data.CAPVAO_ID)
      //   }
      // }
    }
  }
}
import EventBus from '../../../../utils/eventBus'
export default {
    data() {
        return {
            cardtd_id: 0,
            portTemplate: portTemplate,
            vitri: 0,
            loaicard_id: 0,
            frametd_id: 0,
            nhomCard_id: null,
            tenCard: null,
            loaiCard_id: null,
            slot: null,
            soPort: null,
            soPortCu: null,
            serial: null,
            serial_cu: null,
            partNumber: null,
            phienTB: 0,
            phienTD: 0,
            doiDayTBTu: 0,
            doiDayTDTu: 0,
            gridPort: [],
            dsNhomCard: [],
            dsLoaiCard: [],
            dsPort: [],
            isLoading: false,
            isThemMoi: true,
            tenLoaiCard: null,
            currentItem: {
                VITRI_CAM: null,
                NHOMCARD_ID: null,
                LOAICARD_ID: null,
                SERIAL: null,
                PARTNUMBER: null,
                SO_PORT: null,
                USED: null,
                PORT_BD: null
            },
            tenCardTD: ''
        }
    },
    methods: {
        loadLoaiCard: async function() {
            try {
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/dslam-card/get-loaicard-by-id/${this.loaiCard_id}`);
                return rs.data;
            } catch (error) {

            }
        },
        loadDsLamCard: async function() {
            try {
                this.loading(true);
                await this.loadDsNhomCard();
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/dslam-card/chi-tiet/${this.carddsl_id}`);
                if(rs.data) {
                    this.nhomCard_id = rs.data.NHOMCARD_ID;
                    this.tenCard = rs.data.TEN_CARD;
                    this.loaiCard_id = rs.data.LOAICARD_ID;
                    this.soPort = rs.data.SO_PORT;
                    this.soPortCu = rs.data.SO_PORT;
                    this.serial = rs.data.SERIAL;
                    this.serial_cu = rs.data.SERIAL;
                    this.partNumber = rs.data.PARTNUMBER;
                    this.phienTB = rs.data.PHIEN_TB;
                    this.phienTD = rs.data.PHIEN_TD;
                    this.doiDayTBTu = rs.data.DOIDAYTB_TU;
                    this.doiDayTDTu = rs.data.DOIDAYTD_TU;
                    this.vitri = rs.data.VITRI;
                }
                let rsPortSD = await this.$root.context.get(`/web-ecms/danhmuc/dslam-card/get-port-carddslam-sudung?carddsl_id=${this.carddsl_id}`);
                this.portSuDung = rsPortSD.data.KQ;
                let rsDsPort = await this.$root.context.get(`/web-ecms/danhmuc/dslam-card/lay-port-theo-card/${this.carddsl_id}`);
                this.dsPort = rsDsPort.data;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        clearForm: function() {
            this.currentItem.NHOMCARD_ID = 0
            this.loaicard_id =  0
            this.currentItem.LOAICARD_ID =  0
            this.currentItem.VITRI_CAM =  ''
            this.currentItem.SO_PORT = ''
            this.currentItem.PORT_BD =  1
            this.serial_cu = ''
            this.currentItem.PARTNUMBER =  ''
            this.currentItem.USED =   0
        },
        checkForm: async function() {
            let check = true;
            let error = '';

            if(this.currentItem.SERIAL && (this.currentItem.SERIAL != this.serial_cu)) {
                let kq = await this.$root.context.get(`/web-ecms/danhmuc/DslamModule/kiemtra-trung-serial?serial=${this.currentItem.SERIAL}`);
                if(kq.data.result) {
                    check = false;
                    error = 'Serial này đã được sử dụng !\n'.concat(error);
                }
            }

            if(!this.currentItem.LOAICARD_ID) {
                check = false;
                error = 'Chưa chọn loại card!\n'.concat(error);
            }

            if(!this.currentItem.NHOMCARD_ID) {
                check = false;
                error = 'Chưa chọn nhóm card!\n'.concat(error);
            }

            if(!check) {
                this.$root.toastError(error);
            }
            return check;
        },
        taoDuLieuDslamCard: function() {
           
        },
        themMoi: function() {
            this.isThemMoi = true;
            this.clearForm();
        },
        ghiLai: async function() {
            let kq = await this.checkForm();
            console.log('kq',kq)
            if(kq) {
                if(this.isThemMoi) {
                    try {
                        this.loading(true);
                        let rs = await this.themApi();
                        if(rs.length > 0) {
                            this.cardtd_id = rs[0].CARDTD_ID
                            
                            let ten =this.dsLoaiCard.filter((x) =>
              x.LOAICARD_ID == this.currentItem.LOAICARD_ID)
                            this.tenCardTD = ten[0].LOAI_CARD
                            await this.Tao_port_card_truyendan(rs[0].CARDTD_ID)
                            this.$root.toastSuccess("Thêm mới thành công");
                            this.isThemMoi = false;
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message ? error.response.data.message : "Lỗi khi thêm");
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        var rs = await this.capnhatApi();
                        if(rs[0].KETQUA) {
                            let ten =this.dsLoaiCard.filter((x) =>
              x.LOAICARD_ID == this.currentItem.LOAICARD_ID)
                            this.tenCardTD = ten[0].LOAI_CARD
                            this.$root.toastSuccess("Cập nhật thành công");
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message ? error.response.data.message : "Lỗi cập nhật");
                    } finally {
                        this.loading(false);
                    }
                }
            }
        },
        themApi: async function(){
            console.log('vao day chua')
            let data = {
                loaicard_id: this.currentItem.LOAICARD_ID,
                frametd_id: this.frametd_id,
                vitri: this.vitri,
                portbd: this.currentItem.PORT_BD,
                serial: this.currentItem.SERIAL,
                partNumber: this.currentItem.PARTNUMBER
                // PROCEDURE_NAME: 'ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_CARD_TD_INS'
            }
            let res = await this.$root.context.post('/web-ecms/carttd/them', data)
            return res.data
        },
        Tao_port_card_truyendan: async function(cardtd_id){
            
            // let data = {
            //     P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            //     VCARDTD_ID: cardtd_id,
            //     PROCEDURE_NAME: 'ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.TAO_PORT_CARD_TRUYENDAN'
            // }
            let res = await this.$root.context.post(`/web-ecms/carttd/taoport_cardtd?cardtd_id=${cardtd_id}`);
            return res.data
        },
        capnhatApi: async function(){
            let data = {
                loaicard_id: this.currentItem.LOAICARD_ID,
                frametd_id: this.frametd_id,
                vitri: this.vitri,
                portbd: this.currentItem.PORT_BD,
                serial: this.currentItem.SERIAL,
                partNumber: this.currentItem.PARTNUMBER,
                // PROCEDURE_NAME: 'ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_CARD_TD_UPD'
            }
            let res = await this.$root.context.post(`/web-ecms/carttd/capnhat?cardtd_id=${this.cardtd_id}`, data);
            return res.data
        },
        xoaApi: async function(){
            // let data = {
            //     P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            //     P_CARD_ID: this.cardtd_id,
            //     PROCEDURE_NAME: 'ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_CARD_TD_DEL'
            // }
            let res = await this.$root.context.post(`/web-ecms/carttd/xoa?cardtd_id=${this.cardtd_id}`)
            return res.data
        },

        xoa: async function() {
            try {
                this.loading(true);
                var rs = await this.xoaApi();
                if(rs[0].KETQUA) {
                    this.$root.toastSuccess("Xóa dữ liệu thành công!");
                    return true;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Xóa thất bại !");
            } finally {
                this.loading(false);
            }
        },
        
        layDsNhomCard: async function (id) {
            let result = await this.$root.context.get(
                "/web-ecms/thietbichuyenmach/lay-ds-nhom-card",
                {
                loaitbi_id: id,
                }
            );
            if (result.data.length > 0){
                this.currentItem.NHOMCARD_ID = result.data[0].NHOMCARD_ID
                this.dsLoaiCard = await this.layDsLoaiCard(this.currentItem.NHOMCARD_ID, 50);
            }
            return result.data;
        },
        layDsLoaiCard: async function (id1, id2) {
            let result = await this.$root.context.get(
                "/web-ecms/thietbichuyenmach/lay-ds-loai-card",
                {
                nhomcard_id: id1,
                loaitbi_id: id2,
                }
            );
            console.log('result ',result.data.length)
            if (result.data.length > 0){
                console.log('result ',result.data.length)
                this.currentItem.LOAICARD_ID = result.data[0].LOAICARD_ID
                await this.ChangCBO2()
            }
            return result.data;
        },
        LayThongTinFrame_Card: async function (cardtd_id){
            // let data = {
            //     p_phanvung_id: this.$root.token.getPhanVungID(),
            //     P_CARDTD_ID: cardtd_id,
            //     PROCEDURE_NAME: 'ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_THONGTINFRAME_CARD_SEL'
            // }
            let dtInfo = await this.$root.context.get(`/web-ecms/carttd/thongtin_framecard?cardtd_id=${cardtd_id}`);
            return dtInfo.data;
        },
        PortTDCardSuDung: async function(cardtd_id) {
            // let data = {
            //     p_phanvung_id: this.$root.token.getPhanVungID(),
            //     P_CARDTD_ID: cardtd_id,
            //     PROCEDURE_NAME: 'ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_PORTTDCARDSUDUNG_SEL'
            // }
            let dtInfo = await this.$root.context.get(`/web-ecms/carttd/port_sudung?cardtd_id=${cardtd_id}`);
            return dtInfo.data[0].port_id;
        },
        Lay_port_theo_cardtd: async function(cardtd_id){
            this.gridPort = []
            // let data = {
            //     p_phanvung_id: this.$root.token.getPhanVungID(),
            //     P_CARDTD_ID: cardtd_id,
            //     PROCEDURE_NAME: 'ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_PORT_THEO_CARDTD_SEL'
            // }
            let res = await this.$root.context.get(`/web-ecms/carttd/ds_port_theo_cardtd?cardtd_id=${cardtd_id}`);
            this.gridPort = res.data
        },
        // Kiemtra_serial: async function(serial){
        //     let data = {
        //         P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        //         VSERIAL: serial,
        //         PROCEDURE_NAME: 'ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_KIEMTRA_SERIAL'
        //     }
        //     let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        //     return res.data
        // },
        ViewInfo: async function() {

            let dtInfo = await this.LayThongTinFrame_Card(this.cardtd_id);
            let used = await this.PortTDCardSuDung(this.cardtd_id)
            this.currentItem.NHOMCARD_ID = dtInfo[0].NHOMCARD_ID
            this.dsLoaiCard = await this.layDsLoaiCard(this.currentItem.NHOMCARD_ID, 50);
            this.loaicard_id =  dtInfo[0].LOAICARD_ID
            this.currentItem.LOAICARD_ID =  dtInfo[0].LOAICARD_ID
            this.currentItem.VITRI_CAM =  dtInfo[0].VITRI
            this.currentItem.SO_PORT =  dtInfo[0].SO_PORT
            this.currentItem.PORT_BD =  dtInfo[0].PORT_BD
            this.serial_cu = this.currentItem.SERIAL =  dtInfo[0].SERIAL
            this.currentItem.PARTNUMBER =  dtInfo[0].PARTNUMBER
            this.currentItem.USED =   used > 0 ? used : '0'
            await this.Lay_port_theo_cardtd(this.cardtd_id);

            //     imgComboTrangThai.Items.Add(new DevExpress.XtraEditors.Controls.ImageComboBoxItem(r["VITRI"].ToString(), r["VITRI"], r["TT_PORT_ID"].ToString() == "1" ? 1 : r["TT_PORT_ID"].ToString() == "2" ? 3 : r["TT_PORT_ID"].ToString() == "3" ? 0 : r["TT_PORT_ID"].ToString() == "4" ? 2 : r["TT_PORT_ID"].ToString() == "5" ? 4 : r["TT_PORT_ID"].ToString() == "6" ? 5 : r["TT_PORT_ID"].ToString() == "7" ? 6 : r["TT_PORT_ID"].ToString() == "8" ? 7 : r["TT_PORT_ID"].ToString() == "9" ? 8 : 10));

        },
        
        selectedItemsPort: async function (data) {
      
        },
        ChangCBO: async function(){
            if (this.currentItem.NHOMCARD_ID > 0){
                this.dsLoaiCard = await this.layDsLoaiCard(this.currentItem.NHOMCARD_ID, 50);
            }
        },
        LayThongTin_Loai_Card: async function(id){
            // let data = {
            //     P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            //     P_LOAICARD_ID: id,
            //     PROCEDURE_NAME: 'ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_LAYTHONGTIN_LOAI_CARD'
            // }
            let res = await this.$root.context.get(`/web-ecms/carttd/thongtin_loaicard?loaicard_id=${id}`)
            return res.data
        },
        ChangCBO2: async function(){
            
            if (this.currentItem.LOAICARD_ID > 0){
                this.currentItem.SO_PORT = ''
                let temp = await this.LayThongTin_Loai_Card(this.currentItem.LOAICARD_ID);
                this.currentItem.SO_PORT = temp[0].SO_PORT
            }
        },
        bindData: async function(data) {
            if (data != null){
                this.currentItem.SO_PORT = ''
                this.currentItem.PORT_BD =  1
                this.currentItem.PARTNUMBER =  ''
                this.currentItem.SERIAL =  ''
                this.currentItem.USED =   0
                this.gridPort = []
                this.currentItem.VITRI_CAM = "Slot #" + data.vitri;
                this.currentItem.PORT_BD = 1;
                this.dsNhomCard = await this.layDsNhomCard(50);
                this.frametd_id = data.frametd_id
                this.vitri = data.vitri
                this.isThemMoi = true;
                if (data.cardtd_id != 0) {
                    this.isThemMoi = false;
                    this.cardtd_id = data.cardtd_id
                     await this.ViewInfo();
                }
            } else this.currentItem = {};
        }
    },
    watch: {
        
    },
    mounted: async function() {
        try {
            let vm = this;
            EventBus.$on('thongTinDsLamCard', async data => {
                this.isLoading = true;
                // this.shelfdsl_id = data.shelfdsl_id;
                // this.carddsl_id = data.carddsl_id;
                // this.vitri = data.vitri;
                //await this.loadDsLamCard();
                this.isLoading = false;
            })

            EventBus.$on('themMoi', () => {
                vm.clearForm();
                vm.isThemMoi = true;
            })

            EventBus.$on('ghiLai', () => {
                vm.ghiLai();
            })

            EventBus.$on('xoa', () => {
                vm.xoa();
            })

            EventBus.$on('huyBo', () => {
                vm.clearForm();
            })
        } catch (error) {
            
        }
    },
    destroyed() {
        EventBus.$off("themMoi");
        EventBus.$off("ghiLai");
        EventBus.$off("huyBo");
        EventBus.$off("xoa");
    }
}
</script>
