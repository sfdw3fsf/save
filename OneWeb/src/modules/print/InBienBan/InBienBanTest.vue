<template>
  <div class="box-form">
    <div class="legend-title">Danh sách tham số</div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">phanvung_id</div>
          <div class="value">
            <input type="text" ref="txtTinh" class="form-control highlight" v-model="txtTinh">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">phieu_id</div>
          <div class="value">
            <input type="text" ref="txtPhieuId" class="form-control highlight" v-model="txtPhieuId">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">Path</div>
          <div class="value">
            <input type="text" ref="txtPath" class="form-control highlight" v-model="txtPath">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">hdkh_id</div>
          <div class="value">
            <input type="text" ref="txtHdkhID" class="form-control highlight" v-model="txtHdkhId">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">hdtb_id</div>
          <div class="value">
            <input type="text" ref="txtHdtbId" class="form-control highlight" v-model="txtHdtbId">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">ma_tb</div>
          <div class="value">
            <input type="text" ref="txtMaTB" class="form-control highlight" v-model="txtMaTB">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">loaihd_id</div>
          <div class="value">
            <input type="text" ref="txtLoaiHD" class="form-control highlight" v-model="txtLoaiHD">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">donvi_id</div>
          <div class="value">
            <input type="text" ref="txtDonViQL" class="form-control highlight" v-model="txtDonViQL">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">loaitien_id</div>
          <div class="value">
            <input type="text" ref="txtLoaiTien" class="form-control highlight" v-model="txtLoaiTien">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">ma_gd</div>
          <div class="value">
            <input type="text" ref="txtMaGD" class="form-control highlight" v-model="txtMaGD">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">baohong_id</div>
          <div class="value">
            <input type="text" ref="txtBaoHong" class="form-control highlight" v-model="txtBaoHong">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">loaihoadon_id</div>
          <div class="value">
            <input type="text" ref="txtLoaiHoaDonID" class="form-control highlight" v-model="txtLoaiHoaDonID">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">seri_id</div>
          <div class="value">
            <input type="text" ref="txtSeriID" class="form-control highlight" v-model="txtSeriID">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">hoadon</div>
          <div class="value">
            <input type="text" ref="txtHoaDon" class="form-control highlight" v-model="txtHoaDon">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">fkey</div>
          <div class="value">
            <input type="text" ref="txtFkey" class="form-control highlight" v-model="txtFkey">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">thang_ld</div>
          <div class="value">
            <input type="text" ref="txtThangLD" class="form-control highlight" v-model="txtThangLD">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">vsuco_id</div>
          <div class="value">
            <input type="text" ref="txtSuCoId" class="form-control highlight" v-model="txtSuCoId">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">vgiaoviec_id</div>
          <div class="value">
            <input type="text" ref="txtGiaoViecId" class="form-control highlight" v-model="txtGiaoViecId">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">vnhanvien_id</div>
          <div class="value">
            <input type="text" ref="txtNhanVienId" class="form-control highlight" v-model="txtNhanVienId">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">quyettoan_id</div>
          <div class="value">
            <input type="text" ref="txtQuyetToanId" class="form-control highlight" v-model="txtQuyetToanId">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">thungan_id</div>
          <div class="value">
            <input type="text" ref="txtThuNganId" class="form-control highlight" v-model="txtThuNganId">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">ngay_tt</div>
          <div class="value">
            <input type="text" ref="txtNgayTT" class="form-control highlight" v-model="txtNgayTT">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">thang_tt</div>
          <div class="value">
            <input type="text" ref="txtThangTT" class="form-control highlight" v-model="txtThangTT">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">ma_tt</div>
          <div class="value">
            <input type="text" ref="txtMaTT" class="form-control highlight" v-model="txtMaTT">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">nguoi_cn</div>
          <div class="value">
            <input type="text" ref="txtNguoiCn" class="form-control highlight" v-model="txtNguoiCn">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">kieu</div>
          <div class="value">
            <input type="text" ref="txtKieu" class="form-control highlight" v-model="txtKieu">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">nhom_httt_id</div>
          <div class="value">
            <input type="text" ref="txtNhomHTTT" class="form-control highlight" v-model="txtNhomHTTT">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">httt_id</div>
          <div class="value">
            <input type="text" ref="txtHTTTID" class="form-control highlight" v-model="txtHTTTID">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">vdonviql_id</div>
          <div class="value">
            <input type="text" ref="txtDonViQL" class="form-control highlight" v-model="txtDonViQL">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">vnhanvienql_id</div>
          <div class="value">
            <input type="text" ref="txtNhanVienQLID" class="form-control highlight" v-model="txtNhanVienQLID">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">vtuyenthu_id</div>
          <div class="value">
            <input type="text" ref="txtTuyenThuID" class="form-control highlight" v-model="txtTuyenThuID">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">vdagan</div>
          <div class="value">
            <input type="text" ref="txtDaGan" class="form-control highlight" v-model="txtDaGan">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">thuebao_id</div>
          <div class="value">
            <input type="text" ref="txtThueBaoId" class="form-control highlight" v-model="txtThueBaoId">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">ds_phieu_id</div>
          <div class="value">
            <input type="text" ref="txtDsPhieuId" class="form-control highlight" v-model="txtDsPhieuId">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">baohanh_id</div>
          <div class="value">
            <input type="text" ref="txtBaoHanhID" class="form-control highlight" v-model="txtBaoHanhID">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">donvigiao</div>
          <div class="value">
            <input type="text" ref="txtDonViGiao" class="form-control highlight" v-model="txtDonViGiao">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">donvinhan</div>
          <div class="value">
            <input type="text" ref="txtDonViNhan" class="form-control highlight" v-model="txtDonViNhan">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">chungtu_id</div>
          <div class="value">
            <input type="text" ref="txtChungTuID" class="form-control highlight" v-model="txtChungTuID">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">nhanvien_giao_id</div>
          <div class="value">
            <input type="text" ref="txtNhanVienGiaoId" class="form-control highlight" v-model="txtNhanVienGiaoId">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">nhanvien_nhan_id</div>
          <div class="value">
            <input type="text" ref="txtNhanVienNhanId" class="form-control highlight" v-model="txtNhanVienNhanId">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">dsluotgiao_id</div>
          <div class="value">
            <input type="text" ref="txtDsLuotGiaoId" class="form-control highlight" v-model="txtDsLuotGiaoId">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">dsxln_id</div>
          <div class="value">
            <input type="text" ref="txtdsxln_id" class="form-control highlight" v-model="txtdsxln_id">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">buocxln_id</div>
          <div class="value">
            <input type="text" ref="txtbuocxln_id" class="form-control highlight" v-model="txtbuocxln_id">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">kycuoc</div>
          <div class="value">
            <input type="text" ref="txtkycuoc" class="form-control highlight" v-model="txtkycuoc">
          </div>
        </div>
      </b-col>
      <b-col>
        <div class="info-row">
          <div class="key w90">dskn_id</div>
          <div class="value">
            <input type="text" ref="txtdskn_id" class="form-control highlight" v-model="txtdskn_id">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">dsxulyno_id</div>
          <div class="value">
            <input type="text" ref="txtdsxulyno_id" class="form-control highlight" v-model="txtdsxulyno_id">
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col class="text-center pt-3 align-content-center">
        <a class="btn btn-primary m-2" @click="showPopup()">
          <span class="icon one-file-chart"></span> Xem báo cáo
        </a>
      </b-col>
    </div>
    <InBienBan ref="popupInBienBan" :params="params"/>
  </div>
