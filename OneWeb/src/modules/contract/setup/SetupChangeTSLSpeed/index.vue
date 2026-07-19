<style scoped src="./SetupChangeTSLSpeed.scss"></style>
<template>
  <div>
    <breadcrumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li :class="!visiable.nhapmoi ? 'non-ative' : ''">
          <a @click="tsbtnNhapMoi_Click()" style="cursor:pointer">
            <span class="icon one-file-plus"></span> Nhập mới
          </a>
        </li>
        <li :class="!visiable.ghilai ? 'non-ative' : ''">
          <a @click="tsbtnGhiLai_Click()" style="cursor:pointer">
            <span class="icon one-save"></span> Ghi lại
          </a>
        </li>
        <li :class="!visiable.huy ? 'non-ative' : ''" style="cursor:pointer">
          <a @click="tsbtnHuyBo_Click()">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
          </a>
        </li>
        <li :class="!visiable.xoaHD ? 'non-ative' : ''" style="cursor:pointer">
          <a @click="tsbtnXoa_Click()">
            <span class="icon one-trash"></span> Xóa HĐ
          </a>
        </li>
        <li :class="!visiable.themTB ? 'non-ative' : ''" style="cursor:pointer">
          <a @click="tsbtnThemTB_Click()">
            <span class="icon one-mobile-plus"></span> Thêm TB
          </a>
        </li>
        <li :class="!visiable.xoaTB ? 'non-ative' : ''" style="cursor:pointer">
          <a @click="tsbtnXoaTB_Click()">
            <span class="icon one-phone-remove"></span> Xóa TB
          </a>
        </li>
        <li :class="!visiable.thanhtoan ? 'non-ative' : ''" style="cursor:pointer">
          <a @click="tsbtnThanhToan_Click()">
            <span class="icon nc-icon-glyph shopping_credit-card"></span> Thanh toán
          </a>
        </li>
        <li :class="!visiable.email ? 'non-ative' : ''" style="cursor:pointer">
          <a @click="tsbtnEmail_Click()">
            <span class="icon one-email"></span> Email
          </a>
        </li>
        <li :class="!visiable.lienhe ? 'non-ative' : ''" style="cursor:pointer">
          <a @click="tsbtnLienHe_Click()">
            <span class="icon nc-icon-glyph business_contacts"></span> Liên hệ
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <form @submit.prevent="preventSubmit" id="form-element">
        <vue-card>
          <b-row>
            <div class="col-md-2"></div>
            <div class="col-md-2"></div>
            <div class="col-md-2">
              <vue-element label="Mã dự án" class="col-md-12 name fw6">
                <ejs-textbox v-model="model.v1_ma_du_an" id="v1_ma_du_an"></ejs-textbox>
              </vue-element>
            </div>
            <div class="col-md-2">
              <div class="info-row">
                <div class="key w20 nowrap">
                  <div class="check-action">
                    <input id="v1_chb_cong_tac_vien"
                      type="checkbox" @change="chkCTV_CheckedChanged"
                      class="check">
                    <span class="name fw6">Cộng tác viên</span>
                  </div>
                  <ejs-dialog :enableResize='true' :visible="false" ref="v1_dlg_congtacvien" :header='"Cộng tác viên"'
                              showCloseIcon=true target=".main-wrapper" width='60%' height="800">
                      <CongTacVien @PopUpCongTacVien_Thoat="PopUpCongTacVien_Thoat()" @acceptChangeCTV="acceptChangeCTV($event)"/>
                  </ejs-dialog>
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="model.v1_cong_tac_vien" id="v1_cong_tac_vien">
                  <!-- <ejs-textbox v-model="model.v1_cong_tac_vien" id="v1_cong_tac_vien"></ejs-textbox> -->
                </div>
              </div>
            </div>
            <div class="col-md-2">
              <div class="info-row">
                <div class="key w20 nowrap">
                  <div class="check-action">
                    <input id="v1_chb_nguoi_gioi_thieu"
                      type="checkbox" @change="chkNGT_CheckedChanged"
                      class="check">
                    <span class="name fw6">Người giới thiệu</span>
                  </div>
                  <ejs-dialog :enableResize='true' :visible="false" ref="v1_dlg_nguoigioithieu" :header='"Người giới thiệu"'
                              showCloseIcon=true target=".main-wrapper" width='60%' height="800">
                      <NguoiGioiThieu @PopUpNguoiGioiThieu_Thoat="PopUpNguoiGioiThieu_Thoat()" @acceptChangeNGT="acceptChangeNGT($event)" />
                  </ejs-dialog>
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="model.v1_nguoi_gioi_thieu" id="v1_nguoi_gioi_thieu">
                  <!-- <ejs-textbox v-model="model.v1_nguoi_gioi_thieu" id="v1_nguoi_gioi_thieu"></ejs-textbox> -->
                </div>
              </div>
            </div>
            <div class="col-md-2">
              <vue-element label="Tổng tiền" class="col-md-12 fw6">
                <ejs-textbox v-model="model.v1_tong_tien" id="v1_tong_tien"
                             style="text-align: right !important; padding-right: 5px;"></ejs-textbox>
              </vue-element>
            </div>
          </b-row>
        </vue-card>
        <div>
          <ThongTinPhieuYeuCau ref="v2_ThongTinPhieuYeuCau" 
                  @CboLoaihinhTB_SelectedValueChanged="cboLoaihinhTB_SelectedValueChanged" 
                  @CboDichVuVT_SelectedValueChanged="cboDichVuVT_SelectedValueChanged"
                  @CboKieuLD_SelectedValueChanged="cboKieuLD_SelectedValueChanged" 
                  @txtMaGD_KeyPress="txtMaGD_KeyPress" 
                  @acceptSearchContract="acceptSearchContract"
                  @chkCV_CheckedChanged="chkCV_CheckedChanged"
                  />
        </div>
        <div class="grid-stack-box">
          <ThongTinThueBao ref="v3_ThongTinThueBao" 
                  @ShowPopupChonKhuyenMai="ShowPopupChonKhuyenMai"
                  @btnChiTietKM_Click="btnChiTietKM_Click" 
                  @txtMaTB_KeyPress="txtMaTB_KeyPress" 
                  @bntLayMaKh_Click="bntLayMaKh_Click"
                  @chkKhuyenMai_CheckedChanged="chkKhuyenMai_CheckedChanged"
                  @cboKhuyenMai_SelectionChangeCommitted="cboKhuyenMai_SelectionChangeCommitted"
                  />
          <div class="gutter gutter-horizontal" style="width: 16px;"></div>
          <ThongTinKhachHang ref="v4_ThongTinKhachHang"
                  @popupTienKM="popupTienKM"
                  @chkDoiTac_CheckedChanged="chkDoiTac_CheckedChanged"
                  />
        </div>
        <vue-card>
          <div class="legend-title">
            <div class="pull-left">
              <span></span> Danh sách thuê bao
            </div>
            <div class="clearfix"></div>
          </div>
          <b-row>
            <div>    
              <DataGridCustom
                  ref="focusXa"
                  v-bind:columns="collumnsDSThueBao"
                  v-bind:dataSource="v5_dtDSThueBao"
                  :showColumnCheckbox="false"
                  :showFilter="true"
                  :enabledSelectFirstRow="true"
                  :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                  panelDataHeight="300"
                  @rowSelected="grid_FocusedRowChanged"
                  :allowPaging="true" :enablePagingServer="false"
              ></DataGridCustom>                                    
            </div>
          </b-row>
        </vue-card>
      </form>
    </div>

    <ejs-dialog :enableResize='true' :visible="false" ref="thongtinemail" :header='"Thông tin Email"'
                showCloseIcon=true target=".main-wrapper" width='60%' height="800">
        <ThongTinEmail ref="frmThongTinEmail" :hdkh_id="Number(hdkh_id)" :khachhang_id="Number(khachhang_id)" />
    </ejs-dialog>

    <ejs-dialog :enableResize='true' :visible="false" ref="thongtinlienhe" :header='"Thông tin liên hệ"'
        showCloseIcon=true target=".main-wrapper" width='60%' height="800">
        <ThongTinLienHe ref="frmThongTinLienHe" :hdkh_id="Number(hdkh_id)" :khachhang_id="Number(khachhang_id)" />
    </ejs-dialog>

    <b-modal id="popupComponentTSLSpeed" size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0">
        <component :is="popupComponent" ref="popupComponent" :dulieu="popupComponentData" isPopup="true" @form-close="popupClosed" @acceptChangeCTV="popupClosed" @acceptChangeNGT="popupClosed"></component> <!--v-dynamic-events="popupComponentEvents"-->
    </b-modal>

    <!-- <ejs-dialog :enableResize='true' :visible='false' ref='thanhtoanDialog' :position='position' :header='"Thanh toán"'
            showCloseIcon='true' :target='targetTop' width='100%'>
        <ThanhToan ref='thanhToanPopUp' />
    </ejs-dialog> -->

    <TienKMCuPopup ref="tienKMCuPopupRef" :loaihd_id="Number(16)" :ds_tien_caidat="ds_tien_khoanmuc" @accept="acceptTienKM" />

    <ejs-dialog :enableResize='true'  :visible='false' ref='thanhtoanDialog' :position='position'
        :header='"THANH TOÁN HỢP ĐỒNG"' showCloseIcon='true' :target='targetTop' width='100%' :open="open_thanhToanPopUp">
        <keep-alive>
            <div class="popupComponentPayment">
                <component :is="userComponentModule"  ref='thanhToanPopUp' :ispopup="true" 
                    :magiaodich='txtMaGDTT'  v-bind="popupComponentAttr" v-on="popupComponentEvents" />
            </div>
        </keep-alive>
    </ejs-dialog>
  </div>
</template>
<script>

import breadcrumb from '@/components/breadcrumb'
import collums from "./define/collums.js";
import api from "./api/index.js";
import api_InstallNewSubs from "@/modules/contract/setup/InstallNewSubs/InstallNewSubsAPI.js";
import DataGridCustom from "./components/DataGrid";
import ThongTinPhieuYeuCau from './ThongTinPhieuYeuCau.vue'
import ThongTinThueBao from './ThongTinThueBao.vue'
import ThongTinKhachHang from './ThongTinKhachHang.vue'
import CongTacVien from "@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien.vue"
import NguoiGioiThieu from "@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu.vue"
import ThongTinEmail from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue"
import ThongTinLienHe from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
import moment from "moment";
// import ThanhToan from '@/modules/contract/setup/Payment/Payment.vue'
import TienKMCuPopup from '@/modules/search/subscriber/ModifyContractMoney/popup/TienKMCuPopup'

const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')

