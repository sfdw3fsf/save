<template>
  <div>
    <breadcrumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li :class="!enable.traphieu ? 'non-ative' : ''" :style="!visiable.traphieu ? 'display: none' : ''" title="Trả phiếu">
          <a @click="tsbtnTraPhieu_Click()">
            <span class="icon one-file-back" title="Trả phiếu"></span> Trả phiếu
          </a>
        </li>
        <li :class="!enable.laphdchuyendoi ? 'non-ative' : ''" :style="!visiable.laphdchuyendoi ? 'display: none' : ''" title="Chuyển phiếu sang lập HĐ/PL">
          <a @click="tsbtnCD_Click()">
            <span class="icon one-file-edit" title="Chuyển phiếu sang lập HĐ/PL"></span> Lập HĐ chuyển đổi
          </a>
        </li>
        <li :class="!enable.hoanthien ? 'non-ative' : ''" :style="!visiable.hoanthien ? 'display: none' : ''" title="Xác nhận phiếu đã được chuyển HĐ/PL">
          <a @click="tsbtnXacNhan_Click()">
            <span class="icon one-file-check" title="Xác nhận phiếu đã được chuyển HĐ/PL"></span> Hoàn thiện
          </a>
        </li>
        <li :class="!enable.ghepma ? 'non-ative' : ''" :style="!visiable.ghepma ? 'display: none' : ''" title="Ghép mã hợp đồng với phiếu giới thiệu">
          <a @click="btnGhepMa_Click()">
            <span class="icon one-folder-code" title="Ghép mã hợp đồng với phiếu giới thiệu"></span> Ghép mã
          </a>
        </li>
        <li :class="!enable.chuyenhdpl ? 'non-ative' : ''" :style="!visiable.chuyenhdpl ? 'display: none' : ''" title="Chuyển phiếu sang lập HĐ/PL">
          <a @click="tsbtnLapHD_Click()">
            <span class="icon one-clipart-arrow-right" title="Chuyển phiếu sang lập HĐ/PL"></span> Chuyển HĐ/PL
          </a>
        </li>
        <li :class="!enable.chuyenhd ? 'non-ative' : ''" :style="!visiable.chuyenhd ? 'display: none' : ''" title="Chuyển phiếu sang lập HĐ/PL">
          <a @click="tsbtnLM_Click()" id="a_btnLM">
            <span class="icon one-file-arrow-right" title="Chuyển phiếu sang lập HĐ/PL"></span> {{textbtnLM}}
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <form @submit.prevent="preventSubmit" id="form-element">
        <div class="row">
            <div class="col-sm-6 col-12">
                <DanhSachHoSo ref="v1_DanhSachHoSo"
                    @NapDanhSach="v1_NapDanhSach"
                    @gridViewTiepNhan_FocusedRowChanged="gridViewTiepNhan_FocusedRowChanged"
                  />
                <ViTriKhachHang ref="v3_ViTriKhachHang" :style="!visiable.gcMap ? 'display: none' : ''"/>
                <!-- <ViTriKhachHang ref="v3_ViTriKhachHang"/> -->
            </div>
            <div class="col-sm-6 col-12">
                <ChiTietAnhHoSo ref="v2_ChiTietAnhHoSo"
                
                    />
            </div>
        </div>
      </form>
    </div>
    
    <ejs-dialog :enableResize='true' :visible="false" ref="lydotra"
            :header='"Nhập lý do trả"' showCloseIcon=true :target="target" width='40%' height="300">
        <LyDoTra @xac_nhan="popup_LyDoTra_XacNhan" ref="ppLyDoTra" />
    </ejs-dialog>

    <ejs-dialog :enableResize='true' :visible="false" ref="noidungtraphieu"
            :header='"Nhập nội dung trả phiếu"' showCloseIcon=true :target="target" width='40%' height="220">
        <NoiDungTraPhieu @tra_phieu="popup_NDTraPhieu_TraPhieu" @huy_bo="popup_NDTraPhieu_HuyBo" ref="ppNoiDungTraPhieu"/>
    </ejs-dialog>

    <ejs-dialog :enableResize='true' :visible="false" ref="ghepma"
            :header='"Ghép mã"' showCloseIcon=true :target="target" width='60%' height="350">
        <GhepMa ref="ppGhepMa" @ghepma_thanhcong="ghepma_thanhcong"/>
    </ejs-dialog>

    <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract" 
        :loai_hd_id="Number(LoaiHD_ID)" :dichvu_id="0" 
        :ngay_yc="ngay_ht" :ngay_kt="ngay_ht" 
        :trangthai_hd="0" />

    <b-modal id="popupComponentLDM" size="xl" ref="popupComponentLDM"
        hide-footer no-close-on-backdrop
        title="LẮP ĐẶT MỚI"
        body-class="p-0 cq-modal">
            <component :is="userComponentModule" :ispopup="true" :PHIEUHS_ID="PhieuID" :OnDatMoi="true" 
                v-on="popupComponentEvents" />
    </b-modal>

   <b-modal id="popupComponentCQ" size="xl" ref="popupComponentCQ"
        hide-footer no-close-on-backdrop
        title="[LẬP HỢP ĐỒNG]: CHUYỂN QUYỀN"
        body-class="p-0 cq-modal">
            <component :is="userComponentModule" :ispopup="true" />
    </b-modal>

  </div>
</template>
<script>

import breadcrumb from '@/components/breadcrumb'
import api from "./api/index.js";
import DanhSachHoSo from './DanhSachHoSo.vue'
import ChiTietAnhHoSo from './ChiTietAnhHoSo.vue'
import ViTriKhachHang from './ViTriKhachHang.vue'
import moment from "moment";
import LyDoTra from "./popup/popup-LyDoTra.vue";
import NoiDungTraPhieu from "./popup/popup-NoiDungTraPhieu.vue";
import GhepMa from "./popup/popup-GhepMaHopDongPhuLuc.vue";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
// const lapdatmoi = () => import('@/modules/contract/setup/InstallNewSubs/')
const chuyenquyen = () => import('@/modules/contract/setup/ChangeContractOwner/')

import Vue from 'vue';
import { DropDownListPlugin } from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);