</template>

<script>
import InBienBan from './InBienBan'

export default {
  name: 'InBienBanTest',
  components: {InBienBan},
  data () {
    return {
      params: {
        n_phieu_id: '',
        phanvung_id: '',
        path: ''
      },
      txtPhieuId: '',
      txtTinh: '',
      txtPath: '',
      txtHdkhId: '',
      txtHdtbId: '',
      txtMaTB: '',
      txtLoaiHD: '',
      txtDonViQL: '',
      txtLoaiTien: '',
      txtMaGD: '',
      txtBaoHong: '',
      txtLoaiHoaDonID: '',
      txtSeriID: '',
      txtHoaDon: '',
      txtFkey: '',
      txtThangLD: '',
      txtSuCoId: '',
      txtGiaoViecId: '',
      txtNhanVienId: '',
      txtQuyetToanId: '',
      txtThueBaoId: '',
      txtDsPhieuId: '',
      txtThuNganId: '',
      txtNgayTT: '',
      txtThangTT: '',
      txtMaTT: '',
      txtNguoiCn: '',
      txtKieu: '',
      txtNhomHTTT: '',
      txtHTTTID: '',
      txtDonViQLID: '',
      txtNhanVienQLID: '',
      txtTuyenThuID: '',
      txtDaGan: '',
      txtBaoHanhID: '',
      txtDonViGiao: '',
      txtDonViNhan: '',
      txtChungTuID: '',
      txtNhanVienGiaoId: '',
      txtNhanVienNhanId: '',
      txtDsLuotGiaoId: '',
      txtdsxln_id: '',
      txtbuocxln_id: '',
      txtkycuoc: '',
      txtdskn_id: '',
      txtdsxulyno_id: ''
    }
  },
  methods: {
    showPopup () {
      if (!this.txtPath) {
        this.$root.$toast.warning('Hãy nhập path báo cáo !')
        this.$refs.txtPath.focus()
      } else {
        this.params.n_phieu_id = this.txtPhieuId
        this.params.path = this.txtPath
        this.params.phanvung_id = this.txtTinh
        this.params.n_hdkh_id = this.txtHdkhId
        this.params.n_hdtb_id = this.txtHdtbId
        this.params.ma_tb = this.txtMaTB
        this.params.loaihd_id = this.txtLoaiHD
        this.params.donviql_id = this.txtDonViQL
        this.params.loaitien_id = this.txtLoaiTien
        this.params.magd = this.txtMaGD
        this.params.n_baohong_id = this.txtBaoHong
        this.params.seri_id = this.txtSeriID
        this.params.loaihoadon_id = this.txtLoaiHoaDonID
        this.params.hoadon = this.txtHoaDon
        this.params.fkey = this.txtFkey
        this.params.thang_ld = this.txtThangLD
        this.params.vsuco_id = this.txtSuCoId
        this.params.vgiaoviec_id = this.txtGiaoViecId
        this.params.vnhanvien_id = this.txtNhanVienId
        this.params.quyettoan_id = this.txtQuyetToanId
        this.params.thungan_id = this.txtThuNganId
        this.params.ngay_tt = this.txtNgayTT
        this.params.thang_tt = this.txtThangTT
        this.params.ma_tt = this.txtMaTT
        this.params.nguoi_cn = this.txtNguoiCn
        this.params.kieu = this.txtKieu
        this.params.nhom_httt_id = this.txtNhomHTTT
        this.params.httt_id = this.txtHTTTID
        this.params.vdonviql_id = this.txtDonViQLID
        this.params.vnhanvienql_id = this.txtNhanVienQLID
        this.params.vtuyenthu_id = this.txtTuyenThuID
        this.params.vdagan = this.txtDaGan
        this.params.thuebao_id = this.txtThueBaoId
        this.params.ds_phieu_id = this.txtDsPhieuId
        this.params.baohanh_id = this.txtBaoHanhID
        this.params.donvinhan = this.txtDonViNhan
        this.params.donvigiao = this.txtDonViGiao
        this.params.chungtu_id = this.txtChungTuID
        this.params.nhanvien_giao_id = this.txtNhanVienGiaoId
        this.params.nhanvien_nhan_id = this.txtNhanVienNhanId
        this.params.dsluotgiao_id = this.txtDsLuotGiaoId
        this.params.dsxln_id = this.txtdsxln_id
        this.params.buocxln_id = this.txtbuocxln_id
        this.params.kycuoc = this.txtkycuoc
        this.params.dskn_id = this.txtdskn_id
        this.dsxulyno_id = this.txtdsxulyno_id
        console.log(this.params)
        this.$refs.popupInBienBan.showModal()
      }
    }
  }
}
</script>

<style scoped>

</style>
