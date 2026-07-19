<style scoped>
.list-actions-top .list > li > div.button {
    display: block;
    padding: 2px 15px;
    color: #0176FF;
    border-left: 1px solid #BAE7FF;
    text-decoration: none;
    font-weight: 600;
    cursor: pointer;
}
</style>

<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogCatMo" :position="position" :header="'Gửi cắt mở thuê bao lắp đặt mới'" showCloseIcon="true" width="75%" target="#app .main-wrapper">
    <div class="">
        <div class="list-actions-top">
          <ul class="list">
                <li>
                    <a href="#" @click.prevent="tsbnGuiCatMo_Click">
                        <span class="icon nc-icon-glyph arrows-1_simple-right"></span>Cắt mở
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="tsbtnCapNhat_Click">
                        <span class="icon one-file-attach1"></span>Lấy DS
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
                    <div class="box-form" style="padding: 5px 5px">
                        <div class="pull-left fw6 upper" style="margin-top: 9px;">
                            GỬI CẮT MỞ THUÊ BAO LẮP ĐẶT MỚI
                        </div>
                        <div class="pull-right">
                            <div class="info-row">
                                <div class="key w30 nowrap">Ngày cắt mở</div>
                                <div class="value">
                                  <div class="input-icon-right">
                                    <date-picker type="date" :disabled="!controls.dtpNgayCatMo.enabled" input-class="form-control" :format="dateFormat" :value-type="'format'" v-model="controls.dtpNgayCatMo.value"></date-picker>
                                  </div>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Hợp đồng khách hàng</div>
                        <div class="table-content">
                             <DataGrid ref="grcHopDong" class="table-result table-gachle" v-bind:columns="[
                                        {fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowFiltering: true, width: 150},
                                        {fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', allowFiltering: true, type: 'dateTime', format: 'dd/MM/yyyy', width: 100},
                                        {fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true , allowHtml : false, width: 150},
                                        {fieldName: 'diachi_kh', headerText: 'Địa chỉ', allowFiltering: true , allowHtml : false},
                                        {fieldName: 'nguoi_cn', headerText: 'User cập nhật', allowFiltering: true , allowHtml : false, width: 150},
                                    ]"
                                    v-bind:dataSource="controls.grcHopDong.dataSource" :showColumnCheckbox="false"
                                    :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                                    :enabled-select-first-row="true"
                                    @selectedRowChanged="gvHopDong_FocusedRowChanged"
                                    >
                             </DataGrid>
                        </div>
                    </div>

                    <div class="box-form">
                        <div class="legend-title">Hợp đồng thuê bao</div>
                        <div class="table-content">
                             <DataGrid ref="grcThueBao" class="table-result table-gachle" v-bind:columns="[
                                        {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true},
                                        {fieldName: 'simcard', headerText: 'Simcard', allowFiltering: true , allowHtml : false},
                                        {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true , allowHtml : false},
                                        {fieldName: 'diachi', headerText: 'Địa chỉ thuê bao', allowFiltering: true , allowHtml : false},
                                        {fieldName: 'tengoicuoc', headerText: 'Loại thuê bao', allowFiltering: true , allowHtml : false},
                                        {fieldName: 'ten_kieuld', headerText: 'Kiểu yêu cầu', allowFiltering: true , allowHtml : false},
                                    ]"
                                    v-bind:dataSource="controls.grcThueBao.dataSource" :showColumnCheckbox="false"
                                    :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                                    :enabled-select-first-row="true"
                                    >
                             </DataGrid>
                        </div>
                    </div>
                </div>
    </div>
  </ejs-dialog>
</template>
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import api from './PopUpApi'
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";
import { LOAI_HOPDONG, TRANGTHAI_DONGBO, TRANGTHAI_HOPDONG } from '@/constants.js'
import ccbs from "../../ccbsFunctions";
import fn from "../../functions";