export default {
  components: {
    breadcrumb,
    DataGridCustom,
    ThongTinPhieuYeuCau,
    ThongTinThueBao,
    ThongTinKhachHang,
    CongTacVien,
    NguoiGioiThieu,
    ThongTinEmail,
    ThongTinLienHe,
    // ThanhToan,
    TienKMCuPopup
  },
  data() {
    return {
      ...collums,
      target: '.main-wrapper',
      targetTop: '#app .main-wrapper',
      position: { X: 'center', Y: 'top' },
      LoaiHinhTB: {
          LEASEDLINE: 22,
          INTERNET_ADSL: 11,
          INTERNET_FTTH: 58
      },
      LoaiHopDong: {
          THAY_DOI_TOCDO_TSL: 16
      },
      DichVuVienThong: {
          MEGAWAN: 8,
          ADSL: 4,
          TSL: 9
      },
      KHOANMUC_TT: {
          KMTT_DATMOI: 1,
          KMTT_DOITOCDO: 3,
          KMTT_KM_LAPDAT: 19
      },
      TrangThaiHD: {
          MOI: 1
      },
      TrangThaiTB: {
          THUONG: 1
      },
      visiable: {
        nhapmoi: true,
        ghilai: true,
        huy: true,
        xoaHD: true,
        themTB: true,
        xoaTB: true,
        thanhtoan: true,
        email: true,
        lienhe: true,
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentAttr: {},
      popupComponentEvents: {
        'form-close':'popupClosed',
        'acceptChangeCTV': 'popupClosed',
        'acceptChangeNGT': 'popupClosed',
        paymentcreated: this.paymentcreated
      },
      txtMaGDTT: "",
      paymentloaded:false,
      userComponentModule: null,
      ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
      model: {
        v1_ma_du_an: "",
        v1_cong_tac_vien: "",
        v1_nguoi_gioi_thieu: "",
        v1_tong_tien: "",
        lblTongTien: "0"
      },
      v5_dtDSThueBao: [],
      ds_tien_khoanmuc: [],
      nguoigt_id: null,
      loainv_nguoi_gt_id: null,
      donvi_nguoi_gt_id: null,
      ctv_id: null,
      donvi_ctv_id: null, 
      loainv_ctv_id: null,
      trangbi_id: 3,    // Default: khách hàng tự trang bị
      phuongld_id: null,
      phold_id: null,
      apld_id: null,
      khuld_id: null,
      hdkh_id: null,
      khachhang_id: null,
      hdkh_cha_id: null,
      hdtb_id: null,
      thuebao_id: null,
      diachi_tb: null,
      dtienld_tsl: null,
      dvatld_tsl: null,
      doituong_id: 1,
      dtienld_tsl: 0,
      dvatld_tsl: 0,
      dtienkmld: 0,
      dvatkmld: 0,
      tong_kmld: 0,
      tyle_kmld: 0,
      diachi_dau_id: null,
      diachi_cuoi_id: null,
      loaikenh_id: null,
      quytrinh_id: null,
      dtCTKM: [],   
      dsHDKH: [],
      dsHDTB: [],
      dsTDTD_TSL: [],
      dsTTTT: [],
      dsCTTHD: [],
      dsHDTBDV: [],
      dsKM_HDTB: [],
      header: {
        title: "Thay đổi tốc độ TSL",
        list: [
          { name: "Lập hợp đồng/Đổi tốc độ", link: { name: "Ui.cards" } },
          {
            name: "Loại hình thuê bao/Thay đổi tốc độ TSL",
            link: { name: "Ui.buttons" },
          },
        ],
      },
    };
  },
  async mounted() {
    this.hdkh_cha_id = this.$route.query.hdkh_cha_id != undefined ? this.$route.query.hdkh_cha_id : null
    await this.init();
    if (this.$route.query.ma_kh != undefined && this.$route.query.ma_kh != null && this.$route.query.ma_kh != "") {
        this.$refs.v4_ThongTinKhachHang.model.v4_ma_khach_hang = this.$route.query.ma_kh
    }
    if (this.$route.query.ma_tb != undefined && this.$route.query.ma_tb != null && this.$route.query.ma_tb != "") {
        this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc = this.$route.query.ma_tb
        await this.txtMaTB_Change()
    }
    if (this.$route.query.ten_kh != undefined && this.$route.query.ten_kh != null && this.$route.query.ten_kh != "") {
        this.$refs.v4_ThongTinKhachHang.model.v4_ten_khach_hang = this.$route.query.ten_kh
    }
    if (this.$route.query.sdt != undefined && this.$route.query.sdt != null && this.$route.query.sdt != "") {
        
    }
  },
  methods: {
    open_thanhToanPopUp() {
    //   this.loading(true)
    },
    async paymentcreated(val) {      
      this.paymentloaded = true
      this.$refs.thanhtoanDialog.show(true)
    },
    preventSubmit() {
        return false;
    },
    formatNumber(value) {
        let val = (value / 1).toFixed(0).replace(',', '.')
        return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    },
    Unformat_tien(s){
        s = s.toLowerCase().trim();
        let ketqua = "";
        for (let i = 0; i < s.length; i++) {
            if (String(s[i]) != "," && String(s[i]) != "." && String(s[i]) != " ") {
                ketqua = ketqua + s[i];
            }
        }
        return ketqua.trim();
    },
    ChuanHoaTen(ten) {
        let ten_new = "";
        let str_con = ten.trim();
        let str_xl = "";
        let k = 0;
        let found = true;

        try {
            while (str_con.indexOf("  ") > 0)
                str_con = str_con.replace("  ", " ");
            if (str_con.length < 1) return "";
            while (found) {
                k = str_con.indexOf(" ");
                if (k > 0) {
                    str_xl = str_con.substring(0, k);
                    str_con = str_con.substring(k + 1);
                    str_xl = this.ChuyenChuHoa(str_xl);
                    if (ten_new != "")
                        ten_new = ten_new + " " + str_xl;
                    else
                        ten_new = str_xl;
                    found = true;
                } else
                    found = false;
            }

            if (str_con != "") {
                str_xl = str_con;
                str_xl = this.ChuyenChuHoa(str_xl);
                if (ten_new != "")
                    ten_new = ten_new + " " + str_xl;
                else
                    ten_new = str_xl;
            }

            return ten_new;
        } catch (ex) {
            console.log(ex)
            str_xl = ex.message;
            return ten;
        }
    },
    ChuyenChuHoa(str) {
        let kq = "";
        let k = 0;
        for (let i = 0; i < str.length; i++) {
            if (str[i] == str[i].toUpperCase()) {
                if (i < str.length - 1) {
                    if (str[i+1] == str[i+1].toUpperCase()) {
                        k = 1;
                        break;
                    }
                }
            }
        }
        if (k == 1) {
            kq = str.toUpperCase();
        } else {
            if (String(str[0]) == "(") {
                str = str.toLowerCase();
                str = str.substring(1, str.length - 1);
                kq = str.substring(0, 1).toUpperCase() + str.substring(1, str.length).toLowerCase();
                kq = "(" + kq;
            } else {
                str = str.toLowerCase();
                kq = str.substring(0, 1).toUpperCase() + str.substring(1, str.length).toLowerCase();
            }
        }
        return kq;
    },
    Catdaunhay(str) {
        str = str.replace("'", "");
        return str;
    },
    clearTheSpace(st) {
        let st1 = "";
        st = st.trim();
        while (st.length != 0) {
            st += " ";
            let i = st.indexOf(' ');
            st1 += st[0] + st.substring(1, i);
            st = st.substring(i + 1).trim();
        }
        return st1.trim();
    },
    tsbtnThanhToan_Click() {
        if (!this.visiable.thanhtoan) return;

        this.txtMaGDTT = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich
        this.userComponentModule = thanhToan
        if (this.paymentloaded) this.$refs.thanhtoanDialog.show(true)
    },
    async tsbtnNhapMoi_Click() {
        if (!this.visiable.nhapmoi) return;
        await this.setButton(1);
    },
    async tsbtnHuyBo_Click() {
        if (!this.visiable.huy) return;
        await this.setButton(0);
    },
    tsbtnEmail_Click() {
        if (!this.visiable.email) return;
        try {
            this.loading(true)
            this.$refs.frmThongTinEmail.frmThongTinEmail_Load()
        } catch (e) {
            console.log(e)
        } finally {
            this.$refs.thongtinemail.show()
            this.loading(false)
        }      
    },
    async tsbtnLienHe_Click() {
        if (!this.visiable.lienhe) return;
        try {
            this.loading(true)
            await this.$refs.frmThongTinLienHe.getThongTinLienHe(),
            await this.$refs.frmThongTinLienHe.getThongTinMD_CG(),
            await this.$refs.frmThongTinLienHe.getThongTinMD_DG() 
        } catch (e) {
            console.log(e)
        } finally {
            this.$refs.thongtinlienhe.show()
            this.loading(false)
        }   
    },
    async tsbtnXoaTB_Click() {
        if (!this.visiable.xoaTB) return;
        this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa thuê bao không ?', {
            title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
        }).then(async v => {
            if (v) {
                this.loading(true);
                try {   
                    const params = { 
                        hdtb_id: this.hdtb_id
                    };
                    let response = await api.ur_048_21007062_xoa_hdtb(this.axios, params);
                    if (response && response.data) {
                        if (response.data.error_code === 'BSS-00000000') {
                            this.v5_dtDSThueBao = [];
                            let ds = await this.LAY_DS_HDTB_THAYDOI_TOCDO_TSL(this.hdkh_id);
                            this.HienThiDanhSacHDTB(ds);
                            this.$toast.success("Xóa thuê bao thành công");
                        } else {
                            this.$toast.error("Xóa thuê bao thất bại, lỗi: " + response.data.message);  
                        }           
                    } else {
                        this.$toast.error("Xóa thuê bao thất bại");
                    }                                
                } catch (e) {
                    console.log(e);
                    this.$toast.error("Xảy ra lỗi: " + e);  
                } finally {
                    this.loading(false);
                }  
            }
        });
    },
    async tsbtnGhiLai_Click() {
        if (!this.visiable.ghilai) return;
        try {
            this.loading(true);
            if (!await this.KiemTraDL_KhachHang()) return;
            if (!this.KiemTraDL_ThueBao()) return;

            if (this.visiable.nhapmoi == false) {
                let kt_hopdong_khac = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim(), "0", 
                            Number(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu), this.LoaiHopDong.THAY_DOI_TOCDO_TSL);
                if (!kt_hopdong_khac) return;

                let kt_danhba = await this.KIEMTRA_DB_KHI_THEMMOI(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim(), this.khachhang_id, this.thuebao_id, this.dichvuvt_id);
                if (String(kt_danhba) != "1") {
                    this.$toast.error("Thuê bao không tồn tại trong danh bạ, không cập nhật được ");
                    return;
                }

                let dshg = await this.LayHuongGiao_TiepNhan(this.LoaiHopDong.THAY_DOI_TOCDO_TSL, Number(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh), 
                            Number(this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi), 0, Number(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_kieu_yeu_cau));
                let huonggiao_tn = 0;
                this.quytrinh_id = 0;
                if (dshg == undefined || dshg.huonggiao_id == undefined) {
                    this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
                    return;
                }
                huonggiao_tn = Number(dshg.huonggiao_id);
                this.quytrinh_id = Number(dshg.quytrinh_id);

                await this.TaoDuLieu(true);
                let result = await this.ThemMoi_HopDong(this.dsHDKH, this.dsHDTB, this.dsTDTD_TSL, this.dsTTTT, this.dsCTTHD, this.dsHDTBDV, this.dsKM_HDTB, huonggiao_tn);
                if (result != undefined && result != null && result != "") {
                    this.$toast.error(result);
                    return;
                }

                let ds = await this.LAY_DS_HDTB_THAYDOI_TOCDO_TSL(this.hdkh_id);
                this.HienThiDanhSacHDTB(ds);
            } else {
                let dr = await this.fn_kiemtra_tb_tontai_trong_hdtb(this.hdkh_id, this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim(), this.dichvuvt_id);    
                if (dr.length <= 0) {
                    this.$toast.error("Thuê bao không tồn tại trong hợp đồng, không thể cập nhật. Bạn phải Thêm thuê bao!");
                    return;
                }
                
                let kt_danhba = await this.KIEMTRA_DB_KHI_THEMMOI(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim(), this.khachhang_id, this.thuebao_id, this.dichvuvt_id);
                if (String(kt_danhba) != "1") {
                    this.$toast.error("Thuê bao không tồn tại trong danh bạ, không cập nhật được ");
                    return;
                }
                
                if (this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc != undefined && this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim() != "") {
                    let kt_tb_cung_makh = await this.KIEMTRA_TB_CUNG_MAKH_TB_PL(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich, this.thuebao_id);
                    if (String(kt_tb_cung_makh) != "1") {
                        this.$toast.error("Thuê bao này không cùng mã khách hàng đang thực hiện thay đổi tốc độ ADSL");
                        $("#v3_so_may_acc").focus();
                        return;
                    }
                }

                let dshg = await this.LayHuongGiao_TiepNhan(this.LoaiHopDong.THAY_DOI_TOCDO_TSL, Number(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh), 
                            Number(this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi), 0, Number(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_kieu_yeu_cau));
                let huonggiao_tn = 0;
                this.quytrinh_id = 0;
                if (dshg == undefined || dshg.huonggiao_id == undefined) {
                    this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
                    return;
                }
                huonggiao_tn = Number(dshg.huonggiao_id);
                this.quytrinh_id = Number(dshg.quytrinh_id);

                await this.TaoDuLieu(false);
                let result = await this.CapNhat_HopDong(this.dsHDKH, this.dsHDTB, this.dsTDTD_TSL, this.dsTTTT, this.dsCTTHD, this.dsHDTBDV, this.dsKM_HDTB);
                if (result != undefined && result != null && result != "") {
                    this.$toast.error(result);
                    return;
                }

                await this.Update_huonggiao_tiepnhan(this.hdtb_id, huonggiao_tn);
                let ds = await this.LAY_DS_HDTB_THAYDOI_TOCDO_TSL(this.hdkh_id);
                this.HienThiDanhSacHDTB(ds);
            }
            await this.setButton(3);
            this.$toast.success("Đã cập nhật hợp đồng thành công.");
        } catch (exp) {
            console.log(exp);
            this.$toast.error("Cập nhật hợp đồng thất bại: " + exp);
        } finally {
            this.loading(false);
        }   
    },
    async tsbtnXoa_Click() {
        if (!this.visiable.xoaHD) return;
        this.$confirm('Bạn thật sự muốn xóa hợp đồng không ?', {
            confirmButtonText: 'Có', cancelButtonText: 'Không',
        }).then(async v => {
            if (v) {
                this.loading(true);
                try {                    
                    const params =  { 
                                        list:[
                                            {
                                                hdkh_id: this.hdkh_id,
                                                loaihd_id: this.LoaiHopDong.THAY_DOI_TOCDO_TSL
                                            }
                                        ]
                                    };
                    let response = await api.ur_031_24023011_xoa_hopdong(this.axios, params);
                    if (response && response.data) {
                        if (response.data.error_code === 'BSS-00000000') {                                                      
                            this.v5_dtDSThueBao = [];
                            let ds = await this.LAY_DS_HDTB_THAYDOI_TOCDO_TSL(this.hdkh_id);
                            this.HienThiDanhSacHDTB(ds);
                            await this.setButton(0);
                            this.$toast.success("Xóa hợp đồng thành công");
                        } else {
                            this.$toast.error("Xóa hợp đồng thất bại, lỗi: " + response.data.message);  
                        }           
                    } else {
                        this.$toast.error("Xóa hợp đồng thất bại");
                    }                                
                } catch (e) {
                    console.log(e);
                    this.$toast.error("Xảy ra lỗi: " + e);  
                } finally {
                    this.loading(false);
                }            
            }
        });
    },
    async tsbtnThemTB_Click() {
        if (!this.visiable.themTB) return;
        try {
            this.loading(true);
            let kt_hdtb_khi_themmoi = await this.KIEMTRA_HDTB_KHI_THEMMOI(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim(), this.hdkh_id, this.dichvuvt_id, 0);
            if (String(kt_hdtb_khi_themmoi) == "1") {
                this.$toast.error("Thuê bao đã tồn tại trong hợp đồng, không thêm mới được ");
                return;
            }

            let kt_danhba = await this.KIEMTRA_DB_KHI_THEMMOI(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim(), this.khachhang_id, this.thuebao_id, this.dichvuvt_id);
            if (String(kt_danhba) != "1") {
                this.$toast.error("Thuê bao không tồn tại trong danh bạ, không thêm mới được ");
                return;
            }

            let ds = await this.LAY_DS_HOPDONG_THEO_MA_TB_TSL(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim(), this.LoaiHopDong.THAY_DOI_TOCDO_TSL, 
                    this.TrangThaiHD.MOI, 0, this.dichvuvt_id, "0");
            if (ds.length > 0) {
                this.$toast.error("Không thêm mới thuê bao được vì mã TSL đã tồn tại ");
                return;
            }
            
            let kt_hopdong_khac = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim(), "0", 
                            Number(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu), this.LoaiHopDong.THAY_DOI_TOCDO_TSL);
            if (!kt_hopdong_khac) return;
            
            if (this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc != undefined && this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim() != "") {
                let kt_tb_cung_makh = await this.KIEMTRA_TB_CUNG_MAKH_TB_PL(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich, this.thuebao_id);
                if (String(kt_tb_cung_makh) != "1") {
                    this.$toast.error("Thuê bao này không cùng mã khách hàng đang thực hiện thay đổi tốc độ ADSL");
                    $("#v3_so_may_acc").focus();
                    return;
                }
            }

            if (!this.KiemTraDL_ThueBao()) return;

            let dshg = await this.LayHuongGiao_TiepNhan(this.LoaiHopDong.THAY_DOI_TOCDO_TSL, Number(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh), 
                            Number(this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi), 0, Number(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_kieu_yeu_cau));
            let huonggiao_tn = 0;
            this.quytrinh_id = 0;
            if (dshg == undefined || dshg.huonggiao_id == undefined) {
                this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!");
                return;
            }
            huonggiao_tn = Number(dshg.huonggiao_id);
            this.quytrinh_id = Number(dshg.quytrinh_id);

            await this.TaoDuLieu_HDTB(true);
            await this.TaoDuLieu_HDTBDV(true);
            await this.TaoDuLieu_TDTD_TSL(true);
            this.TaoDuLieu_THONGTIN_TT(true);
            await this.TaoDuLieu_CT_TIENHD(true);
            
            let result = await this.ThemMoi_HopDongTB(this.dsHDTB, this.dsTDTD_TSL, this.dsTTTT, this.dsCTTHD, this.dsHDTBDV, huonggiao_tn);
            if (result != undefined && result != null && result != "") {
                this.$toast.error(result);
                return;
            }

            this.v5_dtDSThueBao = [];
            let dsx = await this.LAY_DS_HDTB_THAYDOI_TOCDO_TSL(this.hdkh_id);
            this.HienThiDanhSacHDTB(dsx);
            this.$toast.success("Thêm mới thuê bao thành công");
        } catch (exp) {
            this.$toast.error(exp);
        } finally {
            this.loading(false);
        }
    },
    async init() {
        try {
            this.loading(true);
            this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_lap_hd = this.ngay_ht;
            this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_yeu_cau = this.ngay_ht;
            $("#v2_so_sv_lct").prop( "disabled", true);
            await this.setButton(-1);

            this.ds_tien_khoanmuc = await this.Lay_tienkhoanmuc_loaihd(this.LoaiHopDong.THAY_DOI_TOCDO_TSL);
            await this.HT_Tocdo_Kenh_Combobox();
            await this.HT_DichVuVT_Combobox_TSL();
            await this.HT_DoiTac_Combobox();
            await this.HT_DS_KhuyenMai_Combobox();
            
            if (this.hdkh_cha_id != undefined && this.hdkh_cha_id != null) {
                await this.LoadTuHopDongTuVan()
            }
        } catch (e) {
            console.log(e);
            this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
        } finally {
            this.loading(false);
        }
    },
    async setButton(kieu) {
      this.visiable.nhapmoi = false;
      this.visiable.ghilai = false;
      this.visiable.xoaHD = false;
      this.visiable.huy = false;
      this.visiable.thanhtoan = false;
      this.visiable.themTB = false;
      this.visiable.xoaTB = false;

      if (kieu == -1) {                    // Bat dau
          $("#v3_so_may_acc").focus();
          this.visiable.ghilai = true;
          this.visiable.huy = true;  
          $('#v1_chb_cong_tac_vien').prop('checked', false);
          $('#v1_chb_nguoi_gioi_thieu').prop('checked', false);
          $("#v1_cong_tac_vien").prop( "disabled", true);
          $("#v1_nguoi_gioi_thieu").prop( "disabled", true);
          this.model.v1_nguoi_gioi_thieu = "";
          this.model.v1_cong_tac_vien = "";
      }

      if (kieu == 0) {                  // Bat dau
          $("#v3_so_may_acc").focus();
          this.visiable.nhapmoi = true;
          await this.clear();
      }

      if (kieu == 1) {                      // Them moi
          this.visiable.ghilai = true;
          this.visiable.huy = true;  
          $('#v1_chb_cong_tac_vien').prop('checked', false);
          $('#v1_chb_nguoi_gioi_thieu').prop('checked', false);
          $("#v1_cong_tac_vien").prop( "disabled", true);
          $("#v1_nguoi_gioi_thieu").prop( "disabled", true);
          this.model.v1_nguoi_gioi_thieu = "";
          this.model.v1_cong_tac_vien = "";
          await this.clear();
      }

      if (kieu == 2) {                           // Huy
          $("#v2_ma_giao_dich").focus();
          this.visiable.nhapmoi = true;
          this.visiable.xoaHD = true;
          await this.clear();
      }

      if (kieu == 3) {                      // Edit
          $("#v2_ma_giao_dich").focus();
          this.visiable.thanhtoan = true;
          this.visiable.nhapmoi = true;
          this.visiable.xoaHD = true;
          this.visiable.ghilai = true;
          this.visiable.huy = true;  
          this.visiable.themTB = true;
          if (this.v5_dtDSThueBao.length > 1) {
            this.visiable.xoaTB = true;
          } else {
            this.visiable.xoaTB = false;
          }
              
          // tsbnInPhieuYC.Enabled = true;         visible = false
      }
    },
    async clear() {
        this.model.v1_ma_du_an = "";
        this.model.v1_nguoi_gioi_thieu = "";
        this.model.v1_cong_tac_vien = "";
        this.nguoigt_id = 0;
        this.ctv_id = 0;

        $('#v2_chb_so_cv_lct').prop('checked', false);
        this.$refs.v2_ThongTinPhieuYeuCau.v2_enable_ngay_cv = false;   
        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_so_sv_lct = "";
        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_hd_ttkd = "";
        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich = "";
        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_hop_dong = "";
        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_lap_hd = this.ngay_ht;
        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_yeu_cau = this.ngay_ht;

        $("#v3_so_may_acc").focus();
        this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc = "";
        this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_thue_bao = "";
        this.$refs.v3_ThongTinThueBao.model.v3_ma_erp = "";
        this.$refs.v3_ThongTinThueBao.model.v3_ma_bss = "";
        this.$refs.v3_ThongTinThueBao.model.v3_so_ao_dau = "";
        this.$refs.v3_ThongTinThueBao.model.v3_so_ao_cuoi = "";
        this.$refs.v3_ThongTinThueBao.model.v3_ten_thue_bao = "";
        this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_dau = "";
        this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_cuoi = "";
        this.$refs.v3_ThongTinThueBao.model.v3_ghi_chu = "";
        if (this.$refs.v3_ThongTinThueBao.v3_LstTocDo) {
          this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi = this.$refs.v3_ThongTinThueBao.v3_LstTocDo[0].ID;
        }

        $('#v4_chb_doi_tac').prop('checked', false);
        this.$refs.v4_ThongTinKhachHang.v4_enable_doi_tac = false;   
        this.$refs.v4_ThongTinKhachHang.model.v4_ma_hop_dong_dt = "";
        this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tc_no_vat = "0"
        this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tk_no_vat = "0"
        this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_ht_no_vat = "0"
        this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tbi_no_vat = "0"
        this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tkdt = "0"
        this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tcdt = "0"     
        this.$refs.v4_ThongTinKhachHang.model.v4_ma_khach_hang = ""; 
        this.$refs.v4_ThongTinKhachHang.model.v4_ten_khach_hang = ""; 
        this.$refs.v4_ThongTinKhachHang.model.v4_dia_chi_khach_hang = "";

        this.phuongld_id = 0;
        this.phold_id = 0;
        this.apld_id = 0;
        this.khuld_id = 0;
        this.hdkh_id = 0;
        this.khachhang_id = 0;
        this.trangbi_id = 3;
        this.hdtb_id = 0;
        this.thuebao_id = 0;
        this.diachi_tb = "";   
        this.dtienld_tsl = 0;
        this.dvatld_tsl = 0;
        this.doituong_id = 1;
        // txtDiaChiLD.Text = "";     visible = false

        this.v5_dtDSThueBao = [];
        await this.LayTienTheoKhoanMuc();      
    },
    async HT_Tocdo_Kenh_Combobox() {
        console.log(">>>HT_Tocdo_Kenh_Combobox")
        const params = {};
        let response = await api.ur_002_lay_tocdo_kenh(this.axios, params);
        
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.$refs.v3_ThongTinThueBao.v3_LstTocDo = response.data.data;
            if (this.$refs.v3_ThongTinThueBao.v3_LstTocDo.length > 0) {
                this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi = this.$refs.v3_ThongTinThueBao.v3_LstTocDo[0].ID;
                this.$refs.v3_ThongTinThueBao.model.v3_toc_do_cu = this.$refs.v3_ThongTinThueBao.v3_LstTocDo[0].ID;
            }
        }
    },
    async HT_DichVuVT_Combobox_TSL() {
        console.log(">>>HT_DichVuVT_Combobox_TSL")
        const params = {};
        let response = await api.ur_003_dichvu_vienthong(this.axios, params);
        
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.$refs.v2_ThongTinPhieuYeuCau.v2_LstDichVu = response.data.data;
            if (this.$refs.v2_ThongTinPhieuYeuCau.v2_LstDichVu.length > 0) {
                let dvtsl = this.$refs.v2_ThongTinPhieuYeuCau.v2_LstDichVu.find((item) => item.ID == "9")
                let dvtslnb = this.$refs.v2_ThongTinPhieuYeuCau.v2_LstDichVu.find((item) => item.ID == "25")        
                this.$refs.v2_ThongTinPhieuYeuCau.v2_LstDichVu = []
                this.$refs.v2_ThongTinPhieuYeuCau.v2_LstDichVu.push(dvtsl)
                if (dvtslnb != undefined) {
                    this.$refs.v2_ThongTinPhieuYeuCau.v2_LstDichVu.push(dvtslnb)
                }
                this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu = "9";  // Kenh thue rieng
            }
        }
    },
    async HT_DoiTac_Combobox() {
        console.log(">>>HT_DoiTac_Combobox")
        const params = {};
        let response = await api.ur_004_danhsach_doitac(this.axios, params);
        
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.$refs.v4_ThongTinKhachHang.v4_LstDoiTac = response.data.data;
            if (this.$refs.v4_ThongTinKhachHang.v4_LstDoiTac.length > 0) {
                this.$refs.v4_ThongTinKhachHang.model.v4_doi_tac = this.$refs.v4_ThongTinKhachHang.v4_LstDoiTac[0].ID;
            }
        }
    },
    async HT_DS_KhuyenMai_Combobox() {
        console.log(">>>HT_DS_KhuyenMai_Combobox")
        let itocdo_id = 0;
        let itrangbi_id = 0; // Default: KH tự trang bị
        let imuccuoc_id = 0;
        let iloaitb_id = 0;
        if (this.$refs.v2_ThongTinPhieuYeuCau.v2_LstLoaiHinh.length <= 0) {
            return;
        }
            
        if (this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu != undefined && this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu != null 
                  && !String(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu).includes(".")) {
            this.dichvuvt_id = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu;
        }

        if (this.dichvuvt_id == this.DichVuVienThong.MEGAWAN || this.dichvuvt_id == 26) {
          if (this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi == undefined || this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi == null 
                  || String(this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi).includes(".")) {
              return;
          }
          itocdo_id = this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi;
        } else {
            itocdo_id = 0;
        }

        if (this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh != undefined && this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh != null 
                  && !String(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh).includes(".")) {
            iloaitb_id = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh;
        }       

        const params = {
            vloaitb_id: iloaitb_id,
            vkieuld_id: this.$refs.v2_ThongTinPhieuYeuCau.model.v2_kieu_yeu_cau,
            vphuongld_id: this.phuongld_id,
            vkhoanmuctt_id: this.KHOANMUC_TT.KMTT_DATMOI,
            vphold_id: this.phold_id,
            vapld_id: this.apld_id,
            vkhuld_id: this.khuld_id,
            vtrangbi_id: itrangbi_id,
            vdoituong_id: this.doituong_id,
            vtocdo_id: itocdo_id,
            vmuccuoc_id: imuccuoc_id,
            vloaikm: 0
        };
        let response = await api.ur_005_lay_khuyenmai_hdtb(this.axios, params);
        
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.$refs.v3_ThongTinThueBao.v3_LstKhuyenMai = response.data.data;
        }

        if (this.$refs.v3_ThongTinThueBao.v3_LstKhuyenMai.length == 0) {
            $("#v3_chb_khuyen_mai").attr("disabled", true);
        } else {
            this.$refs.v3_ThongTinThueBao.model.v3_khuyen_mai = this.$refs.v3_ThongTinThueBao.v3_LstKhuyenMai[0].khuyenmai_id;
            $("#v3_chb_khuyen_mai").removeAttr("disabled");
        }
        $('#v3_chb_khuyen_mai').prop('checked', false);      
    },
    async Lay_tienkhoanmuc_loaihd(loaihd_id) {
        let result = [];
        try {
          const params = { loaihd_id: loaihd_id };
          let response = await api.ur_001_lay_tienkhoanmuc_loaihd(this.axios, params);
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
              result = response.data.data;
          }
        } catch (e) {
            console.log(e);
        }
        return result;
    },
    async LayTienTheoKhoanMuc() {
        console.log(">>>LayTienTheoKhoanMuc")
        try {
            let ds = [];
            const params = {
                loaihd_id: this.LoaiHopDong.THAY_DOI_TOCDO_TSL,
                ngay_yc: this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_yeu_cau,
                dichvuvt_id: this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu,
                loaitb_id: this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh,
                kieuld_id: this.$refs.v2_ThongTinPhieuYeuCau.model.v2_kieu_yeu_cau,
                phuongld_id: this.phuongld_id,
                tocdo_id: this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi,
                muccuoc_id: 0,
                ltb_adsl_id: 1,
                ltb_adsl_moi_id: 0,
                ltb_adsl_cu_id: 0,
                tocdo_moi_id: 0,
                tocdo_cu_id: 0,
                kh_cd: 0,
                phold_id: this.phold_id,
                apld_id: this.apld_id,
                khuld_id: this.khuld_id,
                doituong_id: this.doituong_id,
                trangbi_id: this.trangbi_id
            };

            let response = await api.ur_011_lay_tien_khoanmuc_ld(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                ds = response.data.data;
            }

            if (ds.length > 0) {
                this.HienThiTienLapDat(ds);
                this.LayTongTien_HDTB();
            }
        } catch (ex) {
            this.$toast.warning(ex);
        }
    },
    HienThiTienLapDat(ds) {
        console.log(">>>HienThiTienLapDat")
        let kt_lm = true;
        if (ds[0].id != "-1") {
            for (let i = 0; i < ds.length; i++) {
                if (ds[i].khoanmuctt_id == String(this.KHOANMUC_TT.KMTT_DOITOCDO)) {
                    this.dtienld_tsl = ds[0].tien;
                    this.dvatld_tsl = ds[0].vat;
                    kt_lm = false;
                }
            }

            if (kt_lm) {
                this.dtienld_tsl = 0;
                this.dvatld_tsl = 0;
            }
        } else {
            this.dtienld_tsl = 0;
            this.dvatld_tsl = 0;
            this.$toast.error("Thông tin giá chưa được cập nhật");
        }

        for (let j = 0; j < this.ds_tien_khoanmuc; j++) {
            if (this.ds_tien_khoanmuc[j].khoanmuctt_id == String(this.KHOANMUC_TT.KMTT_DOITOCDO)) {
                this.ds_tien_khoanmuc[j].tien = this.dtienld_tsl;
                this.ds_tien_khoanmuc[j].vat = this.dvatld_tsl;
            }
        }
    },
    LayTongTien_HDTB() {
        console.log(">>>LayTongTien_HDTB")
        try {
            let dtongtien = 0;
            let dtongvat = 0;
            for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
                dtongtien += Number(this.ds_tien_khoanmuc[i].tien);
                dtongvat += Number(this.ds_tien_khoanmuc[i].vat);
            }
            this.$refs.v4_ThongTinKhachHang.model.v4_tien = dtongtien;
            this.$refs.v4_ThongTinKhachHang.model.v4_vat = dtongvat;
            this.$refs.v4_ThongTinKhachHang.model.v4_tong_tien = dtongtien + dtongvat;
            this.model.v1_tong_tien = dtongtien + dtongvat;
        } catch (ex) {
            this.$toast.error(ex);
        }

    },
    async LAY_DS_DIACHI_THEO_DBTBID(thuebao_id) {
        let result = [];
        try {
          const params = { thuebao_id: thuebao_id };
          let response = await api.ur_008_lay_ds_diachi_theo_dbtbid(this.axios, params);
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
              result = response.data.data;
          }
        } catch (e) {
            console.log(e);
        }
        return result;
    },
    async HT_LoaiHinh_TB_Combobox(dichvuvt_id) {
        console.log(">>>HT_LoaiHinh_TB_Combobox")
        try {
          const params = { dvvt_id: dichvuvt_id };
          let response = await api.ur_009_loaihinh_thuebao(this.axios, params);
          
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
              this.$refs.v2_ThongTinPhieuYeuCau.v2_LstLoaiHinh = response.data.data;
              if (this.$refs.v2_ThongTinPhieuYeuCau.v2_LstLoaiHinh.length > 0) {
                  this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh = this.$refs.v2_ThongTinPhieuYeuCau.v2_LstLoaiHinh[0].ID;
                  for (let lh of this.$refs.v2_ThongTinPhieuYeuCau.v2_LstLoaiHinh) {
                      if (lh.ID == String(this.LoaiHinhTB.LEASEDLINE)) {
                          this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh = String(this.LoaiHinhTB.LEASEDLINE);
                          break;
                      }
                  }         
              }
          }
        } catch (e) {
            console.log(e);
        }
    },
    async HT_Kieu_LD_Combobox(loaitb_id, loaihd_id) {
        console.log(">>>HT_Kieu_LD_Combobox " + loaitb_id + ", " + loaihd_id)
        try {
            if (loaitb_id == 0) {
                const params = { };
                let response = await api.ur_012_lay_ds_kieu_ld(this.axios, params);
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.$refs.v2_ThongTinPhieuYeuCau.v2_LstKieuYeuCau = response.data.data;
                    if (this.$refs.v2_ThongTinPhieuYeuCau.v2_LstKieuYeuCau.length > 0) {
                        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_kieu_yeu_cau = this.$refs.v2_ThongTinPhieuYeuCau.v2_LstKieuYeuCau[0].ID;
                    }
                }
            } else {
                const params = {loaihd_id: loaihd_id, loaitb_id: loaitb_id };    
                let response = await api.ur_012_lay_ds_kieu_ld_by_loaitb_loaihd(this.axios, params);
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.$refs.v2_ThongTinPhieuYeuCau.v2_LstKieuYeuCau = response.data.data;
                    if (this.$refs.v2_ThongTinPhieuYeuCau.v2_LstKieuYeuCau.length > 0) {
                        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_kieu_yeu_cau = this.$refs.v2_ThongTinPhieuYeuCau.v2_LstKieuYeuCau[0].kieuld_id;
                    }
                }
            }
        } catch (e) {
            console.log(e);
        }
    },
    async cboDichVuVT_SelectedValueChanged(value) {
        this.dichvuvt_id = value;
        await this.HT_LoaiHinh_TB_Combobox(this.dichvuvt_id);
        await this.HT_DS_KhuyenMai_Combobox();    
    },
    async cboLoaihinhTB_SelectedValueChanged() {
        await this.HT_Kieu_LD_Combobox(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh, this.LoaiHopDong.THAY_DOI_TOCDO_TSL);
        await this.HT_DS_KhuyenMai_Combobox();
    },
    async cboKieuLD_SelectedValueChanged() {
        await this.LayTienTheoKhoanMuc();
        await this.HT_DS_KhuyenMai_Combobox();
    },
    chkCTV_CheckedChanged() {
        var checkBox = document.getElementById("v1_chb_cong_tac_vien");
        if (checkBox.checked) {
            this.$refs.v1_dlg_congtacvien.show()
            $("#v1_cong_tac_vien").prop( "disabled", false);
        } else {
            this.$refs.v1_dlg_congtacvien.hide()
            $("#v1_cong_tac_vien").prop( "disabled", true);
        }
    },
    acceptChangeCTV(value) {
      console.log(value)
      this.model.v1_cong_tac_vien = value.ten_ht;
      this.ctv_id = value.nhanvien_id;
      this.$refs.v1_dlg_congtacvien.hide()
      // this.loainv_ctv_id = f.loainv_id;
      // this.donvi_ctv_id = f.donvi_id;
    },
    PopUpCongTacVien_Thoat() {
        this.$refs.v1_dlg_congtacvien.hide()
    },
    chkNGT_CheckedChanged() {
        var checkBox = document.getElementById("v1_chb_nguoi_gioi_thieu");
        if (checkBox.checked) {
            this.$refs.v1_dlg_nguoigioithieu.show()
            $("#v1_nguoi_gioi_thieu").prop( "disabled", false);
        } else {
            this.$refs.v1_dlg_nguoigioithieu.hide()
            $("#v1_nguoi_gioi_thieu").prop( "disabled", true);
        }
    },
    acceptChangeNGT(value) {
        console.log(value)
        this.model.v1_nguoi_gioi_thieu = value.ten_ht;
        this.nguoigt_id = value.nhanvien_id;
        // this.loainv_nguoi_gt_id = f.loainv_id;
        // this.donvi_nguoi_gt_id = f.donvi_id;
    },
    PopUpNguoiGioiThieu_Thoat() {
        this.$refs.v1_dlg_nguoigioithieu.hide()
    },
    chkCV_CheckedChanged() {
        var checkBox = document.getElementById("v2_chb_so_cv_lct");
        if (checkBox.checked) {
            $("#v2_so_sv_lct").prop( "disabled", false);
            this.$refs.v2_ThongTinPhieuYeuCau.v2_enable_ngay_cv = true;     
        } else {
            $("#v2_so_sv_lct").prop( "disabled", true);
            this.$refs.v2_ThongTinPhieuYeuCau.v2_enable_ngay_cv = false; 
            this.$refs.v2_ThongTinPhieuYeuCau.model.v2_so_sv_lct = "";
            this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_cv = this.ngay_ht;
        }
    },
    chkKhuyenMai_CheckedChanged() {
        var checkBox = document.getElementById("v3_chb_khuyen_mai");
        this.$refs.v3_ThongTinThueBao.v3_enable_khuyen_mai = checkBox.checked;
        this.dtienkmld = 0;
        this.dvatkmld = 0;
        this.tyle_kmld = 0;
        this.tong_kmld = 0;
        this.dsKM_HDTB = [];
    },
    chkDoiTac_CheckedChanged() {
        var chkDoiTac = document.getElementById("v4_chb_doi_tac");
        if (chkDoiTac.checked) {
            this.$refs.v4_ThongTinKhachHang.v4_enable_doi_tac = true;
        } else {
            this.$refs.v4_ThongTinKhachHang.v4_enable_doi_tac = false;
            this.$refs.v4_ThongTinKhachHang.model.v4_ma_hop_dong_dt = "";
            this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tkdt = "0";
            this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tcdt = "0";
        }
    },
    async LAY_DS_HOPDONG_THEO_MA_TB_TSL(ma_tb, loaihd_id, tthd_id, donvi_dl_id, dichvuvt_id, ma_gd) {
        let result = [];
        try {
            const params = { 
                ma_tb: ma_tb,
                loaihd_id: loaihd_id,
                tthd_id: tthd_id,
                donvi_dl_id: donvi_dl_id,
                dichvuvt_id: dichvuvt_id,
                ma_gd: ma_gd
            };
            let response = await api.ur_006_lay_ds_hopdong_theo_ma_tb_tsl(this.axios, params);
            console.log(response)
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;
            }
        } catch(e) {
            console.log(e);
        }
        return result;
    },
    async LAY_DS_HDTB_THAYDOI_TOCDO_TSL(hdkh_id) {
        let result = [];
        try {
            const params = { 
                hdkh_id: hdkh_id
            };
            let response = await api.ur_022_lay_ds_hdtb_thaydoi_tocdo_tsl(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;
            }
        } catch(e) {
            console.log(e);
        }
        return result;
    },
    async HienThiDanhSacHDTB(dataSource) {
        console.log(">>>HienThiDanhSacHDTB")
        if (dataSource.length > 0) {
            this.v5_dtDSThueBao = dataSource
            await this.setButton(3);
        } else {
            this.clear();
            this.v5_dtDSThueBao = []
        }
    },
    async LAY_DS_DB_TSL_THEO_TBID(thuebao_id) {
        let result = [];
        try {
            const params = { 
                params :{"thueBaoId": thuebao_id}
            };
            let response = await api.ur_010_21030010_lay_ds_danhba_tsl(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;
            }
        } catch(e) {
            console.log(e);
        }
        return result;
    },
    async LAY_DS_HDTB_TSL_THEO_HDTB_ID(hdtb_id) {
        let result = [];
        try {
            const params = { 
                hdtb_id: hdtb_id
            };
            let response = await api.ur_016_lay_ds_hdtb_tsl_theo_hdtb_id(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;
            }
        } catch(e) {
            console.log(e);
        }
        return result;
    },
    HienThiTTDiaChi(ds, kieu) {
        if (ds.length > 0) {
            if (kieu == 3) {
                this.apld_id = 0;
                this.khuld_id = 0;
                this.phold_id = 0;
                this.phuongld_id = ds[0].phuongld_id;
                if (ds[0].phold_id != undefined && ds[0].phold_id != "") {
                    this.phold_id = ds[0].phold_id;
                }
                if (ds[0].apld_id != undefined && ds[0].apld_id != "") {
                    this.apld_id = ds[0].apld_id;
                }
                if (ds[0].khuld_id != undefined && ds[0].khuld_id != "") {
                    this.khuld_id = ds[0].khuld_id;
                }
            }
        }
    },
    async LAY_CHITIET_KM_THEO_HDTB_ID(hdtb_id, loai_km) {
        let result = [];
        try {
            const params = { 
                hdtb_id: hdtb_id,
                loai_km: loai_km
            };
            let response = await api.ur_017_lay_chitiet_km_theo_hdtb_id(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;
            }
        } catch(e) {
            console.log(e);
        }
        return result;
    },
    async LAY_TIEN_HD_KM_THEO_HDTB(hdtb_id) {
        let result = [];
        try {
            const params = { 
                hdtb_id: hdtb_id
            };
            let response = await api.ur_018_lay_tien_hd_km_theo_hdtb(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;
            }
        } catch(e) {
            console.log(e);
        }
        return result;
    },
    async grid_FocusedRowChanged(data) {
      console.log(">>>grid_FocusedRowChanged")
      if (data) {
        let row = data.data;
        this.loading(true);
        try {            
            this.loaikenh_id = row.loaikenh_id;
            if (row.hdkh_id != undefined && row.hdkh_id != "") {
                this.hdkh_id = row.hdkh_id;
            }        
            this.hdtb_id = row.hdtb_id;
            if (row.thuebao_id != undefined && row.thuebao_id != "") {
                this.thuebao_id = row.thuebao_id;
            }

            this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu = row.dichvuvt_id;
            this.$refs.v2_ThongTinPhieuYeuCau.model.v2_kieu_yeu_cau = row.kieuld_id;

            if (row.ma_hd != undefined && row.ma_hd != "") {
                this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_hop_dong = row.ma_hd;
            }
            this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich = row.ma_gd;

            this.diachi_tb = row.diachi_tb;
            this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc = row.ma_tb;
            this.$refs.v3_ThongTinThueBao.model.v3_ten_thue_bao = row.ten_tb;
            this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_thue_bao = row.diachi_tb;
            this.$refs.v3_ThongTinThueBao.model.v3_ghi_chu = row.ghichu;
            // txtDiaChiLD.Text = myDataTables.Rows[nIndex]["diachi_ld"].ToString();                // visible = false
           
            this.$refs.v4_ThongTinKhachHang.model.v4_ma_khach_hang = row.ma_kh;
            this.$refs.v4_ThongTinKhachHang.model.v4_ten_khach_hang = row.ten_kh;
            this.$refs.v4_ThongTinKhachHang.model.v4_dia_chi_khach_hang = row.diachi_kh;

            this.loaitb_id = row.loaitb_id;
            this.doituong_id = row.doituong_id;
      
            if (row.doitac_id != undefined && row.doitac_id != "") {
                this.$refs.v4_ThongTinKhachHang.model.v4_doi_tac = row.doitac_id;
                this.$refs.v4_ThongTinKhachHang.model.v4_ma_hop_dong_dt = row.mahd_dt;
                this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tkdt = this.formatNumber(row.cuoc_tkdt);
                this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tcdt = this.formatNumber(row.cuoc_tcdt);
                $('#v4_chb_doi_tac').prop('checked', true);
                this.$refs.v4_ThongTinKhachHang.v4_enable_doi_tac = true;
            } else {
                this.$refs.v4_ThongTinKhachHang.model.v4_ma_hop_dong_dt = "";
                this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tkdt = "0";
                this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tcdt = "0";
                $('#v4_chb_doi_tac').prop('checked', false);
                this.$refs.v4_ThongTinKhachHang.v4_enable_doi_tac = false;
            }

            if (row.ma_cv != undefined && row.ma_cv != "") {
                $('#v2_chb_so_cv_lct').prop('checked', true);
                this.$refs.v2_ThongTinPhieuYeuCau.model.v2_so_sv_lct = row.ma_cv;
                this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_cv = row.ngay_cv;
                this.$refs.v2_ThongTinPhieuYeuCau.v2_enable_ngay_cv = true;
            } else {
                $('#v2_chb_so_cv_lct').prop('checked', false);
                this.$refs.v2_ThongTinPhieuYeuCau.model.v2_so_sv_lct = "";
                this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_cv = this.ngay_ht;
                this.$refs.v2_ThongTinPhieuYeuCau.v2_enable_ngay_cv = false;
            }

            this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tk_no_vat = this.formatNumber(row.cuoc_tk);
            this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tc_no_vat = this.formatNumber(row.cuoc_tc);
            this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_ht_no_vat = this.formatNumber(row.cuoc_ht);
            this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tbi_no_vat = this.formatNumber(row.cuoc_tbi);
            this.model.v1_tong_tien = row.tongtien;

            if (row.tocdo_cu != undefined && row.tocdo_cu != "") {
                this.$refs.v3_ThongTinThueBao.model.v3_toc_do_cu = row.tocdo_id_cu;
            }
            if (row.tocdo_moi != undefined && row.tocdo_moi != "") {
                this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi = row.tocdo_id_moi;
            }

            let ds1 = await this.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id);
            if (ds1.length > 0) {
                if (Number(ds1[0].daucuoi_id) == 1) {
                    this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_dau = ds1[0].diachi;
                    this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_cuoi = ds1[1].diachi;
                    this.$refs.v3_ThongTinThueBao.model.v3_so_ao_dau = ds1[0].ma_lt;
                    this.$refs.v3_ThongTinThueBao.model.v3_so_ao_cuoi = ds1[1].ma_lt;
                }
                if (Number(ds1[0].daucuoi_id) == 2) {
                    this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_dau = ds1[1].diachi;
                    this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_cuoi = ds1[0].diachi;
                    this.$refs.v3_ThongTinThueBao.model.v3_so_ao_dau = ds1[1].ma_lt;
                    this.$refs.v3_ThongTinThueBao.model.v3_so_ao_cuoi = ds1[0].ma_lt;
                }
            }

            let dsHDTB = await this.LAY_DS_HDTB_TSL_THEO_HDTB_ID(this.hdtb_id);
            if (dsHDTB.length > 0) {
                this.$refs.v3_ThongTinThueBao.model.v3_ma_bss = dsHDTB[0].ma_bss;
                this.$refs.v3_ThongTinThueBao.model.v3_ma_erp = dsHDTB[0].id_erp;
            }

            let dsTTDC = await this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao_id);
            this.HienThiTTDiaChi(dsTTDC, 3);

            await this.HT_DS_KhuyenMai_Combobox();
            let dsTemp = await this.LAY_CHITIET_KM_THEO_HDTB_ID(this.hdtb_id, 0);
            if (dsTemp.length > 0) {
                if (dsTemp[0].khuyenmai_id != undefined && dsTemp[0].khuyenmai_id != "") {
                    this.$refs.v3_ThongTinThueBao.model.v3_khuyen_mai = dsTemp[0].khuyenmai_id;
                    $('#v3_chb_khuyen_mai').prop('checked', true);
                    this.$refs.v3_ThongTinThueBao.v3_enable_khuyen_mai = true;
                } else {
                    $('#v3_chb_khuyen_mai').prop('checked', false);
                    this.$refs.v3_ThongTinThueBao.v3_enable_khuyen_mai = false;
                }

                let ithang_huongdc = 0, ithang_huongkm = 0, ithang_tg = 0;
                let dtiendatcoc_csd = 0, dtien_td = 0;
                this.dsKM_HDTB = [];

                for (let i = 0; i < dsTemp.length; i++) {
                    ithang_huongdc = 0; ithang_huongkm = 0; ithang_tg = 0;
                    dtiendatcoc_csd = 0; dtien_td = 0;
                    let dr = {};
                    try { ithang_huongdc = Number(dsTemp[i].thang_huongdc) } catch (e) {};
                    try { ithang_huongkm = Number(dsTemp[i].thang_huongkm) } catch (e) {};
                    try { ithang_tg = Number(dsTemp[i].sothang_tg) } catch (e) {};
                    try { dtiendatcoc_csd = Number(dsTemp[i].datcoc_csd) } catch (e) {};
                    try { dtien_td = Number(dsTemp[i].tien_td) } catch (e) {};
                    dr.THANG_HUONGDC = ithang_huongdc;
                    dr.THANG_HUONGKM = ithang_huongkm;
                    dr.SOTHANG_TG = ithang_tg;
                    dr.DATCOC_CSD = dtiendatcoc_csd;
                    dr.TIEN_TD = dtien_td;

                    if (String(dsTemp[i].kieu_bdkm) == "3") {
                        if (moment(String(dsTemp[i].thang_bdkm), 'yyyyMM', true).isValid()) {
                            dr.THANG_BD = Number(dsTemp[i].thang_bdkm);
                            let date = moment(String(dsTemp[i].thang_bdkm), "YYYYMM").toDate();
                            let newDate = new Date(date.setMonth(date.getMonth() + ithang_huongkm));
                            dr.THANG_KT = Number(moment(newDate).format('yyyyMM'));
                        }
                    }

                    if (String(dsTemp[i].kieu_bdtg) == "3") {
                        if (moment(String(dsTemp[i].thang_bdtg), 'yyyyMM', true).isValid()) {
                            dr.THANG_BDTG = Number(dsTemp[i].thang_bdtg);
                            let date = moment(String(dsTemp[i].thang_bdtg), "YYYYMM").toDate();
                            let newDate = new Date(date.setMonth(date.getMonth() + ithang_tg));
                            dr.THANG_KTTG = Number(moment(newDate).format('yyyyMM'));
                        }
                    }

                    if (String(dsTemp[i].kieu_bddc) == "3") {
                        if (moment(String(dsTemp[i].thang_bddc), 'yyyyMM', true).isValid()) {
                            dr.THANG_BDDC = Number(dsTemp[i].thang_bddc);
                            let date = moment(String(dsTemp[i].thang_bddc), "YYYYMM").toDate();
                            let newDate = new Date(date.setMonth(date.getMonth() + ithang_huongdc));
                            dr.THANG_KTDC = Number(moment(newDate).format('yyyyMM'));
                        }
                    }

                    dr.TYLE_KMLD = Number(dsTemp[i].tyle_kmld);
                    dr.TYLE_SD = Number(dsTemp[i].tyle_sd);
                    dr.TYLE_TB = Number(dsTemp[i].tyle_tb);
                    dr.HDTB_ID = this.hdtb_id;
                    dr.CHITIETKM_ID = Number(dsTemp[i].chitietkm_id);
                    dr.KHUYENMAI_ID = Number(dsTemp[i].khuyenmai_id);
                    dr.KIEU_YC = Number(dsTemp[i].kieu_yc);
                    this.dsKM_HDTB.push(dr);
                }
            } else {
                $('#v3_chb_khuyen_mai').prop('checked', false);
                this.$refs.v3_ThongTinThueBao.v3_enable_khuyen_mai = false;
                this.dsKM_HDTB = [];
            }

            this.dtCTKM = await this.LAY_TIEN_HD_KM_THEO_HDTB(this.hdtb_id);
            for (let i = 0; i < this.dtCTKM.length; i++) {
                if (this.dtCTKM[i].khoanmuctt_id == String(this.KHOANMUC_TT.KMTT_DOITOCDO)) {
                    this.dtienld_tsl =  this.dtCTKM[i].tien;
                    this.dvatld_tsl =  this.dtCTKM[i].vat;
                }
                if (this.dtCTKM[i].khoanmuctt_id == String(this.KHOANMUC_TT.KMTT_KM_LAPDAT)) {
                    this.dtienkmld = this.dtCTKM[i].tien;
                    this.dvatkmld = this.dtCTKM[i].vat;
                }
            }           
        } catch (ex) {
            this.$toast.error(ex);
        }
        this.loading(false);
      }
    },
    async KiemTraDL_KhachHang() {
        let kq_bc = await this.kiemtra_hd_bancheo_tc(this.thuebao_id, this.LoaiHopDong.THAY_DOI_TOCDO_TSL);
        if (kq_bc != "OK") {
            this.$toast.error(kq_bc);
            return false;
        }

        if (this.$refs.v4_ThongTinKhachHang.model.v4_ten_khach_hang.trim() == "") {
            this.$toast.error("Hãy nhập tên khách hàng !");
            $("#v4_ten_khach_hang").focus();
            return false;
        }

        if (this.$refs.v4_ThongTinKhachHang.model.v4_ma_khach_hang.trim() == "" && this.khachhang_id != 0) {
            this.$toast.error("Kiểm tra lại mã khách hàng !");
            $("#v4_ma_khach_hang").focus();
            return false;
        }
        
        if (this.$refs.v4_ThongTinKhachHang.model.v4_ma_khach_hang.trim() != "") {
            let kt_makh = await this.Kiemtra_makh_mtt(this.$refs.v4_ThongTinKhachHang.model.v4_ma_khach_hang.trim(), 1, this.LoaiHopDong.THAY_DOI_TOCDO_TSL);
            if (kt_makh == 0) {
                this.$toast.error("Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
                $("#v4_ma_khach_hang").focus();
                return false;
            }
        }

        if (this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich.trim() != "") {
            let kt_makh = await this.Kiemtra_makh_mtt(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich.trim(), 3, this.LoaiHopDong.THAY_DOI_TOCDO_TSL);
            if (kt_makh == 0) {
                this.$toast.error("Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
                $("#v2_ma_giao_dich").focus();
                return false;
            }
        }

        let chkCTV = document.getElementById("v1_chb_cong_tac_vien");
        if (chkCTV.checked && (this.model.v1_cong_tac_vien == undefined || this.model.v1_cong_tac_vien.trim() == "") && this.ctv_id == 0) {
            this.$toast.error("Bạn chưa chọn cộng tác viên !");
            $("#v1_cong_tac_vien").focus();
            return false;
        }

        let chkNguoiGT = document.getElementById("v1_chb_nguoi_gioi_thieu");
        if (chkNguoiGT.checked && (this.model.v1_nguoi_gioi_thieu == undefined || this.model.v1_nguoi_gioi_thieu.trim() == "") && this.nguoigt_id == 0) {
            this.$toast.error("Bạn chưa chọn người giới thiệu hợp đồng !");
            $("#v1_nguoi_gioi_thieu").focus();
            return false;
        }

        return true;
    },
    async kiemtra_hd_bancheo_tc(vthuebao_id, vloaihd_id) {
        let result;
        try {
            const params = { 
                thuebao_id: vthuebao_id,
                loaihd_id: vloaihd_id
            };
            let response = await api.ur_019_21008054_kiemtra_hd_bancheo_tc(this.axios, params);
            
            if (response && response.data) {
                if (response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                } else {
                    result = response.data.message;
                }      
            }
        } catch(e) {
            console.log(e);
            result = e.message;
        }
        return result;
    },
    async Kiemtra_makh_mtt(ma_kh, kieu, vloaihd_id) {
        let result = "";
        try {
            const params = { 
                ma_kh: ma_kh,
                kieu: kieu,
                loaihd_id: vloaihd_id
            };
            let response = await api.ur_020_kiemtra_makh_mtt(this.axios, params);
            
            if (response && response.data) {
                if (response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                } else {
                    result = response.data.message;
                }      
            }
        } catch(e) {
            console.log(e);
            result = e.message;
        }
        return result;
    },
    KiemTraDL_ThueBao() {        
        if (this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_hop_dong.trim() == "" && this.khachhang_id != 0) {
            this.$toast.error("Hãy nhập mã hợp đồng !");
            $("#v2_ma_hop_dong").focus();
            return false;
        }

        if (this.$refs.v3_ThongTinThueBao.model.v3_ten_thue_bao.trim() == "") {
            this.$toast.error("Hãy nhập tên thuê bao !");
            $("#v3_ten_thue_bao").focus();
            return false;
        }

        if (String(this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi) == String(this.$refs.v3_ThongTinThueBao.model.v3_toc_do_cu)
                && this.$refs.v2_ThongTinPhieuYeuCau.model.v2_kieu_yeu_cau != "13271") {
            this.$toast.error("Tốc độ mới phải khác tốc độ cũ!");
            return false;
        }

        if (this.$refs.v3_ThongTinThueBao.model.v3_ghi_chu != undefined && this.$refs.v3_ThongTinThueBao.model.v3_ghi_chu != null &&
                this.$refs.v3_ThongTinThueBao.model.v3_ghi_chu.length > 1500) {
            this.$toast.error("Độ dài trường ghi chú không được vượt quá 1500 ký tự!");
            return false;
        }

        return true;
    },
    async KIEMTRA_THOIHAN_KM(khuyenmai_id, ngay_yc) {
        let result;
        try {
            const params = { 
                khuyenmai_id: khuyenmai_id,
                ngay_yc: ngay_yc
            };
            let response = await api.ur_023_kiemtra_thoihan_km(this.axios, params);
            
            if (response && response.data) {
                if (response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                } else {
                    result = response.data.message;
                }      
            }
        } catch(e) {
            console.log(e);
            result = e.message;
        }
        return result;
    },
    HienThiTienKMLapDat() {
        console.log(">>>HienThiTienKMLapDat")
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
            if (String(this.ds_tien_khoanmuc[i].khoanmuctt_id).trim() == String(this.KHOANMUC_TT.KMTT_KM_LAPDAT)) {
                this.ds_tien_khoanmuc[i].tien = Math.abs(this.dtienkmld) * -1;
                this.ds_tien_khoanmuc[i].vat = Math.abs(this.dvatkmld) * -1;
            }
        }
        this.LayTongTien_HDTB();
    },
    LayTongTien_HDTB() {
        console.log(">>>LayTongTien_HDTB")
        try {
            let dtongtien = 0;
            let dtongvat = 0;
            for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
                dtongtien += Number(this.ds_tien_khoanmuc[i].tien);
                dtongvat += Number(this.ds_tien_khoanmuc[i].vat);
            }

            this.$refs.v4_ThongTinKhachHang.model.v4_tien = this.formatNumber(dtongtien);
            this.$refs.v4_ThongTinKhachHang.model.v4_vat = this.formatNumber(dtongvat);
            this.$refs.v4_ThongTinKhachHang.model.v4_tong_tien = this.formatNumber(dtongtien + dtongvat);
            this.model.v1_tong_tien = this.formatNumber(dtongtien + dtongvat);
        } catch (ex) {
            this.$toast.error(ex.message);
        }
    },
    async btnChiTietKM_Click() {
        try {
            let chkKhuyenMai = document.getElementById("v3_chb_khuyen_mai");
            if (chkKhuyenMai.checked) {
                if (this.$refs.v3_ThongTinThueBao.v3_LstKhuyenMai.length > 0) {
                    let vmuccuoctn_id = 0;
                    let vtocdotn_id = 0;
                    if (this.dichvuvt_id == this.DichVuVienThong.ADSL) {
                        vtocdotn_id = this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi;
                        if (this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh == this.LoaiHinhTB.INTERNET_ADSL 
                                || this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh == this.LoaiHinhTB.INTERNET_FTTH) {
                            vmuccuoctn_id = 0;
                        }
                    } else if (this.dichvuvt_id == this.DichVuVienThong.MEGAWAN || this.dichvuvt_id == 26) {
                        vtocdotn_id = this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi;
                    } else if (this.dichvuvt_id == this.DichVuVienThong.TSL || this.dichvuvt_id == 25) {
                        vtocdotn_id = this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi;
                    }
                        
                    let khuyenmai_id = this.$refs.v3_ThongTinThueBao.model.v3_khuyen_mai;
                    let kt_km = await this.KIEMTRA_THOIHAN_KM(String(khuyenmai_id), this.ngay_ht);
                    if (kt_km == 0) {
                        let ngay_kt_km = "";
                        let params = { 
                            params :{"khuyenMaiId": khuyenmai_id}
                        };
                        let response = await api.CollectGeneratedAferInstallAPI_layNgayKM(this.axios, params);
                        
                        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                            ngay_kt_km = response.data.data;
                        }

                        var kq = confirm('Khuyến mại ' + khuyenmai_id + " đã hết hạn từ ngày "+ ngay_kt_km +".\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?");
                        if (kq == false) {
                            this.dtienkmld = 0;
                            this.dvatkmld = 0;
                            this.tyle_kmld = 0;
                            this.tong_kmld = 0;
                            this.HienThiTienKMLapDat();
                            return;
                        }
                    }

                    let dulieu = {};
                    dulieu.dsKM_HDTB = this.dsKM_HDTB;
                    dulieu.vtocdotn_id = vtocdotn_id;
                    dulieu.vmuccuoctn_id = vmuccuoctn_id;
                    dulieu.loaihinh_tb = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_loai_hinh;
                    dulieu.khuyenmai_id = khuyenmai_id;
                    dulieu.vdichvuvt_id = this.dichvuvt_id;
                    this.ShowPopupKhuyenMai(dulieu);
                }
            }
        } catch (ex) {
            console.log(ex);
        }
    },
    ShowPopupKhuyenMai(dulieu) {
        console.log(dulieu);
        this.popupComponentData = dulieu;
        this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai');
        this.popupComponentName = "popup-Dangkychitietkhuyenmai";
        this.Popup('popupComponentTSLSpeed');
    },
    ShowPopupChonKhuyenMai() {
        var dulieu = {};
        dulieu.loai = 0;
        dulieu.dt = this.$refs.v3_ThongTinThueBao.v3_LstKhuyenMai;
        this.popupComponentData = dulieu;
        this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai');
        this.popupComponentName = "popup-ChonKhuyenMai";
        this.Popup('popupComponentTSLSpeed');
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(vma_tb, vma_gd, vdichvuvt_id, vloaihd_id) {
        let result = true
        try {
            const params = { 
                maTb: vma_tb,
                maGd: vma_gd,
                dichVuVtId: vdichvuvt_id,
                loaiHdId: vloaihd_id
            };
            let response = await api.ur_024_211007003_kiemtra_thuebao_lap_hopdong_khac(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                let row = response.data.data;
                if (row.ma_tb != undefined) {
                    result = false;
                    let message =
                            "Số máy/Acc: " + vma_tb + " đang được lập bởi hợp đồng (phụ lục) " + row.ten_loaihd + " có mã giao dịch " + row.ma_gd + " do User " 
                                + row.nguoi_cn + " thuộc đơn vị " + row.ten_dv + " thực hiện vào ngày " + row.ngay_yc
                    this.$toast.error(message);
                }
            }
        } catch(e) {
            console.log(e);
            result = false;      
        }
        return result;
    },
    async KIEMTRA_DB_KHI_THEMMOI(maTb, khachHangId, thueBaoId, dichVuVtId) {
        let result = "";
        try {
            const params = { 
                maTb: maTb,
                khachHangId: khachHangId,
                thueBaoId: thueBaoId,
                dichVuVtId: dichVuVtId
            };
            let response = await api.ur_025_21030079_kiemtra_danhba_khi_themmoi(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async LayHuongGiao_TiepNhan(loaiHdId, loaiTbId, tocDoId, mucCuocId, kieuLdId) {
        let result;
        try {
            const params = { 
                loaiHdId: loaiHdId,
                loaiTbId: loaiTbId,
                tocDoId: tocDoId,
                mucCuocId: mucCuocId,
                kieuLdId: kieuLdId
            };
            let response = await api.ur_026_21030081_lay_huonggiao_tiepnhan(this.axios, params);
            
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async getKey(keyName) {
        let key;
        try {
            await api.getKey(this.axios, {
                keyName : keyName,
                numBlockSize : 1,
                numRetry:  10
                }).then((res) => {         
                if (res.data.error_code == "BSS-00000000") {
                    key = res.data.data;
                }
                });
        } catch(e) {
            console.log(e)
        }
      return key;
    },
    async Lay_Ma_GD(loaihd_id) {
        let result = "";
        try {
            const params = { 
                loaihd_id: loaihd_id
            };
            let response = await api.ur_21007051_sinh_magd(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async fn_tt_db_khachhang(type, param) {
        let result = "";
        try {
            const params = { 
                type: type,
                param: param
            };
            let response = await api.fn_tt_db_khachhang(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async fn_tt_doitac(type, param) {
        let result = "";
        try {
            const params = { 
                type: type,
                param: param
            };
            let response = await api.fn_tt_doitac(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async TaoDuLieu(themmoi) {
        await this.TaoDuLieu_HDKH(themmoi);
        await this.TaoDuLieu_HDTB(themmoi);
        this.TaoDuLieu_THONGTIN_TT(themmoi);
        await this.TaoDuLieu_CT_TIENHD(true);
        await this.TaoDuLieu_TDTD_TSL(themmoi);
        await this.TaoDuLieu_HDTBDV(themmoi);
    },
    async TaoDuLieu_HDKH(themmoi) {
        console.log(">>>TaoDuLieu_HDKH")
        this.dsHDKH = [];
        if (themmoi == true) {
            this.hdkh_id = await this.getKey("HD_KHACHHANG");
        }
            
        let rowHDKH = {};
        rowHDKH.HDKH_ID = this.hdkh_id;
        if (themmoi == true) {
            this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich = await this.Lay_Ma_GD(this.LoaiHopDong.THAY_DOI_TOCDO_TSL);
            rowHDKH.MA_GD = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich;
            rowHDKH.MA_HD = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_hop_dong;
        } else {
            rowHDKH.MA_GD = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich;
            rowHDKH.MA_HD = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_hop_dong;
        }

        rowHDKH.MA_KH = this.$refs.v4_ThongTinKhachHang.model.v4_ma_khach_hang;
        if (this.khachhang_id > 0) {
            rowHDKH.KHACHHANG_ID = this.khachhang_id;
        }
            
        rowHDKH.TEN_KH = this.$refs.v4_ThongTinKhachHang.model.v4_ten_khach_hang;
        rowHDKH.DIACHI_KH = this.$refs.v4_ThongTinKhachHang.model.v4_dia_chi_khach_hang;
        rowHDKH.NGAYLAP_HD = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_lap_hd;
        rowHDKH.DONVI_ID = this.$root.token.getDonViID();
        rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID();

        if (this.khachhang_id > 0) {
            let khl_id = await this.fn_tt_db_khachhang(1, this.khachhang_id);
            if (khl_id != "" && khl_id != "-1") {
                rowHDKH.KHLON_ID = Number(khl_id);
            } else {
                rowHDKH.KHLON_ID = 1;
            }        
        } else {
            rowHDKH.KHLON_ID = 1;
        }     

        if (this.hdkh_cha_id != null && this.hdkh_cha_id > 0) {
            rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id;
            this.hdkh_cha_id = 0;
        }

        rowHDKH.LOAIHD_ID = this.LoaiHopDong.THAY_DOI_TOCDO_TSL;
        rowHDKH.NGUOI_CN = this.$root.token.getUserName();
        rowHDKH.NGAY_CN = this.ngay_ht;
        rowHDKH.MAY_CN = this.$root.token.getNguoiDungID();

        let chkCTV = document.getElementById("v1_chb_cong_tac_vien");
        if (chkCTV.checked && this.model.v1_cong_tac_vien != undefined && this.model.v1_cong_tac_vien != "") {
            rowHDKH.CTV_ID = this.ctv_id;
        }

        let chkNguoiGT = document.getElementById("v1_chb_nguoi_gioi_thieu");
        if (chkNguoiGT.checked && this.model.v1_nguoi_gioi_thieu != undefined && this.model.v1_nguoi_gioi_thieu != "") {
            rowHDKH.NHANVIENGT_ID = this.nguoigt_id;
        }

        rowHDKH.NGAY_YC = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_yeu_cau;
        rowHDKH.MA_DUAN = this.model.v1_ma_du_an;

        this.dsHDKH.push(rowHDKH);
    },
    async TaoDuLieu_HDTB(themmoi) {
        console.log(">>>TaoDuLieu_HDTB")
        this.dsHDTB = [];
        if (themmoi == true) {
            this.hdtb_id = await this.getKey("HD_THUEBAO");
        }
            
        let rowHDTB = {};
        rowHDTB.HDTB_ID = this.hdtb_id;
        rowHDTB.HDKH_ID = this.hdkh_id;
        if (this.thuebao_id > 0) {
            rowHDTB.THUEBAO_ID = this.thuebao_id;
        }  

        rowHDTB.MA_TB = this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc;
        rowHDTB.TEN_TB = this.$refs.v3_ThongTinThueBao.model.v3_ten_thue_bao;
        rowHDTB.DIACHI_TB = this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_thue_bao;
        // rowHDTB.DIACHI_LD = txtDiaChiLD.Text);          // visivle = false
        rowHDTB.KIEULD_ID = Number(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_kieu_yeu_cau);
        rowHDTB.DOITUONG_ID = this.doituong_id;
        rowHDTB.TTHD_ID = this.TrangThaiHD.MOI;
        rowHDTB.LOAITB_ID = this.loaitb_id;
        rowHDTB.DICHVUVT_ID = this.dichvuvt_id;
        rowHDTB.DONVI_ID = this.donvi_id;
        rowHDTB.NGUOI_CN = this.$root.token.getUserName();
        rowHDTB.NGAY_CN = this.ngay_ht;
        rowHDTB.MAY_CN = this.$root.token.getNguoiDungID();
        rowHDTB.GHICHU = this.$refs.v3_ThongTinThueBao.model.v3_ghi_chu;
        rowHDTB.QUYTRINH_ID = this.quytrinh_id;

        this.dsHDTB.push(rowHDTB);
        for (let row in this.dsKM_HDTB) {
            this.dsKM_HDTB[row].HDTB_ID = this.hdtb_id;
        }
    },
    TaoDuLieu_THONGTIN_TT(themmoi) {

    },
    async TaoDuLieu_CT_TIENHD(themmoi) {
        console.log(">>>TaoDuLieu_CT_TIENHD")
        this.dsCTTHD = [];
        let rowCTTHD = {};

        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
            rowCTTHD = {};
            rowCTTHD.HDTB_ID = this.hdtb_id;
            if (themmoi == true) {
                let CT_TIENHD_ID = await this.getKey("CT_TIENHD");
                rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID;
            }

            rowCTTHD.KHOANMUCTT_ID = Number(this.ds_tien_khoanmuc[i].khoanmuctt_id);
            rowCTTHD.TIEN = Number(this.ds_tien_khoanmuc[i].tien);
            rowCTTHD.VAT = Number(this.ds_tien_khoanmuc[i].vat);
            rowCTTHD.ID = Number(this.ds_tien_khoanmuc[i].khoanmuctt_id);
            rowCTTHD.LOAI_ID = 1;
            this.dsCTTHD.push(rowCTTHD);
        }
    },
    CopyRow(row) {
        let entries = Object.entries(row);
        let capsEntries = entries.map((entry) => [entry[0].toUpperCase(), entry[1]]);
        return Object.fromEntries(capsEntries);
    },
    async TaoDuLieu_TDTD_TSL(themmoi) {
        console.log(">>>TaoDuLieu_TDTD_TSL")
        this.dsTDTD_TSL = [];
        let ds = await this.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id);
        for (let i = 0; i < ds.length; i++) {
            let rowTDTD_TSL = {};
            // rowTDTD_TSL = ds[i];
            rowTDTD_TSL = this.CopyRow(ds[i])
            rowTDTD_TSL.MA_PL = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_hd_ttkd;

            var chkCongVan = document.getElementById("v2_chb_so_cv_lct");
            if (chkCongVan.checked) {
                rowTDTD_TSL.MA_CV = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_so_sv_lct.trim();
                rowTDTD_TSL.NGAY_CV = this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_cv;
            }

            var chkDoiTac = document.getElementById("v4_chb_doi_tac");
            if (chkDoiTac.checked) {
                rowTDTD_TSL.DOITAC_ID = Number(this.$refs.v4_ThongTinKhachHang.model.v4_doi_tac);
                if (this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tkdt != undefined && this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tkdt != "") {
                    rowTDTD_TSL.CUOC_TKDT = Number(this.Unformat_tien(this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tkdt));
                } else {
                    rowTDTD_TSL.CUOC_TKDT = 0;
                }         

                if (this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tcdt != undefined && this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tcdt != "") {
                    rowTDTD_TSL.CUOC_TCDT = Number(this.Unformat_tien(this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tcdt));
                } else {
                    rowTDTD_TSL.CUOC_TCDT = 0;
                }
                    
                rowTDTD_TSL.MAHD_DT = this.$refs.v4_ThongTinKhachHang.model.v4_ma_hop_dong_dt;
            }

            if (this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_ht_no_vat != undefined && this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_ht_no_vat != "") {
                rowTDTD_TSL.CUOC_HT = Number(this.Unformat_tien(this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_ht_no_vat));
            } else {
                rowTDTD_TSL.CUOC_HT = 0;
            }

            if (this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tbi_no_vat != undefined && this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tbi_no_vat != "") {
                rowTDTD_TSL.CUOC_TBI = Number(this.Unformat_tien(this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tbi_no_vat));
            } else {
                rowTDTD_TSL.CUOC_TBI = 0;
            }

            if (this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tc_no_vat != undefined && this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tc_no_vat != "") {
                 rowTDTD_TSL.CUOC_TC = Number(this.Unformat_tien(this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tc_no_vat));
            } else {
                rowTDTD_TSL.CUOC_TC = 0;
            }

            if (this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tk_no_vat != undefined && this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tk_no_vat != "") {
                rowTDTD_TSL.CUOC_TK = Number(this.Unformat_tien(this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tk_no_vat));
            } else {
                rowTDTD_TSL.CUOC_TK = 0;
            }

            rowTDTD_TSL.MA_BSS = this.$refs.v3_ThongTinThueBao.model.v3_ma_bss;
            rowTDTD_TSL.ID_ERP = this.$refs.v3_ThongTinThueBao.model.v3_ma_erp;
            rowTDTD_TSL.HDTB_ID = this.hdtb_id;
            rowTDTD_TSL.TOCDO_ID = Number(this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi);

            this.dsTDTD_TSL.push(rowTDTD_TSL);
        }
    },
    async LAY_DS_HDTB_DV(thuebao_id) {
        let result = [];
        try {
            const params = { 
                thuebao_id: thuebao_id
            };
            let response = await api.ur_048A_lay_ds_hdtb_dv(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async TaoDuLieu_HDTBDV(themmoi) {
        console.log(">>>TaoDuLieu_HDTBDV")
        this.dsHDTBDV = [];
        let dtTemp = await this.LAY_DS_HDTB_DV(String(this.thuebao_id));
        for (let i = 0; i < dtTemp.length; i++) {
            let row = {};
            row.HDTB_ID = this.hdtb_id;
            row.LOAIDV_ID = Number(dtTemp[i].loaidv_id);
            row.DONVI_ID = Number(dtTemp[i].donvi_id);
            row.KIEUDV_ID = Number(dtTemp[i].kieudv_id);
            this.dsHDTBDV.push(row);
        }
    },
    async ThemMoi_HopDong(dsHDKH, dsHDTB, dsTDTD_TSL, dsTTTT, dsCTTHD, dsHDTBDV, dsKM_HDTB, huonggiao_tn) {
        let result;
        try {
            const params = { 
                js_hdtb_tsl: JSON.stringify(dsTDTD_TSL),
                js_khuyenmai: JSON.stringify(dsKM_HDTB),
                js_hd_khachhang: JSON.stringify(dsHDKH),
                js_thongtin_tt: JSON.stringify(dsTTTT),
                js_hdtb_dv: JSON.stringify(dsHDTBDV),
                js_hd_thuebao: JSON.stringify(dsHDTB),
                js_ct_tienhd: JSON.stringify(dsCTTHD),
                huonggiao_id: huonggiao_tn
            };
            let response = await api.ur_027_sp_themmoi_hopdongtb_thaydoitocdotsl(this.axios, params);
            console.log(response)
            if (response && response.data) {
                if (response.data.error_code != 'BSS-00000000') {
                    result = response.data.message;           
                }         
            } else {
                result = "Có lỗi xảy ra trong quá trình cập nhật";
            }
        } catch(e) {
            console.log(e);
            result = e.message;           
        }
        return result;
    },
    async CapNhat_HopDong(dsHDKH, dsHDTB, dsTDTD_TSL, dsTTTT, dsCTTHD, dsHDTBDV, dsKM_HDTB) {
        let result;
        try {
            const params = { 
                js_hdtb_tsl: JSON.stringify(dsTDTD_TSL),
                js_khuyenmai: JSON.stringify(dsKM_HDTB),
                js_hd_khachhang: JSON.stringify(dsHDKH),
                js_thongtin_tt: JSON.stringify(dsTTTT),
                js_hdtb_dv: JSON.stringify(dsHDTBDV),
                js_hd_thuebao: JSON.stringify(dsHDTB),
                js_ct_tienhd: JSON.stringify(dsCTTHD),
                js_khuyenmai_hdtb: JSON.stringify(dsKM_HDTB)
            };
            let response = await api.ur_029_sp_capnhat_hopdong_thaydoi_tsl(this.axios, params);
            
            if (response && response.data) {
                if (response.data.error_code != 'BSS-00000000') {
                    result = response.data.message;           
                }          
            } else {
                result = "Có lỗi xảy ra trong quá trình cập nhật";
            }
        } catch(e) {
            console.log(e);    
            result = e.message;                  
        }
        return result;
    },
    async fn_kiemtra_tb_tontai_trong_hdtb(hdkh_id, ma_tb, dichvuvt_id) {
        let result = [];
        try {
            const params = { 
                hdkh_id: hdkh_id,
                ma_tb: ma_tb,
                dichvuvt_id: dichvuvt_id
            };
            let response = await api.ur_028_fn_kiemtra_tb_tontai_trong_hdtb(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async KIEMTRA_TB_CUNG_MAKH_TB_PL(ma_gd, thuebao_id) {
        let result = "";
        try {
            const params = { 
                ma_gd: ma_gd,
                thuebao_id: thuebao_id
            };
            let response = await api.ur_033_21007058_kiemtra_tb_cung_makh_tb_pl(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async Update_huonggiao_tiepnhan(hdtb_id, huonggiao_id) {
        let result = "";
        try {
            const params = { 
                hdtb_id: hdtb_id,
                huonggiao_id: huonggiao_id
            };
            let response = await api.ur_030_21007065_update_huonggiao_tiepnhan(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async KIEMTRA_HDTB_KHI_THEMMOI(maTb, hdkhId, dichVuVtId, loaiTbId) {
        let result = "";
        try {
            const params = { 
                maTb: maTb, 
                hdkhId: hdkhId, 
                dichVuVtId: dichVuVtId, 
                loaiTbId: loaiTbId
            };
            let response = await api.ur_032_21030078_kiemtra_hdtb_khi_themmoi(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async LAY_DANHBA_THEO_MATB(ma_tb, dichvuvt_id) {
        let result = [];
        try {
            const params = { 
                dichVuVtId: dichvuvt_id, 
                maTb: ma_tb
            };
            let response = await api.ur_037_21030050_lay_danhba_theo_matb(this.axios, params);
            
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async txtMaTB_KeyPress(event) {
        try {
            if (event.keyCode == 13) {
                event.preventDefault();
                if (this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc && this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim() != "") {
                    await this.txtMaTB_Change();
                }
            }
        } catch (exp) {
            console.log(exp);
        }
    },
    async txtMaTB_Change() {
        console.log(">>>txtMaTB_Change")
        this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc = this.clearTheSpace(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc);
        this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc = this.Catdaunhay(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc);
        if (this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc != "") {
            this.loading(true);
            try {  
                let ds = await this.LAY_DS_HOPDONG_THEO_MA_TB_TSL(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim(), this.LoaiHopDong.THAY_DOI_TOCDO_TSL, 
                    this.TrangThaiHD.MOI, 0, this.dichvuvt_id, "0");

                if (ds.length > 0) {
                    this.HienThiDanhSacHDTB(ds);
                } else {
                    await this.HienThiTT_DanhBa(this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc);
                }   
            } finally {
                this.loading(false);
            }          
        }
    },
    async HienThiTT_DanhBa(ma_tb) {
        console.log(">>>HienThiTT_DanhBa")
        let ds = await this.LAY_DANHBA_THEO_MATB(ma_tb, this.dichvuvt_id);
        if (ds.stt != undefined && ds.stt != null) {
            let kt_hopdong_khac = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(ma_tb.trim(), "0", 
                            Number(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu), this.LoaiHopDong.THAY_DOI_TOCDO_TSL);
            if (!kt_hopdong_khac) return;
            
            if (Number(ds.ky_hd) == 0) {
                this.$toast.error("Khách hàng có Account: " + ma_tb + " chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc");
                return;
            }

            if (Number(ds.trangthaitb_id) != this.TrangThaiTB.THUONG) {
                this.$toast.error("Thuê bao có mã: " + ds.ma_tb
                                    + " đang ở trạng thái: " + ds.trangthai_tb
                                    + "\nBạn không thể thực hiện thay đổi tốc độ cho thuê bao này");
                return;
            }

            //Thông tin khách hàng
            this.$refs.v4_ThongTinKhachHang.model.v4_ma_khach_hang = ds.ma_kh;
            this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_hop_dong = ds.ma_hd;
            this.khachhang_id = Number(ds.khachhang_id);
            this.$refs.v4_ThongTinKhachHang.model.v4_ten_khach_hang = this.ChuanHoaTen(ds.ten_kh);
            this.$refs.v4_ThongTinKhachHang.model.v4_dia_chi_khach_hang = this.ChuanHoaTen(ds.diachi_kh);
            if (ds.ngaylap_hd != undefined && ds.ngaylap_hd != null) {
                try {
                    this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ngay_lap_hd = moment(ds.ngaylap_hd, "DD/MM/yyyy HH:mm:ss").format("DD/MM/yyyy");         
                } catch (e) { console.log(e)} 
            }

            //Thông tin thuê bao
            this.thuebao_id = Number(ds.thuebao_id);
            this.$refs.v3_ThongTinThueBao.model.v3_ten_thue_bao = this.ChuanHoaTen(ds.ten_tb);
            this.diachi_tb = ds.diachi_tb;
            this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_thue_bao = this.ChuanHoaTen(ds.diachi_tb);
            // txtDiaChiLD.Text = CommonFunction.ChuanHoaTen(ds.Tables[0].Rows[0]["diachi_ld"].ToString());
            this.loaitb_id = Number(ds.loaitb_id);
            this.doituong_id = Number(ds.doituong_id);
            this.donvi_id = Number(ds.donviql_id);

            ds = await this.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id);
            let dsTTDC = await this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao_id);
            this.HienThiTTDiaChi(dsTTDC, 3);

            if (ds.length > 0) {
                //Toc do cu
                this.$refs.v3_ThongTinThueBao.model.v3_toc_do_cu = String(ds[0].tocdo_id);
                this.$refs.v3_ThongTinThueBao.model.v3_toc_do_moi = String(ds[0].tocdo_id);
                this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_hd_ttkd = ds[0].ma_pl;
                if (Number(ds[0].daucuoi_id) == 1) {
                    this.diachi_dau_id = Number(ds[0].diachi_id);
                    this.diachi_cuoi_id = Number(ds[1].diachi_id);
                    this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_dau = this.ChuanHoaTen(ds[0].diachi);
                    this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_cuoi = this.ChuanHoaTen(ds[1].diachi);
                    this.$refs.v3_ThongTinThueBao.model.v3_so_ao_dau = ds[0].ma_lt;
                    this.$refs.v3_ThongTinThueBao.model.v3_so_ao_cuoi = ds[1].ma_lt;
                }
                if (Number(ds[0].daucuoi_id) == 2) {
                    this.diachi_dau_id = Number(ds[1].diachi_id);
                    this.diachi_cuoi_id = Number(ds[0].diachi_id);
                    this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_dau = this.ChuanHoaTen(ds[1].diachi);
                    this.$refs.v3_ThongTinThueBao.model.v3_dia_chi_cuoi = this.ChuanHoaTen(ds[0].diachi);
                    this.$refs.v3_ThongTinThueBao.model.v3_so_ao_dau = ds[1].ma_lt;
                    this.$refs.v3_ThongTinThueBao.model.v3_so_ao_cuoi = ds[0].ma_lt;
                }

                this.loaikenh_id = Number(ds[0].loaikenh_id);
                if (ds[0].trangbi_id != undefined && String(ds[0].trangbi_id).trim() != "") {
                    this.trangbi_id = Number(ds[0].trangbi_id);
                } else {
                    this.trangbi_id = 3;
                }
                    
                if (ds[0].doitac_id != undefined && String(ds[0].doitac_id).trim() != "" && String(ds[0].doitac_id).trim() != "0") {
                    let doitac_id = Number(ds[0].doitac_id);
                    this.$refs.v4_ThongTinKhachHang.model.v4_doi_tac = doitac_id;
                    this.$refs.v4_ThongTinKhachHang.model.v4_ma_hop_dong_dt = ds[0].mahd_dt;
                    this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tkdt = this.formatNumber(String(ds[0].cuoc_tkdt));
                    this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tcdt = this.formatNumber(String(ds[0].cuoc_tcdt));
                    $("#v4_chb_doi_tac").prop('checked', true);
                      
                    let str_doitac = await this.fn_tt_doitac(1, doitac_id);
                    this.$toast.error("Thuê bao này có Hợp đồng/PLHĐ với đối tác " + str_doitac);
                } else {               
                    this.$refs.v4_ThongTinKhachHang.model.v4_ma_hop_dong_dt = "";
                    this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tkdt = "0";
                    this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tcdt = "0";
                    $("#v4_chb_doi_tac").prop('checked', false);
                }

                this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tk_no_vat = this.formatNumber(String(ds[0].cuoc_tk));
                this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tc_no_vat = this.formatNumber(String(ds[0].cuoc_tc));
                this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_ht_no_vat = this.formatNumber(String(ds[0].cuoc_ht));
                this.$refs.v4_ThongTinKhachHang.model.v4_cuoc_tbi_no_vat = this.formatNumber(String(ds[0].cuoc_tbi));

                this.$refs.v3_ThongTinThueBao.model.v3_ma_bss = ds[0].ma_bss;
                this.$refs.v3_ThongTinThueBao.model.v3_ma_erp = ds[0].id_erp;
                await this.HT_DS_KhuyenMai_Combobox();
            }

            $("#v3_toc_do_moi").focus();
        } else {
            this.$toast.error("Không tìm thấy thông tin về mã thuê bao " + ma_tb.trim() + "");
            await this.clear();
        }
    },
    async ThemMoi_HopDongTB(dsHDTB, dsTDTD_TSL, dsTTTT, dsCTTHD, dsHDTBDV, huonggiao_tn) {
        let result;
        try {
            const params = { 
                js_hd_thuebao: JSON.stringify(dsHDTB),
                js_hd_khachhang: "[]",
                js_ct_tienhd: JSON.stringify(dsCTTHD),
                js_hdtb_tsl: JSON.stringify(dsTDTD_TSL),
                js_hdtb_dv: JSON.stringify(dsHDTBDV),
                js_thongtin_tt: JSON.stringify(dsTTTT),         
                js_khuyenmai: "[]",
                huonggiao_id: huonggiao_tn
            };
            let response = await api.ur_027A_sp_themmoi_hopdongtb_thaydoitocdotsl(this.axios, params);
            if (response && response.data) {
                if (response.data.error_code != 'BSS-00000000') {
                    result = response.data.message;           
                }          
            } else {
                result = "Có lỗi xảy ra trong quá trình cập nhật";
            }
        } catch(e) {
            console.log(e);  
            result = e.message;         
        }
        return result;
    },
    async txtMaGD_KeyPress(event) {
        console.log(">>>txtMaGD_KeyPress")
        if (event.keyCode == 13) {
            event.preventDefault();
            if(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich && this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich.trim()!="") {
                await this.txtMaGD_Change();
            } 
        }
    },
    async txtMaGD_Change() {
        console.log(">>>txtMaGD_Change")
        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich = this.clearTheSpace(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich);
        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich = this.Catdaunhay(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich);
        
        if (this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich != "") {
            let ds = await this.LAY_DS_HOPDONG_THEO_MA_TB_TSL("0", this.LoaiHopDong.THAY_DOI_TOCDO_TSL, 
                this.TrangThaiHD.MOI, 0, 0, this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich);
            this.HienThiDanhSacHDTB(ds);
        }
    },
    bntLayMaKh_Click() {
        this.popupComponent = ()=>import('@/modules/search/subscriber/SearchAccount');
        this.popupComponentName = "SearchAccount";
        try {
            this.popupComponent.data.isPopup = true;
        } catch(ex) {}
        this.Popup('popupComponentTSLSpeed');
    },
    async popupTienKM() {
        this.$refs.tienKMCuPopupRef.showModal()
    },
    acceptTienKM(val) {
        this.ds_tien_khoanmuc = val;
        if (this.ds_tien_khoanmuc.length > 0) {
            for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
                if (String(this.ds_tien_khoanmuc[i].khoanmuctt_id) == String(this.KHOANMUC_TT.KMTT_DOITOCDO)) {
                    this.dtienld_tsl = Number(this.ds_tien_khoanmuc[i].tien);
                    this.dvatld_tsl = Number(this.ds_tien_khoanmuc[i].vat);
                }
                this.LayTongTien_HDTB();
            }
        }
    },
    async popupClosed(val) {
        console.log(">>>popupClosed")
        try {
            this.loading(true)
            if (this.popupComponentName == "popupTienKM") {
                this.ds_tien_khoanmuc = val;
                if (this.ds_tien_khoanmuc.length > 0) {
                    for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
                        if (String(this.ds_tien_khoanmuc[i].khoanmuctt_id) == String(this.KHOANMUC_TT.KMTT_DOITOCDO)) {
                            this.dtienld_tsl = Number(this.ds_tien_khoanmuc[i].tien);
                            this.dvatld_tsl = Number(this.ds_tien_khoanmuc[i].vat);
                        }
                        this.LayTongTien_HDTB();
                    }
                }
            }
            
            if (this.popupComponentName == "popup-Dangkychitietkhuyenmai") {
                if (val) {
                    this.dsKM_HDTB = val.dsKM_HDTB;
                    this.dtienkmld = val.tien_kmld;
                    this.dvatkmld = val.vat_kmld;
                    this.tyle_kmld = val.tyle_kmld;

                    if (this.dtienkmld == 0) {
                        if (this.tyle_kmld > 0) {
                            this.dtienkmld = Math.round(this.dtienld_tsl * this.tyle_kmld / 100);
                            this.dvatkmld = (this.dtienld_tsl + this.dvatld_tsl) * this.tyle_kmld / 100 - this.dtienkmld;
                        }
                    }
                    this.tong_kmld = this.dtienkmld + this.dvatkmld;
                }

                if (this.dsKM_HDTB && this.dsKM_HDTB.length > 0) {
                    
                } else {
                    this.dsKM_HDTB = [];
                    this.dtienkmld = 0;
                    this.dvatkmld = 0;
                    this.tong_kmld = 0;
                    this.tyle_kmld = 0;
                }
                this.HienThiTienKMLapDat();
            }

            if (this.popupComponentName == "popup-ChonKhuyenMai") {
                if (val.loaikm == 0) {
                    console.log(">>>popupClosed_khuyenmai_id: " + val.khuyenmai.khuyenmai_id)
                    this.$refs.v3_ThongTinThueBao.model.v3_khuyen_mai = val.khuyenmai.khuyenmai_id;
                }
            }    
            
            if (this.popupComponentName == "SearchAccount") {
                if (val.ma_tb) {
                    console.log(">>>popupClosed_ma_tb: " + val.ma_tb)
                    this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc = val.ma_tb;
                    if (this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc && this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc.trim() != "") {
                        await this.txtMaTB_Change();
                    }
                }  
            }

        } catch(e) {
            console.log(e);
        } finally {
            this.ClosePopup('popupComponentTSLSpeed');
            this.ClosePopup("popupComponentPayment")
            this.loading(false);
        }    
    },
    Popup(modalId) { 
        this.$bvModal.show(modalId);
    }, 
    async ClosePopup(modalId) { 
        this.$bvModal.hide(modalId);
    }, 
    async acceptSearchContract(maGD) {
        console.log(">>>acceptSearchContract: " + maGD)
        this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich = maGD;
        if(this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich && this.$refs.v2_ThongTinPhieuYeuCau.model.v2_ma_giao_dich.trim()!="") {
            await this.txtMaGD_Change();
        }      
    },
    cboKhuyenMai_SelectionChangeCommitted() {
        this.dtienkmld = 0;
        this.dvatkmld = 0;
        this.tyle_kmld = 0;
        this.tong_kmld = 0;
        this.dsKM_HDTB = [];
    },
    async lay_db_theo_hdkh_cha(hdkh_cha_id) {
        let res = []
        try {
            let params = {
                "hdkh_cha_id": hdkh_cha_id
            }
            let response = await api.lay_db_theo_hdkh_cha(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                res = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return res
    },
    async LoadTuHopDongTuVan() {
        let dt = await this.lay_db_theo_hdkh_cha(this.hdkh_cha_id)
        if (dt.length > 0) {
            this.$refs.v2_ThongTinPhieuYeuCau.model.v2_dich_vu = String(dt[0].dichvuvt_id)
            this.$refs.v3_ThongTinThueBao.model.v3_so_may_acc = dt[0].ma_tb

            await this.txtMaTB_Change()
        } else {
            this.$toast.error("Khách hàng không có thuê bao Truyền Số Liệu");
        }
    }
  },
  watch: {
      
  },
};
</script>
<style>
  .non-ative a {
    color: #d3d3d3 !important;
  }
  .e-numeric.e-control-wrapper.e-input-group .e-input-group-icon {
    display: none;
  }
</style>