export default {
  components: {
    breadcrumb,
    DanhSachHoSo,
    ChiTietAnhHoSo,
    ViTriKhachHang,
    LyDoTra,
    NoiDungTraPhieu,
    GhepMa,
    SearchContractModal
  },
  data() {
    return {
      position: { X: 'center', Y: 'top' },
      userComponentModule: null,
      popupComponentEvents: {
        OnDatMoi: this.frmTiepNhanHopDongSMP_OnDatMoi
      },
      target: '.main-wrapper',
      enable: {
        traphieu: false,
        laphdchuyendoi: false,
        hoanthien: false,
        ghepma: false,
        chuyenhdpl: false,
        chuyenhd: false,
      },
      visiable: {
        traphieu: true,
        laphdchuyendoi: true,
        hoanthien: true,
        ghepma: true,
        chuyenhdpl: true,
        chuyenhd: true,
        gcMap: true,
        colDiaChi: true,
        colMaTB: true,
        colGCL: true,
        colLoaiHD: true,
        colSDT: true,
        colDonVIGiao: true,
        colMaTB: true,
      },
    //   path: "http://10.70.52.164:8081/FILE/",
      path: "https://vnptcab.vn:8888/wsHandler.ashx?action=lay_anh",
      ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
      THONGBAO_HOSO_GUITOI: false,
      TRANGTHAI_PH: {
          DATRALAI: 3,
          DATHUCHIEN: 4,
      },
      TINHTRANG_HS: {
          DA_LAP_HOPDONG: 4
      },
      LoaiHopDong: {
          DAT_MOI: 1,
          CHUYEN_QUYEN: 2,
          DI_CHUYEN: 3,
          CHUYENDOI_LH: 6,
          THAY_DOI_DV: 7,
          THAY_DOI_TOCDO_ADSL: 8,
          TUVAN_DV: 38
      },
      palInfo: false,
      lap_hd: "",
      loaihd_nc: "",
      tenKH: "",
      loai_hd: "",
      rowSelected: null,
      PhieuID: null,
      Phieu_CHA_ID: null,
      TTKH_ID: null,
      SDKH_ID: null,
      Hdtb_ID: null,
      LoaiHD_ID: "-1",
      KhachHang_ID: null,
      Ma_KH: null,
      textbtnLM: "Chuyển HĐ",
      header: {
        title: "Danh sách hồ sơ chờ lập HĐ/PL",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Danh sách hồ sơ chờ lập HĐ/PL",
            link: { name: "Ui.buttons" },
          },
        ],
      },
    };
  },
  mounted() {
    this.init();
  },
  created() {
    this.$root.$on('bv::modal::show', (bvEvent, modalId) => {
      document.getElementsByClassName('modal-content').forEach(function(v) {
        v.removeAttribute('tabindex')
      })
    })
  },
  methods: {
    preventSubmit() {
        return false;
    },
    async KiemTraTrangThaiPhieu(vPhieuID) {
        let kt = false;
        try {
            let params = {
                phieu_id: vPhieuID
            }
            let response = await api.ur_005_kiemtra_trangthai_phieu(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data 
                    && response.data.data.length > 0 && response.data.data[0].phieu_id == String(vPhieuID)) {
                kt = true;       
            }
        } catch(e) {
            console.log(e);           
        }
        return kt;
    },
    popup_LyDoTra_XacNhan(ndTra) {
        this.$refs.lydotra.hide();
        this.capnhat_traphieu(ndTra)
    },
    async capnhat_traphieu(ndTra) {
        try {
            this.loading(true);
            let params = {
                nd_tra: ndTra,
                nhanvien_tra_id: this.$root.token.getNhanVienID(),
                phieu_id: this.PhieuID,
                ttph_id: this.TRANGTHAI_PH.DATRALAI
            }
            let response = await api.ur_005_capnhat_traphieu(this.axios, params);
            if (response && response.data) {
                if (response.data.error_code == 'BSS-00000000') {
                    this.$toast.success("Trả phiếu thành công");              
                } else {
                    this.$toast.error(response.data.message);
                }
            } else {
                this.$toast.error("Có lỗi khi trả phiếu");
            }
        } catch(e) {
            console.log(e);    
            this.$toast.error(e);
        } finally {
            await this.NapDanhSach();
            this.loading(false);
        }
    },
    popup_NDTraPhieu_TraPhieu(ndTra) {
        this.$refs.noidungtraphieu.hide();
        this.thaotac_phieu_hdtv(ndTra);
    },
    async thaotac_phieu_hdtv(ndTra) {
        try {
            this.loading(true);
            let params = {
                data: this.createXmlTraPhieu(),
                kieu: 3,
                lydotra_id: ndTra.lydo,
                nd_tra: ndTra.noidung,
            }
            let response = await api.ur_005_thaotac_phieu_hdtv(this.axios, params);
            console.log(response)
            if (response && response.data) {
                if (response.data.error_code == 'BSS-00000000') {
                    this.$toast.success("Trả phiếu thành công");              
                } else {
                    this.$toast.error(response.data.message);
                }
            } else {
                this.$toast.error("Có lỗi khi trả phiếu");
            }
        } catch(e) {
            console.log(e);    
            this.$toast.error(e);
        } finally {
            await this.NapDanhSach();
            this.loading(false);
        }   
    },
    popup_NDTraPhieu_HuyBo() {
        this.$refs.noidungtraphieu.hide();
    },
    createXmlTraPhieu() {
        let data = '<ds><vt ';
        data += 'ph="' +  (this.PhieuID ? this.PhieuID : "") + '" ';
        data += 'pc="' +  (this.Phieu_CHA_ID ? this.Phieu_CHA_ID : "") + '" ';
        data += 'hd="' +  (this.Hdtb_ID ? this.Hdtb_ID : "") + '" ';
        data += '/></ds>'
        return data;
    },
    async tsbtnTraPhieu_Click() {
        if (!this.enable.traphieu) return;
        let PhanLoaiHS_ID = Number(this.$refs.v1_DanhSachHoSo.model.v1_phan_loai);
        try {
            this.loading(true);
            if (PhanLoaiHS_ID != 2) {
                let kt_trangthai = await this.KiemTraTrangThaiPhieu(Number(this.PhieuID));
                if (!kt_trangthai) {
                    await this.NapDanhSach();
                    return;
                }              
                this.$refs.ppLyDoTra.noiDungTra = "";
                this.$refs.lydotra.show();
            } else {              
                this.$refs.ppNoiDungTraPhieu.init();
                this.$refs.noidungtraphieu.show();
            }              
        } catch (e) {
            console.log(e);
            this.$toast.error("Có lỗi khi trả phiếu");
        } finally {
            this.loading(false);
        }
    },
    async tsbtnCD_Click() {
        if (!this.enable.laphdchuyendoi) return;
        try {
            this.loading(true);
            if (this.rowSelected.hdkh_id == undefined || this.rowSelected.hdkh_id == null || this.rowSelected.hdkh_id == "") {
                this.$toast.error("Bạn chưa chọn đúng thông tin khách hàng");
                return;
            }

            let TTPhieu_ID = Number(this.$refs.v1_DanhSachHoSo.model.v1_trang_thai);
            if (TTPhieu_ID == 1) {
                let kt = await this.fn_kiemtra_capnhat_lmcd(this.PhieuID, this.Hdtb_ID);
                if (kt != "OK") {                   
                    this.$toast.error(kt);
                    return;
                }
            }

            let hdkh_id = this.rowSelected.hdkh_id;
            let ketcuoi_id = (this.rowSelected.ketcuoi_id != undefined && this.rowSelected.ketcuoi_id != null) ? Number(this.rowSelected.ketcuoi_id) : -1;

            // frm.KetCuoi_ID = ketcuoi_id;
            this.$router.push({
                path: 'ChangeSubsType',
                query: { hdkh_cha_id: hdkh_id }
            })    
        } catch (e) {
            console.log(e);
            this.$toast.error("Lập HĐ chuyển đổi thất bại");
        } finally {
            this.loading(false);
        }
    },
    async count_hd_khachhang(vhdkh_cha_id) {
        let result = "0";
        try {
            let params = {
              "hdkh_cha_id": vhdkh_cha_id
            }
            let response = await api.ur_008_count_hd_khachhang(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                result = response.data.message;              
            }
        } catch(e) {
            console.log(e);           
        }
        return Number(result);
    },
    async tuvan_hths() {
        try {   
            const params = {
                donvi_id: this.$root.token.getDonViID(),
                may_cn: this.$root.token.getNguoiDungID(),
                nd_giao: "Hoàn thiện hồ sơ",
                nguoi_cn: this.$root.token.getUserName(),
                nhanvien_id: this.$root.token.getNhanVienID(),
                phieu_id: this.PhieuID
            }
            let response = await api.ur_008_tuvan_hths(this.axios, params);
            if (response && response.data) {
                if (response.data.error_code === 'BSS-00000000') {
                    this.$toast.success("Thành công");
                } else {
                    this.$toast.error("Thất bại, lỗi: " + response.data.message);  
                }           
            } else {
                this.$toast.error("Thất bại");
            }                                
        } catch (e) {
            console.log(e);
            this.$toast.error("Xảy ra lỗi: " + e);  
        } finally {
            await this.NapDanhSach();
        }
    },
    async update_giaophieu_hs(hdkh_id, nhanvien_ht_id, phieu_id, tinhtranghs_id, ttph_id) {
        let tt = true;
        try {   
            const params = {
                hdkh_id: hdkh_id,
                nhanvien_ht_id: nhanvien_ht_id,
                phieu_id: phieu_id,
                tinhtranghs_id: tinhtranghs_id,
                ttph_id: ttph_id
            }
            let response = await api.ur_008_update_giaophieu_hs(this.axios, params);
            if (response && response.data) {
                if (response.data.error_code === 'BSS-00000000') {
                    
                } else {
                    this.$toast.error("Thất bại, lỗi: " + response.data.message);  
                    tt = false;
                }           
            } else {
                this.$toast.error("Thất bại");
                tt = false;
            }                                
        } catch (e) {
            console.log(e);
            this.$toast.error("Xảy ra lỗi: " + e);  
            tt = false;
        }
        return tt;
    },
    async update_tt_didong() {
        let tt = true;
        try {   
            const params = {
                nguoi_cn: this.$root.token.getUserName(),
                ttkh_id: this.rowSelected.ttkh_id
            }
            let response = await api.ur_008_update_tt_didong(this.axios, params);
            if (response && response.data) {
                if (response.data.error_code === 'BSS-00000000') {
                    
                } else {
                    this.$toast.error("Thất bại, lỗi: " + response.data.message);  
                    tt = false;
                }           
            } else {
                this.$toast.error("Thất bại");
                tt = false;
            }                                
        } catch (e) {
            console.log(e);
            this.$toast.error("Xảy ra lỗi: " + e);  
            tt = false;
        }
        return tt;
    },
    async acceptSearchContract(item) {
        if (item.ma_gd == "") {
            this.$toast.error("Giao dịch không tồn tại");
            return;
        }

        if (item.loaihd_id != this.rowSelected.loaihd_id) {
            this.$toast.error("Loại hợp đồng của mã giao dịch " + item.ma_gd + " không khớp với hồ sơ, yêu cầu kiểm tra lại!");
            return;
        }

        this.$bvModal.msgBoxConfirm('Bạn có muốn xác nhận phiếu đã được chuyển sang HĐ/PL có mã gd:' + item.ma_gd + '?', {
            title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
        }).then(async v => {
            if (v) {
                console.log(this.$root.token)
                if (!await this.update_giaophieu_hs(item.hdkh_id, this.$root.token.getNhanVienID(), this.PhieuID, 
                                        this.TINHTRANG_HS.DA_LAP_HOPDONG, this.TRANGTHAI_PH.DATHUCHIEN)) return;
                this.$toast.success("Thành công");
                await this.NapDanhSach();
            }
        });           
    },
    async tsbtnXacNhan_Click() {
        if (!this.enable.hoanthien) return;
        let PhanLoaiHS_ID = Number(this.$refs.v1_DanhSachHoSo.model.v1_phan_loai);
        try {
            this.loading(true);
            if (PhanLoaiHS_ID == 2) {
                let hdkh_cha_id = this.rowSelected.hdkh_id;
                let count = await this.count_hd_khachhang(hdkh_cha_id);
                if (count == 0) {
                    this.$toast.error("Hợp đồng chưa có mã ghép. Bạn cần ghép mã trước khi hoàn thiện!");
                    return;
                }

                this.$bvModal.msgBoxConfirm('Bạn có muốn hoàn thiện phiếu này?', {
                    title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                }).then(async v => {
                    if (v) {
                        await this.tuvan_hths();
                    }
                });
            } else {
                let kt_trangthai = await this.KiemTraTrangThaiPhieu(Number(this.PhieuID));
                if (!kt_trangthai) {
                    await this.NapDanhSach();
                    return;
                }

                if (PhanLoaiHS_ID == 1) {
                    this.$refs.popupSearchContract.showModal();
                } else if (PhanLoaiHS_ID == 0) {
                    //Hồ sơ di động thì chỉ cập nhật trạng thái
                    this.$bvModal.msgBoxConfirm('Bạn có muốn xác nhận phiếu đã được chuyển sang HĐ/PL?', {
                            title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                        }).then(async v => {
                            if (v) {
                                if (!await this.update_giaophieu_hs(0, this.$root.token.getNhanVienID(), this.PhieuID, 
                                                        this.TINHTRANG_HS.DA_LAP_HOPDONG, this.TRANGTHAI_PH.DATHUCHIEN)) return;
                                if (!await this.update_tt_didong()) return;
                                this.$toast.success("Thành công");
                                await this.NapDanhSach();
                            }
                        });        
                }
            }
        } catch (e) {
            console.log(e);
            this.$toast.error("Hoàn thiện thất bại");
        } finally {
            this.loading(false);
        }
    },
    btnGhepMa_Click() {
        if (!this.enable.ghepma) return;
        this.$refs.ppGhepMa.init();
        this.$refs.ghepma.show();
    },
    ghepma_thanhcong() {
        this.$refs.ghepma.hide();
    },
    async tsbtnLapHD_Click() {
        if (!this.enable.chuyenhdpl) return;
        try {
            this.loading(true);
            let kt_trangthai = await this.KiemTraTrangThaiPhieu(Number(this.PhieuID));
            if (!kt_trangthai) {
                await this.NapDanhSach();
                return;
            }

            // let CurrentLoaiHD_ID = "1"; this.Ma_KH = "HPG-000846164"; this.KhachHang_ID = "800666";
            let CurrentLoaiHD_ID = this.loai_hd;
            if (CurrentLoaiHD_ID == String(this.LoaiHopDong.DAT_MOI)) {
                this.frmDatmoi_Shown();
            } else if (CurrentLoaiHD_ID == String(this.LoaiHopDong.CHUYENDOI_LH)) {
                // mo form Thay doi loai hinh thue bao ChangeSubsType (2.1.035) kem PHIEUHS_ID
                this.$toast.success("Mở form Thay đổi loại hình thuê bao UR2.1.035");
            }
        } catch (e) {
            console.log(e);
            this.$toast.error("Chuyển HĐ/PL thất bại");
        } finally {
            this.loading(false);
        }
    },
    async getDiaChiObjFromSdkhID(vSdkh_id) {
        let result;
        try {   
            const params = {
                p_dkh_id: vSdkh_id
            }
            let response = await api.ur_004_danhmuc_ds_diachi_theo_sdkhid(this.axios, params);
            console.log(response)
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
                result = response.data.data[0];
            }                            
        } catch (e) {
            console.log(e);
        }
        return result;
    },
    async frmDatmoi_Shown() {
        try {
            // this.userComponentModule = lapdatmoi
            this.userComponentModule = () => import('@/modules/contract/setup/InstallNewSubs/')
            this.$bvModal.show("popupComponentLDM")
            if (this.KhachHang_ID != 0 && this.Ma_KH != "") {
                this.loading(true)
                setTimeout(()=>{
                    this.$refs.popupComponentLDM.$children[0].$children[0].khachhang.makh = this.Ma_KH
                }, 500)
                this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật thông tin địa chỉ khách hàng mới?', {
                    title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                }).then(async v => {
                    if (v) {
                        var diachiObj = await this.getDiaChiObjFromSdkhID(this.rowSelected.sdkh_id);
                        if (diachiObj != null) {
                            setTimeout(()=>{
                                this.$refs.popupComponentLDM.$children[0].$children[0].diachiKH = diachiObj
                            }, 200)
                            this.$bvModal.show("popupDiaChiKH");
                        } else {
                            this.$bvModal.msgBoxConfirm('Không có thông tin về địa chỉ khách hàng, bạn có muốn tiếp tục lập hợp đồng?', {
                                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                            }).then(async v => {
                                if (!v) {
                                    this.$refs.frmDatMoi.hide();
                                }
                            });
                        }
                    }
                });    
            }     
        } catch (ex) {
            this.$toast.error("frmDatmoi_Shown:" + ex.message);
        }
    },
    async frmTiepNhanHopDongSMP_OnDatMoi(val) {
        this.$refs.frmDatMoi.hide();
        if (val.PhieuHs_ID == undefined || val.PhieuHs_ID == 0) return;
        var check = await this.fn_tt_giaophieu_hs(1, val.PhieuHs_ID);
        if (check == "" || check == "-1") return;

        if (!await this.update_giaophieu_hs(val.hdkh_id, this.$root.token.getNhanVienID(), val.PhieuHs_ID, 
                                                this.TINHTRANG_HS.DA_LAP_HOPDONG, this.TRANGTHAI_PH.DATHUCHIEN)) return;

        this.$toast.success("Chuyển hồ sơ của khách hàng [" + val.TenKH + "] sang HĐ lắp mới thành công!");
        await this.NapDanhSach();
    },
    async frmTiepNhanHopDongSMP_OnThayDoiLHTB(hdkh_id, hdtb_id, phieuhs_id, tenkh) {     // invoke tu form ChangeSubsType
        if (phieuhs_id == 0) return;
        var check = await this.fn_tt_giaophieu_hs(1, phieuhs_id);
        if (check == "" || check == "-1") return;

        if (!await this.update_giaophieu_hs(hdkh_id, this.$root.token.getNhanVienID(), phieuhs_id, 
                                                this.TINHTRANG_HS.DA_LAP_HOPDONG, this.TRANGTHAI_PH.DATHUCHIEN)) return;

        this.$toast.success("Chuyển hồ sơ của khách hàng [" + tenkh + "] sang HĐ chuyển đổi LHTB thành công!");
        await this.NapDanhSach();
    },
    async fn_tt_giaophieu_hs(type, param) {
        let result = "";
        try {
            const params = { 
                type: type,
                param: param
            };
            let response = await api.fn_tt_giaophieu_hs(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async fn_kiemtra_capnhat_lmcd(vphieu_id, vhdtb_id) {
        let result = "";
        try {
            const params = { 
                vphieu_id: vphieu_id,
                vhdtb_id: vhdtb_id
            };
            let response = await api.ur_011_fn_kiemtra_capnhat_lmcd(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async tsbtnLM_Click() { 
        if (!this.enable.chuyenhd) return;
        try {
            this.loading(true);
            if (this.loaihd_nc == String(this.LoaiHopDong.DAT_MOI) || this.loaihd_nc == "-1" ||
                 this.loaihd_nc == String(this.LoaiHopDong.DI_CHUYEN) || this.loaihd_nc == String(this.LoaiHopDong.CHUYEN_QUYEN) || 
                 this.loaihd_nc == String(this.LoaiHopDong.THAY_DOI_DV) || this.loaihd_nc == String(this.LoaiHopDong.THAY_DOI_TOCDO_ADSL) || 
                 this.loaihd_nc == String(this.LoaiHopDong.CHUYENDOI_LH) || this.loaihd_nc == "99") {
                     
                // chung cho cac dich vu
                if (this.rowSelected.hdkh_id == undefined || this.rowSelected.hdkh_id == null || this.rowSelected.hdkh_id == "") {
                    this.$toast.error("Bạn chưa chọn đúng thông tin khách hàng");
                    return;
                }
                
                let TTPhieu_ID = Number(this.$refs.v1_DanhSachHoSo.model.v1_trang_thai);
                if (TTPhieu_ID == 1) {
                    let kt = await this.fn_kiemtra_capnhat_lmcd(this.PhieuID, this.Hdtb_ID);
                    if (kt != "OK") {                   
                        this.$toast.error(kt);
                        return;
                    }
                }

                let hdkh_id = Number(this.rowSelected.hdkh_id);
                let ketcuoi_id = (this.rowSelected.ketcuoi_id != undefined && this.rowSelected.ketcuoi_id != null) ? Number(this.rowSelected.ketcuoi_id) : -1;

                if (this.loaihd_nc == String(this.LoaiHopDong.DAT_MOI) || this.loaihd_nc == "-1") {
                    this.loading(true)
                    // this.userComponentModule = lapdatmoi
                    this.userComponentModule = () => import('@/modules/contract/setup/InstallNewSubs/')
                    console.log("_zzzzzzz: import xong r ne")
                    this.$bvModal.show("popupComponentLDM")
                    setTimeout(async ()=>{                       
                        console.log("_zzzzzzz: buttonSet = " + this.$refs.popupComponentLDM.$children[0].$children[0].buttonSet)
                        console.log("_zzzzzzz: watcherActive = " + this.$refs.popupComponentLDM.$children[0].$children[0].watcherActive)
                        this.$refs.popupComponentLDM.$children[0].$children[0].KetCuoi_ID = ketcuoi_id
                        this.$refs.popupComponentLDM.$children[0].$children[0].buttonSet = 0
                        this.$refs.popupComponentLDM.$children[0].$children[0].watcherActive = true
                        await this.$refs.popupComponentLDM.$children[0].$children[0].LoadTuHopDongTuVan(hdkh_id)
                        console.log("_zzzzzzz: buttonSet = " + this.$refs.popupComponentLDM.$children[0].$children[0].buttonSet)  
                        console.log("_zzzzzzz: watcherActive = " + this.$refs.popupComponentLDM.$children[0].$children[0].watcherActive)             
                    }, 500)
                } else if (this.loaihd_nc == String(this.LoaiHopDong.DI_CHUYEN)) {
                    // TODO mo form chuyen dich - UR2.1.005
                    this.$toast.success("Mở form Chuyển dịch UR2.1.005");
                    // var frm = new frmChuyenDich();
                    // frm.MdiParent = this.MdiParent;
                    // frm.Show();
                    // frm.LoadTuHopDongTuVan(hdkh_id);
                    // frm.KetCuoi_ID = ketcuoi_id;
                } else if (this.loaihd_nc == String(this.LoaiHopDong.CHUYEN_QUYEN)) {
                    this.loading(true)
                    this.userComponentModule = chuyenquyen
                    this.$bvModal.show("popupComponentCQ")
                    setTimeout(()=>{
                       this.$refs.popupComponentCQ.$children[0].$children[0].LoadTuHopDongTuVan(hdkh_id)
                    }, 500)
                } else if (this.loaihd_nc == String(this.LoaiHopDong.THAY_DOI_DV)) {
                    // TODO mo form thay doi dich vu gia tang - UR2.1.053
                    this.$toast.success("Mở form Thay đổi dịch vụ gia tăng UR2.1.053");
                    // var frm = new frmThayDoiDV();
                    // frm.MdiParent = this.MdiParent;
                    // frm.Show();
                    // frm.LoadTuHopDongTuVan(hdkh_id);
                } else if (this.loaihd_nc == String(this.LoaiHopDong.THAY_DOI_TOCDO_ADSL)) {
                    this.$router.push({
                        path: 'ChangeInternetSpeed',
                        query: { hdkh_cha_id: hdkh_id, ctv_id: Number(this.rowSelected.nhanvien_giao_id) }
                    })   
                } else if (this.loaihd_nc == "99") {  // chuyen mang MNP
                    // TODO mo form cap nhat thong tin thue bao di dong chuyen mang den - UR2.1.062
                    this.$toast.success("Mở form Cập nhật thông tin thuê bao di động chuyển mạng đến (PI) UR2.1.062");
                    // var frm = new frmCapNhatDDTT();
                    // frm.MdiParent = this.MdiParent;
                    // frm.vphieu_tv_id = PhieuID;
                    // frm.Show();
                    // frm.LoadTuHopDongTuVan(hdkh_id);
                } else if (this.loaihd_nc == String(this.LoaiHopDong.CHUYENDOI_LH)) {
                    // frm.KetCuoi_ID = ketcuoi_id;
                    this.$router.push({
                        path: 'ChangeSubsType',
                        query: { hdkh_cha_id: hdkh_id }
                    })
                }
            }
        } catch (e) {
            console.log(e);
            this.$toast.error("Chuyển HĐ thất bại");
        } finally {
            this.loading(false);
        }
    },
    async init() {
        try {
            this.loading(true);        
            this.$refs.v2_ChiTietAnhHoSo.fixWidthXemTruoc = document.getElementById("v2_img_xt_div").clientWidth + "px";
            this.$refs.v2_ChiTietAnhHoSo.fixHeightXemTruoc = document.getElementById("v2_img_xt_div").clientHeight + "px";
            this.frmTiepNhanHopDongSMP_Load();
            await this.frmTiepNhanHopDongSMP_Shown();
            await this.getDSLoaiHD();
            await this.getDSLoaiFileHD();
            document.getElementById("container").style.width = document.getElementById("boxImage").clientWidth + "px";
            document.getElementById("container").style.height = (document.getElementById("boxImage").clientHeight - document.getElementById("boxImageControl").clientHeight - 10) + "px";        
        } catch (e) {
            console.log(e);
            this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
        } finally {
            this.loading(false);
            this.$bvModal.hide('popupComponents')
        }
    },
    frmTiepNhanHopDongSMP_Load() {
        $('#v2_chb_thong_bao').prop('checked', this.THONGBAO_HOSO_GUITOI);
    },
    async frmTiepNhanHopDongSMP_Shown() {
        var today = moment();
        this.$refs.v1_DanhSachHoSo.model.v1_tu_ngay = moment(today).add(-7, 'days').format("DD/MM/yyyy");
        this.$refs.v1_DanhSachHoSo.model.v1_den_ngay = this.ngay_ht;
        await this.NapDanhSach();
    },
    async getDSLoaiHD() {
        try {
            let response = await api.ur_001_loai_hopdong_phanloai(this.axios);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                // this.$refs.v2_ChiTietAnhHoSo.v2_LstLoaiHD = response.data.data;
                // if (this.$refs.v2_ChiTietAnhHoSo.v2_LstLoaiHD.length > 0) {
                //     this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = this.$refs.v2_ChiTietAnhHoSo.v2_LstLoaiHD[0].ID;
                // }                
            }
        } catch(e) {
            console.log(e);           
        }
    },
    async getDSLoaiFileHD() {
        this.$refs.v2_ChiTietAnhHoSo.v2_LstLoaiFileHD = [];
        try {
            let response = await api.ur_002_loai_file_hopdong(this.axios);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                let mmCXD = {"ID":"-1", "NAME":"Chưa xác định"};
                response.data.data.unshift(mmCXD)
                this.$refs.v2_ChiTietAnhHoSo.v2_LstLoaiFileHD = response.data.data.map((x)=>({id:x.ID, text:x.NAME}))
                this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = "-1";
            }
        } catch(e) {
            console.log(e);           
        }
    },
    async LAY_DS_HOSO_CHO_LAP_HDPL(vNhanVien, vDonVi, vTrangThai, vPhanLoai, vTuNgay, vDenNgay) {
        let result = [];
        try {
            let params = {
              "denngay": vDenNgay,
              "donvi_id": vDonVi,
              "nhanvien_id": vNhanVien,
              "phanloai_id": vPhanLoai,
              "ttph_id": vTrangThai,
              "tungay": vTuNgay
            }
            let response = await api.ur_003_ds_hoso_cho_laphopdong(this.axios, params);
            console.log(response)
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;              
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async sp_lay_anh_hs(vhdkh_id, vhdtb_id) {
        let result = [];
        try {
            let params = {
              "hdkh_id": vhdkh_id,
              "hdtb_id": vhdtb_id
            }
            let response = await api.ur_014_sp_lay_anh_hs(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;              
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async v1_NapDanhSach() {
        try {
            this.loading(true);
            await this.NapDanhSach();
        } finally {
            this.loading(false);
        }
    },
    async NapDanhSach() {        
        let PhanLoaiHS_ID = Number(this.$refs.v1_DanhSachHoSo.model.v1_phan_loai);
        let TTPhieu_ID = Number(this.$refs.v1_DanhSachHoSo.model.v1_trang_thai);

        this.palInfo = TTPhieu_ID == 1;        
        this.visiable.colDiaChi = PhanLoaiHS_ID == 1 || PhanLoaiHS_ID == 2;
        this.visiable.colMaTB = PhanLoaiHS_ID == 0 || PhanLoaiHS_ID == 2;
        this.visiable.colGCL = PhanLoaiHS_ID == 2 && TTPhieu_ID != 3;
        this.visiable.chuyenhdpl = PhanLoaiHS_ID == 1;

        if (PhanLoaiHS_ID == 0) {
            this.visiable.traphieu = true; this.enable.traphieu = (TTPhieu_ID == 1);
            this.visiable.laphdchuyendoi = true; this.enable.laphdchuyendoi = (TTPhieu_ID == 1 || TTPhieu_ID == 2);
            this.visiable.ghepma = false;
            this.visiable.chuyenhdpl = false;
            this.visiable.chuyenhd = true; this.enable.chuyenhd = (TTPhieu_ID == 1 || TTPhieu_ID == 2);

            this.visiable.gcMap = false;
            this.visiable.colDonVIGiao = false;
            this.visiable.colSDT = false;
            this.visiable.colLoaiHD = false;
            this.$refs.v1_DanhSachHoSo.v1_dtDSHoSo = [];
        } else if (PhanLoaiHS_ID == 1) {
            this.visiable.traphieu = true; this.enable.traphieu = (TTPhieu_ID == 1);
            this.visiable.laphdchuyendoi = true; this.enable.laphdchuyendoi = (TTPhieu_ID == 1 || TTPhieu_ID == 2);     
            this.visiable.ghepma = false;
            this.visiable.chuyenhdpl = true;  this.enable.chuyenhdpl = (TTPhieu_ID == 1);
            this.visiable.chuyenhd = false; 

            this.visiable.gcMap = true;
            this.visiable.colLoaiHD = true;
            this.visiable.colSDT = false;
            this.visiable.colDonVIGiao = false;
            this.$refs.v1_DanhSachHoSo.v1_dtDSHoSo = [];
        } else if (PhanLoaiHS_ID == 2) {
            this.visiable.traphieu = true; this.enable.traphieu = (TTPhieu_ID == 1 || TTPhieu_ID == 2);
            this.visiable.laphdchuyendoi = true; this.enable.laphdchuyendoi = (TTPhieu_ID == 1 || TTPhieu_ID == 2);
            this.visiable.ghepma = true; this.enable.ghepma = true;
            this.visiable.chuyenhdpl = false;
            this.visiable.chuyenhd = true; this.enable.chuyenhd = (TTPhieu_ID == 1 || TTPhieu_ID == 2);

            this.visiable.gcMap = true;
            this.visiable.colDonVIGiao = true;
            this.visiable.colLoaiHD = true;
            this.visiable.colSDT = true;      
            this.$refs.v1_DanhSachHoSo.v1_dtDSHoSo = [];
        } else if (PhanLoaiHS_ID == 3) {
            this.visiable.traphieu = true; this.enable.traphieu = (TTPhieu_ID == 1 || TTPhieu_ID == 2);
            this.visiable.laphdchuyendoi = true; this.enable.laphdchuyendoi = (TTPhieu_ID == 1 || TTPhieu_ID == 2);
            this.visiable.hoanthien = false;
            this.visiable.ghepma = false; 
            this.visiable.chuyenhdpl = false;
            this.visiable.chuyenhd = true; this.enable.chuyenhd = (TTPhieu_ID == 1 || TTPhieu_ID == 2);

            this.visiable.gcMap = true;
            this.visiable.colDonVIGiao = true;
            this.visiable.colLoaiHD = true;
            this.visiable.colSDT = true;
            this.visiable.colMaTB = true;
            this.$refs.v1_DanhSachHoSo.v1_dtDSHoSo = [];
        }

        this.$refs.v1_DanhSachHoSo.v1_dtDSHoSo = await this.LAY_DS_HOSO_CHO_LAP_HDPL(this.$root.token.getNhanVienID(), this.$root.token.getDonViID(), TTPhieu_ID, PhanLoaiHS_ID, 
                this.$refs.v1_DanhSachHoSo.model.v1_tu_ngay, this.$refs.v1_DanhSachHoSo.model.v1_den_ngay);

        if (this.$refs.v1_DanhSachHoSo.v1_dtDSHoSo.length == 0 && (PhanLoaiHS_ID == 2 || PhanLoaiHS_ID == 3)) {
            this.enable.laphdchuyendoi = false;
            this.enable.chuyenhd = false;
            this.enable.traphieu = false;
            this.enable.hoanthien = false;
        }

        if (this.$refs.v1_DanhSachHoSo.v1_dtDSHoSo.length == 0) {
            this.clearForm();
        }
    },
    clearForm() {
        this.visiable.gcMap = false;
        this.$refs.v2_ChiTietAnhHoSo.chonHoSo = false;
        this.$refs.v2_ChiTietAnhHoSo.v2_lstImg = [];
        this.$refs.v2_ChiTietAnhHoSo.v2_lstTitle = [];
        this.$refs.v2_ChiTietAnhHoSo.v2_lstType = [];
        this.setV2Img(document.getElementById("noImage").src);
        this.$refs.v2_ChiTietAnhHoSo.v2_xem_truoc = "";
        $('#lstImg').html("");
    },
    setV2Img(src) {
        this.$refs.v2_ChiTietAnhHoSo.setV2Img(src);
        this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_view = "3";
        this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = "";
    },
    async gridViewTiepNhan_FocusedRowChanged(row) {
        this.rowSelected = row;
        let PhanLoaiHS_ID = Number(this.$refs.v1_DanhSachHoSo.model.v1_phan_loai);
        let TTPhieu_ID = Number(this.$refs.v1_DanhSachHoSo.model.v1_trang_thai);
        this.LoaiHD_ID = row.loaihd_id;
        this.KhachHang_ID = row.khachhang_id;
        this.Hdtb_ID = row.hdtb_id;
        this.Ma_KH = row.ma_kh;
        this.PhieuID = row.phieu_id;
        this.Phieu_CHA_ID = row.phieu_cha_id;
        this.LoaiHD_ID = row.loaihd_id;
        console.log(row)

        try {            
            this.lap_hd = "";
            this.loaihd_nc = "";

            if (PhanLoaiHS_ID == 1) {
                try  {
                    this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = this.LoaiHD_ID;
                } catch (e) {
                    this.$toast.error("Phiếu chưa xác định loại hợp đồng");
                }

                this.tenKH = row.ten_kh;
                this.enable.chuyenhdpl = (this.LoaiHD_ID != 0 && TTPhieu_ID == 1);
                this.enable.traphieu = (this.LoaiHD_ID != 0 && TTPhieu_ID == 1);
                this.visiable.chuyenhd = false;
            } else if (PhanLoaiHS_ID == 2 || PhanLoaiHS_ID == 3) {
                this.textbtnLM = "Chuyển HĐ"
                this.visiable.chuyenhd = true;
                this.visiable.laphdchuyendoi = false;

                let diachi = row.vido + "," + row.kinhdo;
                if (diachi.length < 5) {
                    diachi = row.diachi;
                }

                this.$refs.v3_ViTriKhachHang.map.lat = Number(row.vido);
                this.$refs.v3_ViTriKhachHang.map.lng = Number(row.kinhdo);
                this.$refs.v3_ViTriKhachHang.map.dia_chi = row.diachi;

                this.tenKH = row.ten_kh;
                this.lap_hd = row.lap_hd;
                this.loaihd_nc = row.loaihd_nc;

                if (this.lap_hd == "0") {   //Không bắt buộc lập HĐ
                    this.visiable.chuyenhd = false;
                    this.visiable.hoanthien = true;
                } else if (this.lap_hd == "-1") {
                    this.visiable.laphdchuyendoi = true;
                    this.visiable.chuyenhd = true;
                    this.visiable.hoanthien = true;
                    this.textbtnLM = "Chuyển HĐ lắp mới"
                } else {
                    this.visiable.chuyenhd = true;
                }
            }

            try {
                this.$refs.v2_ChiTietAnhHoSo.chonHoSo = false;
                if (PhanLoaiHS_ID != 2) {                   
                    this.$refs.v2_ChiTietAnhHoSo.v2_lstImg = [];
                    this.setV2Img(document.getElementById("noImage").src);
                    this.$refs.v2_ChiTietAnhHoSo.v2_lstTitle = [];
                    this.$refs.v2_ChiTietAnhHoSo.v2_lstType = [];

                    var lstFromMoTa = this.GetImage(row.mota)
                    var ImageUrlList = lstFromMoTa.lstImg;
                    var ImageTitleList = lstFromMoTa.lstTitle;
                    var ImageTypeList = lstFromMoTa.lstType;           
                    if (ImageUrlList && ImageUrlList.length > 0) {
                        for (let inx in ImageUrlList) {
                            // let fullUrl = this.path + this.$root.token.getMaTinh() + "/" + ImageUrlList[inx];
                            let fullUrl = this.path + "&tinh=" + this.$root.token.getMaTinh() + "&ten_file=" + ImageUrlList[inx];
                            this.$refs.v2_ChiTietAnhHoSo.v2_lstImg.push(fullUrl);
                            this.$refs.v2_ChiTietAnhHoSo.v2_lstTitle.push(ImageTitleList[inx]);
                            this.$refs.v2_ChiTietAnhHoSo.v2_lstType.push(ImageTypeList[inx]);
                        }
                        this.setV2Img(this.$refs.v2_ChiTietAnhHoSo.v2_lstImg[0]);
                        this.$refs.v2_ChiTietAnhHoSo.v2_xem_truoc = "1/" + this.$refs.v2_ChiTietAnhHoSo.v2_lstImg.length;
                        if (this.$refs.v2_ChiTietAnhHoSo.v2_lstImg.length > 1) {
                            this.$refs.v2_ChiTietAnhHoSo.chonHoSo = true;
                        }
                        if (this.$refs.v2_ChiTietAnhHoSo.v2_lstType[0] == undefined || this.$refs.v2_ChiTietAnhHoSo.v2_lstType[0] == null) {
                            this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = "-1";
                        } else {
                            this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = this.$refs.v2_ChiTietAnhHoSo.v2_lstType[0];
                        }
                        let html = this.viewListImage(this.$refs.v2_ChiTietAnhHoSo.v2_lstImg, this.$refs.v2_ChiTietAnhHoSo.v2_lstTitle);
                        $('#lstImg').html(html);
                    } else {
                        this.setV2Img(document.getElementById("noImage").src);
                        this.$refs.v2_ChiTietAnhHoSo.v2_xem_truoc = "1/" + this.$refs.v2_ChiTietAnhHoSo.v2_lstImg.length;
                    }
                } else {  
                    $('#lstImg').html('');
                    let dt = await this.sp_lay_anh_hs(row.hdkh_id, row.hdtb_id);
                    if (dt) {
                        if (dt.length > 0) {                    
                            this.$refs.v2_ChiTietAnhHoSo.v2_lstImg = [];
                            this.setV2Img(document.getElementById("noImage").src);
                            this.$refs.v2_ChiTietAnhHoSo.v2_lstTitle = [];
                            this.$refs.v2_ChiTietAnhHoSo.v2_lstType = [];

                            for (let i=0; i<dt.length; i++) {
                                var lstFromMoTa = this.GetImage(dt[i].mota)
                                var ImageUrlList = lstFromMoTa.lstImg;
                                var ImageTitleList = lstFromMoTa.lstTitle;
                                var ImageTypeList = lstFromMoTa.lstType;                            
                                
                                if (ImageUrlList) {
                                    for (let inx in ImageUrlList) {
                                        // let fullUrl = this.path + this.$root.token.getMaTinh() + "/TVPHDICHVU/" + ImageUrlList[inx];
                                        let fullUrl = this.path + "&tinh=" + this.$root.token.getMaTinh() + "&ten_file=" + ImageUrlList[inx];
                                        this.$refs.v2_ChiTietAnhHoSo.v2_lstImg.push(fullUrl);
                                        this.$refs.v2_ChiTietAnhHoSo.v2_lstTitle.push(ImageTitleList[inx]);
                                        this.$refs.v2_ChiTietAnhHoSo.v2_lstType.push(ImageTypeList[inx]);
                                    }                         
                                }
                            }
                            if (this.$refs.v2_ChiTietAnhHoSo.v2_lstImg.length > 0) {
                                this.setV2Img(this.$refs.v2_ChiTietAnhHoSo.v2_lstImg[0]);
                                this.$refs.v2_ChiTietAnhHoSo.v2_xem_truoc = "1/" + this.$refs.v2_ChiTietAnhHoSo.v2_lstImg.length;
                                if (this.$refs.v2_ChiTietAnhHoSo.v2_lstImg.length > 1) {
                                    this.$refs.v2_ChiTietAnhHoSo.chonHoSo = true;
                                }
                                if (this.$refs.v2_ChiTietAnhHoSo.v2_lstType[0] == undefined || this.$refs.v2_ChiTietAnhHoSo.v2_lstType[0] == null) {
                                    this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = "-1";
                                } else {
                                    this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = this.$refs.v2_ChiTietAnhHoSo.v2_lstType[0];
                                }
                                let html = this.viewListImage(this.$refs.v2_ChiTietAnhHoSo.v2_lstImg, this.$refs.v2_ChiTietAnhHoSo.v2_lstTitle);
                                $('#lstImg').html(html);
                            } else {
                                this.setV2Img(document.getElementById("noImage").src);
                                this.$refs.v2_ChiTietAnhHoSo.v2_xem_truoc = "1/" + this.$refs.v2_ChiTietAnhHoSo.v2_lstImg.length;
                            } 
                        } else {
                            this.setV2Img(document.getElementById("noImage").src);
                            if (this.$refs.v2_ChiTietAnhHoSo.v2_LstLoaiFileHD.length > 0) {
                                this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = "-1";
                            } 
                        }
                    } else {
                        this.setV2Img(document.getElementById("noImage").src);
                        if (this.$refs.v2_ChiTietAnhHoSo.v2_LstLoaiFileHD.length > 0) {
                            this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = "-1";
                        }  
                    }
                }
            } catch (e) {
                this.setV2Img(document.getElementById("noImage").src);
                if (this.$refs.v2_ChiTietAnhHoSo.v2_LstLoaiFileHD.length > 0) {
                    this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = "-1";
                } 
            }      
        } catch (e) {
            console.log(e)
            this.setV2Img(document.getElementById("noImage").src);
            if (this.$refs.v2_ChiTietAnhHoSo.v2_LstLoaiFileHD.length > 0) {
                this.$refs.v2_ChiTietAnhHoSo.model.v2_loai_file_hd = "-1";
            }
            this.$refs.v3_ViTriKhachHang.lat = 0;
            this.$refs.v3_ViTriKhachHang.lng = 0;
            this.$refs.v3_ViTriKhachHang.dia_chi = "vị trí không xác định";
        }

        this.enable.chuyenhdpl = (this.LoaiHD_ID != 0 && TTPhieu_ID == 1);
        if (PhanLoaiHS_ID != 2) {
            this.enable.traphieu = (this.LoaiHD_ID != 0 && TTPhieu_ID == 1);
            this.enable.hoanthien = (this.LoaiHD_ID != 0 && TTPhieu_ID == 1);
        } else {
            this.enable.hoanthien = (this.LoaiHD_ID != 0 && TTPhieu_ID < 4);
        }
    },
    GetImage(xml) {
        let img = {};
        let lstImg = [], lstTitle = [], lstType = [];
        let parser = new DOMParser();
        let xmlDoc = parser.parseFromString(xml,"text/xml");
        let nodes = xmlDoc.getElementsByTagName("cabman_img_data")[0].childNodes
        for (let node of nodes) {    
            if (node.outerHTML != undefined) {
                xmlDoc = parser.parseFromString(node.outerHTML,"text/xml");

                let urlImg = xmlDoc.getElementsByTagName("img")[0].getAttribute("url")
                lstImg.push(urlImg);
                let titleImg = xmlDoc.getElementsByTagName("img")[0].getAttribute("title")
                lstTitle.push(titleImg);
                let typeImg = xmlDoc.getElementsByTagName("img")[0].getAttribute("type")
                lstType.push(typeImg);
            }
        }
        img.lstImg = lstImg;
        img.lstTitle = lstTitle;
        img.lstType = lstType;
        return img;
    },
    viewListImage(lstImage, lstTitle) {
        let html = "";
        for (let index in lstImage) {
            html += '<div class="text-desc marb10">' + lstTitle[index] + '</div>'
            html += '<div class="image-canvas marb10"><img src="' + lstImage[index] + '" alt="" style="width: 100px; height: 150px"></div>';
        }
        return html;
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
  .centered {
    position: fixed;
    left: 50%;
  }
    .cq-modal .breadcrumb-top {
        display: none;
    }

    .cq-modal .page-content {
        position: unset !important;
    }

    .cq-modal .main-wrapper {
        position: unset !important;
    }

    #popupComponentCQ .modal-xl {
        max-width: 95% !important;
    }

    #popupComponentLDM .modal-xl {
        max-width: 95% !important;
    }
</style>