export default {
  components: {
    moment,
    DatePicker,
    api,
  },
  name: 'GuiCatMoModal',
  data() {
    return {
      Loading: false,
      datetimeFormat: "DD/MM/YYYY hh:mm A",
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: "",
        ip_cn: "",
        ngay_cn: moment(new Date()).format("DD/MM/YYYY"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY"
        ),
      },
      state: {},
      controls: {
        dtpNgayCatMo: {
          enabled: true,
          value: moment(new Date()).format("DD/MM/YYYY"),
        },
        grcHopDong: {
          dataSource: [],
        },
        grcThueBao: {
          dataSource: [],
        },


      }
    }
  },
  created: async function () {

  },
  mounted: function () {

  },
  watch: {

  },
  methods: {

    dialogClose() {},
    openDialog(data) {
      // if(!data){
      //   return;
      // }

      // this.state =  {
      //   hdtb_id: data.hdtb_id,
      //   phieu_id: data.phieu_id,
      //   loai: data.loai,
      //   kieu: data.kieu ?  data.kieu : 1,
      //   vip_giamsat: data.vip_gs
      // };
      this.pageLoad();
      this.$refs.dialogCatMo.show()
    },
    async pageLoad(){
      this.tt_nd.may_cn = await this.$root.token.getMachine();
      this.tt_nd.ip_cn = await this.$root.token.getIP();
      let thongtin_nguoidung2 = await commonFn.thongtin_nguoidung2(this.axios);
      Object.assign(this.tt_nd, thongtin_nguoidung2);
      console.log("tt_nd", this.tt_nd);

      //this.controls.dtpNgayCatMo.value = this.tt_nd.ngay_cn;
      this.loading(true);
      await this.Lay_DuLieu_HDKH(LOAI_HOPDONG.DAT_MOI, TRANGTHAI_DONGBO.DONGBO_TT, TRANGTHAI_HOPDONG.THANH_TOAN);

    },
    async btnGhiLai_Click(){

    },
    async Lay_DuLieu_HDKH(loai_hd, status, tthd_id){
      let req = {"hdkh_id":0,"loaihd_id":loai_hd,"status":status,"tthd_id":tthd_id}
      let dsHDKH = this.GetData(await api.lay_ds_hdkh_chua_tbdd(this.axios, req));
      console.log('dsHDKH', dsHDKH);
      if(dsHDKH && dsHDKH.length > 0){
        this.controls.grcHopDong.dataSource = dsHDKH;
      }
      else{
          this.controls.grcHopDong.dataSource = null;
          this.controls.grcThueBao.dataSource = null;
      }
      this.loading(false);
    },
    async gvHopDong_FocusedRowChanged(e){
      try{
        this.loading(true);
        this.state.selectedHDKH = e;
        console.log('selectedHDKH', this.state.selectedHDKH);
        if(!this.state.selectedHDKH) return;
        this.loading(true);
        let ma_gd_neo = this.state.selectedHDKH.ma_gd
        let dsThuebao = await this.LayDS_TBLD_Vinaphone(ma_gd_neo);
        console.log('LayDS_TBLD_Vinaphone', dsThuebao);
        this.controls.grcThueBao.dataSource = dsThuebao;
      }catch(ex){
        commonFn.showException(ex);
      }finally{
        this.loading(false);
      }
    },
    async LayDS_TBLD_Vinaphone(ma_gd_neo){
      if (!this.tt_nd.user_neo || this.tt_nd.user_neo == "")
      {
          this.$toast.error("Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
          return [];
      }
      if(!this.state.selectedHDKH) {
        return [];
      }
      let dtCatMo = [];
      if (await this.checkHopDongMNP(this.state.selectedHDKH.hdkh_id) == true)
          dtCatMo = await ccbs.TS_THUPHI_HOAMANGMOI_LAYDS_TB_LD(this.axios, this.tt_nd.tentat_ccbs, ma_gd_neo, this.tt_nd.user_neo);
      else{
        dtCatMo = await ccbs.TS_THUPHI_HOAMANGMOI_LAYDS_TB_LD(this.axios, this.tt_nd.tentat_ccbs, ma_gd_neo, this.tt_nd.user_neo);
        if(dtCatMo && dtCatMo.length > 0)
          dtCatMo = dtCatMo.filter((x) => x.trangthai == 2);
      }

      if(dtCatMo && dtCatMo.length > 0){

        let req = {
          "hdkh_id": this.state.selectedHDKH.hdkh_id
        }
        dtCatMo = commonFn.LowerCasePropertyList(dtCatMo);
        let dsTemp = this.GetData(await api.lay_ds_hdtb_dd_theo_hdkhid(this.axios, req));

        dtCatMo = dtCatMo.map((x) => {
          let x1 = x;
          let current_row = dsTemp.filter((item) =>  item.ma_tb == x.ma_tb.substring(2));
          if(current_row && current_row.length > 0) current_row = current_row[0];
          console.log("current_row", current_row)
          x1.hdtb_id = current_row.hdtb_id;
          x1.somay_ntk = current_row.somay_ntk;
          x1.tengoicuoc = current_row.tengoicuoc;
          x1.ten_kieuld = current_row.ten_kieuld;
          x1.simcard = current_row.simcard;
          x1.goicuoc_id_neo = current_row.goicuoc_id_neo;
          x1.trangthai_catmo = "0";
          x1.thongbao = "";
          x1.ma_hd = current_row.ma_hd;

          return x1;
        });
        console.log("dtCatMo: ", dtCatMo)
      }

      return dtCatMo;
    },
    async checkHopDongMNP(vhdkh_id){
        let breturn = true;
        if (!TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU)
            return breturn;
        let req = {"khachhang_id": vhdkh_id};
        let dt_dsHTTB = await api.fn_kiemtra_hdkh_co_chuyenmang(this.axios, req);
        if (dt_dsHTTB && dt_dsHTTB.length > 0)
        {
            let isMNP_TB = await fn.checkMNP(this.axios, dt_dsHTTB[0]["hdtb_id"]);
            breturn = isMNP_TB;
        }
        return breturn;
    },
    async tsbnGuiCatMo_Click(){
      try{
        this.loading(true);
      var grid = this.$refs.grcThueBao;
      var selectedRows = grid.getSelectedRecords();

      console.log(selectedRows);
      if(!selectedRows || selectedRows.length == 0){
        this.$toast.error("Chưa chọn thuê bao. Hãy tick chọn vào các thuê bao chưa gửi cắt mở để thực hiện.");
        return;
      }

      if (this.state.selectedHDKH && this.state.selectedHDKH.nguoi_cn != this.tt_nd.ma_nd)
      {
          this.$toast.error(`Hợp đồng/phụ lục: ${this.state.selectedHDKH.ma_gd} không phải do user ${this.tt_nd.ma_nd} lập.\nBạn không có quyền đồng bộ dữ liệu lên VinaPhone!`);
          return;
      }

      let ret =  await this.ThucHienGuiCatMo(selectedRows[0])

      // selectedRows.map((x) => {
      //   this.ThucHienGuiCatMo(x)
      // });

      if (ret == 'ok') {
        this.pageLoad();
      }
    }catch(ex){
          console.error(ex);
          commonFn.showException(this, ex);
      }
      finally{
        this.loading(false);
      }
    },
    async tsbtnCapNhat_Click(){
      this.pageLoad();
    },
    async tsbtnThoat_Click(){

    },
    async ThucHienGuiCatMo(drThueBao){

        console.log("ThucHienGuiCatMo drThueBao:", drThueBao);

        this.setTrangThaiCatMo(drThueBao, 1, "");//Đang gửi cắt mở
        var somay = drThueBao["ma_tb"];
        var hdtb_id = drThueBao["hdtb_id"];
        var somay_ntk = "84" + drThueBao["somay_ntk"];
        var RID = drThueBao["rid"];
        var kieuld_id_neo = drThueBao["kieuld_id"];
        var goicuoc_id_neo = drThueBao["goicuoc_id_neo"];

        //var hdkh_id = await commonFn.MAP_ID(this.axios, "hdkh_id", "css.hd_thuebao", "where hdtb_id = " + hdtb_id);
        //var ma_hd = await commonFn.MAP_ID(this.axios, "ma_gd", "css.hd_khachhang", "where hdkh_id = " + hdkh_id);

        var hdkh_id = this.GetData(await api.fn_tt_hd_thuebao(this.axios, { type: 1, param: hdtb_id }));
        var ma_hd = this.GetData(await api.fn_tt_hd_khachhang(this.axios, { type: 2, param: hdkh_id }));

        if (await fn.checkMNP(this.axios, hdtb_id) == true)
        {
            await fn.capnhat_status_hopdong_thuebao(this.axios, {"hdtbId": hdtb_id, "status": TRANGTHAI_DONGBO.DONGBO_CM});
            await api.sp_capnhat_ngayht(this.axios, {"hdtb_id":hdtb_id,"ngay_ht": moment(this.controls.dtpNgayCatMo.value, "DD/MM/YYYY").format("DD/MM/YYYY 00:00:00")});
            this.setTrangThaiCatMo(drThueBao, 2, "");
            this.$toast.success("Đã thực hiện cập nhật trạng thái hợp đồng cho thuê bao MNP: " + somay + "!");
            return;
        }

        if (kieuld_id_neo == "7") //kieuld_id_neo =7 -> Khởi tạo lại ( 1.Khởi tạo AC, 2.Khởi tạo lại)
        {
            let khoitaoAc = await this.KhoiTaoAC(somay, hdtb_id);
            if (khoitaoAc == "OK")
                this.$toast.success("Đã thực hiện gửi cắt mở AC cho số thuê bao " + somay + " trên VinaPhone thành công!");
            else
            {
                this.setTrangThaiCatMo(drThueBao, 3, "Có lỗi xảy ra trong quá trình kết nối với Sercvice VinaPhone!\n" + khoitaoAc);
                return;
            }

            let khoitaolai = await this.KhoiTaoLai(somay);
            if (khoitaolai == "OK")
            {
                this.$toast.success("Đã thực hiện khởi tạo lại cho số thuê bao " + somay + " trên VinaPhone thành công!");
            }
            else
            {
                this.setTrangThaiCatMo(drThueBao, 3, "Có lỗi xảy ra trong quá trình kết nối với Sercvice VinaPhone!\n" + khoitaolai);
                return;
            }
        }
        let strErrorVinaPhone = "";
        let guicatmo3G = await this.GuiCatMo_3G(RID, somay, somay_ntk, ma_hd, hdtb_id);
        if (guicatmo3G == "OK")
        {
            this.$toast.success("Đã thực hiện gửi cắt mở 3G cho số thuê bao " + somay + " trên VinaPhone thành công!")

            await fn.capnhat_status_hopdong_thuebao(this.axios, {"hdtbId": hdtb_id, "status": TRANGTHAI_DONGBO.DONGBO_CM});
            await api.sp_capnhat_ngayht(this.axios, {"hdtb_id":hdtb_id,"ngay_ht": moment(this.controls.dtpNgayCatMo.value, "DD/MM/YYYY").format("DD/MM/YYYY 00:00:00")});

            this.setTrangThaiCatMo(drThueBao, 2, "");
            return 'ok';
        }
        else
        {
            this.setTrangThaiCatMo(drThueBao, 3, "Có lỗi xảy ra trong quá trình kết nối với Sercvice VinaPhone!\n" + strErrorVinaPhone);
            this.$toast.error("Có lỗi thực hiện cắt mở: " + guicatmo3G);
        }

    },
    setTrangThaiCatMo(drThueBao, trangthai, thongbao){
      if(!drThueBao) return;
      drThueBao.trangthai_catmo = trangthai;
      drThueBao.thongbao = thongbao;
    },
    async KhoiTaoAC(somay, hdtb_id)
    {
        try
        {
            if (!this.tt_nd.user_neo || this.tt_nd.user_neo == "")
              return "Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.";

            let PRS_GHI_CHU_IN = "";
            var rs = await ccbs.TS_THUPHI_HOAMANGMOI_GUI_CMDV_KHOITAO_AC(this.axios, this.tt_nd.tentat_ccbs, PRS_GHI_CHU_IN, somay, this.tt_nd.user_neo, hdtb_id);
            if (rs.IssSuccessed)
                return "OK";
            else
              return rs.Message;
        }
        catch (ex)
        {
            throw ex;
        }
    },
    async KhoiTaoLai(somay)
    {
        if (!this.tt_nd.user_neo || this.tt_nd.user_neo == "")
          return "Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.";

        let PRS_GHI_CHU_IN = "";
        var rs = await ccbs.TS_THUPHI_HOAMANGMOI_GUI_CMDV_KHOITAO_LAI(this.axios, this.tt_nd.tentat_ccbs, PRS_GHI_CHU_IN, somay, this.tt_nd.user_neo);
        if (rs.IssSuccessed)
            return "OK";
        else return rs.Message;
    },
    async GuiCatMo_3G(Row_ID, somay, somay_ntk, ma_hd, hdtb_id)
    {
        // console.log("GuiCatMo_3G: ", {
        //   Row_ID: Row_ID,
        //   somay: somay,
        //   somay_ntk: somay_ntk,
        //   ma_hd: ma_hd
        // })

        if (!this.tt_nd.user_neo || this.tt_nd.user_neo == "")
          return "Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.";

        let PRS_GHI_CHU_IN = "";
        var rs = await ccbs.TS_THUPHI_HOAMANGMOI_GUI_CMDV_KHOITAO_CMDV(this.axios, Row_ID + ",", this.tt_nd.tentat_ccbs, somay, somay_ntk, ma_hd, this.tt_nd.user_neo, hdtb_id);
        if (rs.IssSuccessed)
            return "OK";
        else return rs.Message;
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return null
    },

    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  },
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>
