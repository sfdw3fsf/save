<template>
  <div>
  <div class="list-actions-top">
    <ul class="list">
      <li>
        <a @click="tsbtnCapNhat_Click">
          <span class="icon one-save"></span>Cập nhật
        </a>
      </li>
    </ul>

  </div>
  <div class="page-content">
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="box-form">
          <div class="legend-title">Thông tin dịch vụ tỉnh bán</div>
          <div class="info-row">
            <div class="key w90">&nbsp;</div>
            <div class="value">
              &nbsp;
            </div>

          </div>
          <div class="info-row">
            <div class="key w90">&nbsp;</div>
            <div class="value">
              &nbsp;
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Tốc độ</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboTocDoBan.SelectedValue"
                  :allowFiltering="true"
                  ref="cboTocDoBan"

                  :disabled="!cboTocDoBan.Enabled"
                  :dataSource="cboTocDoBan.list"
                  dataValueField="TOCDO_ID"
                  dataTextField="THUONGHIEU"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Tốc độ thực</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboTocDoThucBan.SelectedValue"
                  :allowFiltering="true"
                  ref="cboTocDoThucBan"
                  :disabled="!cboTocDoThucBan.Enabled"
                  :dataSource="cboTocDoThucBan.list"
                  dataValueField="tocdo_id"
                  dataTextField="thuonghieu"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div v-show="cboMucCuocBan.Visible" class="info-row">
            <div class="key w90">Mức cước</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboMucCuocBan.SelectedValue"
                  :allowFiltering="true"
                  ref="cboMucCuocBan"
                  :disabled="!cboMucCuocBan.Enabled"
                  :dataSource="cboMucCuocBan.list"
                  dataValueField="MUCCUOC_ID"
                  dataTextField="TENMUC"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div v-show="cboCongNgheBan.Visible" class="info-row">
            <div class="key w90">Công nghệ</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboCongNgheBan.SelectedValue"
                  :allowFiltering="true"
                  ref="cboCongNgheBan"
                  :disabled="!cboCongNgheBan.Enabled"
                  :dataSource="cboCongNgheBan.list"
                  dataValueField="TRANGBI_ID"
                  dataTextField="TENTRANGBI"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div v-show="cboKieuTraBan.Visible" class="info-row">
            <div class="key w90">Kiểu trả</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboKieuTraBan.SelectedValue"
                  :allowFiltering="true"
                  ref="cboKieuTraBan"
                  :disabled="!cboKieuTraBan.Enabled"
                  :dataSource="cboKieuTraBan.list"
                  dataValueField="KIEUTRA_ID"
                  dataTextField="TENKIEU"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Đối tượng</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboDoiTuongBan.SelectedValue"
                  :allowFiltering="true"
                  ref="cboDoiTuongBan"
                  :disabled="!cboDoiTuongBan.Enabled"
                  :dataSource="cboDoiTuongBan.list"
                  dataValueField="DOITUONG_ID"
                  dataTextField="TEN_DT"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Trang bị</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboTrangBiBan.SelectedValue"
                  :allowFiltering="true"
                  ref="cboTrangBiBan"
                  :disabled="!cboTrangBiBan.Enabled"
                  :dataSource="cboTrangBiBan.list"
                  dataValueField="TRANGBI_ID"
                  dataTextField="TENTRANGBI"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Thiết bị</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboThietBiDCBan.SelectedValue"
                  :allowFiltering="true"
                  ref="cboThietBiDCBan"
                  :disabled="!cboThietBiDCBan.Enabled"
                  :dataSource="cboThietBiDCBan.list"
                  dataValueField="thietbidc_id"
                  dataTextField="ten_tbi"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Mức cước TB</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboMucCuocTBBan.SelectedValue"
                  :allowFiltering="true"
                  ref="cboMucCuocTBBan"
                  :disabled="!cboMucCuocTBBan.Enabled"
                  :dataSource="cboMucCuocTBBan.list"
                  dataValueField="mucuoctb_id"
                  dataTextField="tenmuccuoc"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Chủ quản</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboChuQuan_Ban.SelectedValue"
                  :allowFiltering="true"
                  ref="cboChuQuan_Ban"
                  :disabled="!cboChuQuan_Ban.Enabled"
                  :dataSource="cboChuQuan_Ban.list"
                  dataValueField="CHUQUAN_ID"
                  dataTextField="TENCHUQUAN"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Đơn vị TC</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboChuQuanLD_Ban.SelectedValue"
                  :allowFiltering="true"
                  ref="cboChuQuanLD_Ban"
                  :disabled="!cboChuQuanLD_Ban.Enabled"
                  :dataSource="cboChuQuanLD_Ban.list"
                  dataValueField="CHUQUAN_ID"
                  dataTextField="TENCHUQUAN"
                >
                </SelectExt>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="box-form">
          <div class="legend-title">Thông tin dịch vụ tỉnh thi công</div>
          <div class="info-row">
            <div class="key w90 nowrap">
              Kiểu LD
            </div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboKieuLDTC.SelectedValue"
                  :allowFiltering="true"
                  ref="cboKieuLDTC"

                  :disabled="!cboKieuLDTC.Enabled"
                  :dataSource="cboKieuLDTC.list"
                  dataValueField="kieuld_id"
                  dataTextField="ten_kieuld"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90 nowrap">
              Mã TN
            </div>
            <div class="value">
              <input type="text" v-model="txtMATNTC" class="form-control">
            </div>
          </div>
          <div class="info-row">

              <div class="key w90 nowrap">
                <div class="check-action" v-show="chkTocDoTC.Visible">
                  <input type="checkbox" v-model="chkTocDoTC.Checked" @click="chkTocDoTC_CheckedChanged"   class="check">
                  <span class="name">Tốc độ</span>

                </div>
                <span class="name" v-show="!chkTocDoTC.Visible">Tốc độ</span>
              </div>

              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    v-model="cboTocDoTC.SelectedValue"
                    :allowFiltering="true"
                    ref="cboTocDoTC"
                    :disabled="!cboTocDoTC.Enabled"
                    :dataSource="cboTocDoTC.list"
                    dataValueField="tocdo_id"
                    dataTextField="thuonghieu"
                  >
                  </SelectExt>
                </div>
              </div>

          </div>
          <div class="info-row">
            <div class="key w90">Tốc độ thực</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboTocDoThucTC.SelectedValue"
                  :allowFiltering="true"
                  ref="cboTocDoThucTC"
                  :disabled="!cboTocDoThucTC.Enabled"
                  :dataSource="cboTocDoThucTC.list"
                  dataValueField="tocdo_id"
                  dataTextField="thuonghieu"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div v-show="cboMucCuocTC.Visible" class="info-row">
            <div class="key w90">Mức cước</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboMucCuocTC.SelectedValue"
                  :allowFiltering="true"
                  ref="cboMucCuocTC"
                  :disabled="!cboMucCuocTC.Enabled"
                  :dataSource="cboMucCuocTC.list"
                  dataValueField="MUCCUOC_ID"
                  dataTextField="TENMUC"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div v-show="cboCongNgheTC.Visible" class="info-row">
            <div class="key w90">Công nghệ</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboCongNgheTC.SelectedValue"
                  :allowFiltering="true"
                  ref="cboCongNgheTC"
                  :disabled="!cboCongNgheTC.Enabled"
                  :dataSource="cboCongNgheTC.list"
                  dataValueField="TRANGBI_ID"
                  dataTextField="TENTRANGBI"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div v-show="cboKieuTraTC.Visible" class="info-row">
            <div class="key w90">Kiểu trả</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboKieuTraTC.SelectedValue"
                  :allowFiltering="true"
                  ref="cboKieuTraTC"
                  :disabled="!cboKieuTraTC.Enabled"
                  :dataSource="cboKieuTraTC.list"
                  dataValueField="KIEUTRA_ID"
                  dataTextField="TENKIEU"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Đối tượng</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboDoiTuongTC.SelectedValue"
                  :allowFiltering="true"
                  ref="cboDoiTuongTC"
                  :disabled="!cboDoiTuongTC.Enabled"
                  :dataSource="cboDoiTuongTC.list"
                  dataValueField="doituong_id"
                  dataTextField="ten_dt"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Trang bị</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboTrangBiTC.SelectedValue"
                  :allowFiltering="true"
                  ref="cboTrangBiTC"
                  :disabled="!cboTrangBiTC.Enabled"
                  :dataSource="cboTrangBiTC.list"
                  dataValueField="trangbi_id"
                  dataTextField="tentrangbi"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Thiết bị</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboThietBiDCTC.SelectedValue"
                  :allowFiltering="true"
                  ref="cboThietBiDCTC"
                  :disabled="!cboThietBiDCTC.Enabled"
                  :dataSource="cboThietBiDCTC.list"
                  dataValueField="thietbidc_id"
                  dataTextField="ten_tbi"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Mức cước TB</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="btnMucCuocTB_Click">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <div class="select-custom">
                  <SelectExt
                    v-model="cboMucCuocTBTC.SelectedValue"
                    :allowFiltering="true"
                    ref="cboMucCuocTBTC"
                    :disabled="!cboMucCuocTBTC.Enabled"
                    :dataSource="cboMucCuocTBTC.list"
                    dataValueField="mucuoctb_id"
                    dataTextField="tenmuccuoc"
                  >
                  </SelectExt>
                </div>
              </div>

            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Chủ quản</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboChuQuan_TC.SelectedValue"
                  :allowFiltering="true"
                  ref="cboChuQuan_TC"
                  :disabled="!cboChuQuan_TC.Enabled"
                  :dataSource="cboChuQuan_TC.list"
                  dataValueField="CHUQUAN_ID"
                  dataTextField="TENCHUQUAN"
                >
                </SelectExt>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w90">Đơn vị TC</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt
                  v-model="cboChuQuanLD_TC.SelectedValue"
                  :allowFiltering="true"
                  ref="cboChuQuanLD_TC"
                  :disabled="!cboChuQuanLD_TC.Enabled"
                  :dataSource="cboChuQuanLD_TC.list"
                  dataValueField="CHUQUAN_ID"
                  dataTextField="TENCHUQUAN"
                >
                </SelectExt>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
    <ejs-dialog :enableResize='true' :visible='false' ref='DSTBCungDoiCapBCDialog' :position='position'
                :header='"Chọn mã truy nhập"' showCloseIcon='true' :target='targetTop' width='90%' height="800">
      <DSTBCungDoiCapBC ref='DSTBCungDoiCapBCPopup' :khachhang_id="khachhang_id"
                        :tinhtc="tinhtc" :kieuld_id="cboKieuLDTC.SelectedValue" @accept="acceptDSTBCungDoiCapBC" />

    </ejs-dialog>
    <ejs-dialog :enableResize='true' :visible='false' ref='MucCuocThueBaoDialog' :position='position'
                :header='"MỨC CƯỚC THUÊ BAO"' showCloseIcon='true' :target='targetTop' width='70%' height="800">
      <MucCuocThueBao ref='MucCuocThueBaoPopup' :tinhtc="tinhtc" :vtocdo_id="vtocdotc_id" @accept="acceptMucCuocThueBao" />

    </ejs-dialog>
    <ejs-dialog :enableResize='true' :visible='false' ref='DSTBLapKemBCDialog' :position='position'
                :header='"Chọn mã truy nhập"' showCloseIcon='true' :target='targetTop' width='90%' height="800">
      <DSTBLapKemBC ref='DSTBLapKemBCPopup' :hdkh_id="hdkh_id"
                    :vkieuld_id="thongso_tc.kieuld_id" @accept="acceptDSTBLapKemBC" />

    </ejs-dialog>
  </div>
</template>

<script>
import api from './api'
import '@/assets/vendor/jquery/split'
import Vue from 'vue'
import moment from 'moment'
import DSTBCungDoiCapBC from '../DSTBCungDoiCapBC/DSTBCungDoiCapBC'
import DSTBLapKemBC from '../DSTBLapKemBC/DSTBLapKemBC'
import MucCuocThueBao from '../MucCuocThueBao/MucCuocThueBao.vue'
import {DialogPlugin} from '@syncfusion/ej2-vue-popups'
import {LoaiHopDong, DichVuVienThong, TrangThaiHD, LOAI_DV, TrangThaiTB, LoaiHinhTB} from '@/const/enums'
Vue.use(DialogPlugin)
export default {
  name: "TocDoMucCuoc",
  components: {
    LoaiHopDong, DichVuVienThong, TrangThaiHD, LOAI_DV, TrangThaiTB, LoaiHinhTB,DSTBCungDoiCapBC,DSTBLapKemBC,MucCuocThueBao
  },
  props:{
    thongso_ban:{
      type:Object,
      default:{
        'tocdo_id': 0,
        'tocdothuc_id': 0,
        'tocdo_pir_id': 0,
        'tocdo_nix': 0,
        'tocdo_isp': 0,
        'chuquan_id': 0,
        'chuquanld_id': 0,
        'congnghe_id': 0,
        'muccuoc_id': 0,
        'kieutra_id': 0,
        'doituong_id': 0,
        'muccuoctb_id': 0,
        'trangbi_id': 0,
        'kieuld_id': 0,
        'thietbidc_id': 0,
        'loaimd_id': 0,
        'loaikenh_id': 0,
        'thietbidc_id_cuoi': 0,
        'thoihan_id': 0,
        'ma_tn': '',
        'ma_doicap': '',
        'chuquanld_id_cuoi': 0,
        'phanloai_id': 0,
        'phantach_id': 0,
        'mhkn_id': 0,
        'gdkn_id': 0,
        'yc_baove': 0,
        'tocdo_pop': 0,
        'gdkn_pop_id': 0,
        'gdkn_kh_id': 0,
        'cauhinh_dt_id': 0,
        'kieu_duplex_id': 0,
        'kieukn_id': 0,
        'mtu_size': '',
        'auto_nego': '',
        'sncp': '',
        'cap_chinh': '',
        'cap_duphong': '',
        'diem_pop': '',
        'wan_ip': '',
        'lan_ip': '',
        'ce_ip': '',
        'tt_ip': '',
        'sl_ip': '',
        'ten_trunk_nni': '',
        'so_as': '',
        'hqos': '',
        'cos': '',
        'kieu_nghiemthu': [],
        'svlan': '',
        'cvlan': '',
        'vlan_transparent': '',
        'tt_port': '',
        'loai_ip': [],
        'thongso_cntt': [],
      }
    },
    thongso_tc:{
      type:Object,
      default:{
        'tocdo_id': 0,
        'tocdothuc_id': 0,
        'tocdo_pir_id': 0,
        'tocdo_nix': 0,
        'tocdo_isp': 0,
        'chuquan_id': 0,
        'chuquanld_id': 0,
        'congnghe_id': 0,
        'muccuoc_id': 0,
        'kieutra_id': 0,
        'doituong_id': 0,
        'muccuoctb_id': 0,
        'trangbi_id': 0,
        'kieuld_id': 0,
        'thietbidc_id': 0,
        'loaimd_id': 0,
        'loaikenh_id': 0,
        'thietbidc_id_cuoi': 0,
        'thoihan_id': 0,
        'ma_tn': '',
        'ma_doicap': '',
        'chuquanld_id_cuoi': 0,
        'phanloai_id': 0,
        'phantach_id': 0,
        'mhkn_id': 0,
        'gdkn_id': 0,
        'yc_baove': 0,
        'tocdo_pop': 0,
        'gdkn_pop_id': 0,
        'gdkn_kh_id': 0,
        'cauhinh_dt_id': 0,
        'kieu_duplex_id': 0,
        'kieukn_id': 0,
        'mtu_size': '',
        'auto_nego': '',
        'sncp': '',
        'cap_chinh': '',
        'cap_duphong': '',
        'diem_pop': '',
        'wan_ip': '',
        'lan_ip': '',
        'ce_ip': '',
        'tt_ip': '',
        'sl_ip': '',
        'ten_trunk_nni': '',
        'so_as': '',
        'hqos': '',
        'cos': '',
        'kieu_nghiemthu': [],
        'svlan': '',
        'cvlan': '',
        'vlan_transparent': '',
        'tt_port': '',
        'loai_ip': [],
        'thongso_cntt': [],
      }
    },
    _ploaitb_ld:{
      type:Number,
      default:0,
    },
    _ploaitb_td:{
      type:Number,
      default:0,
    },
    tinhtc:{
      type:Number,
      default:0,
    },
    _vloaihd:{
      type:Number,
      default:0,
    },
    thuebao_id_tc:{
      type:Number,
      default:0
    },
    thuebao_id:{
      type:Number,
      default:0
    },

  },
  watch:{
    cboTocDoBan:{
      handler:async function () {
        let response = await api.ht_tocdo_thuc_adsl_theo_tocdo_id(this.axios,{
          "tinhthicong_id": this.tinhtc,
          "tocdo_id": this.cboTocDoBan.SelectedValue
        })
        if(response.data.error_code === 'BSS-00000000' && response.data.data){
          this.cboTocDoThucBan.list = response.data.data;
          this.cboTocDoThucBan.SelectedValue = this.cboTocDoThucBan.list[0].tocdo_id
        }

        this.cboTocDoThucBan.SelectedValue = this.cboTocDoBan.SelectedValue;
 
        if (!this.kt_load)
         await this.Lay_DS_MucCuoc_TB_Ban(this.pLoaihinhTB, this.cboDoiTuongBan.SelectedValue, moment().format("DD/MM/YYYY"), 3);
        if (this.chkTocDoTC.Checked == true){
          let maTocDoTinhBan = "";
          if(this.cboTocDoBan.list.filter(x=>x.TOCDO_ID == this.cboTocDoBan.SelectedValue).length > 0){
            maTocDoTinhBan = this.cboTocDoBan.list.filter(x=>x.TOCDO_ID == this.cboTocDoBan.SelectedValue)[0].MATOCDO;
          }

          try{
            if (this._vloaihd == LoaiHopDong.CHUYEN_QUYEN || this._vloaihd == LoaiHopDong.DI_CHUYEN || this._vloaihd == LoaiHopDong.KHOIPHUC_SD)

            {
              let response1 = await api.laydmtocdotinhtctheomatocdo_dc(this.axios, {
                "tinhthicong_id": this.tinhtc,
                "loaitb_id": this._ploaitb_td,
                "maTocDo": maTocDoTinhBan
              })
              if(response1.data.error_code === 'BSS-00000000' && response1.data.data){
                this.cboTocDoTC.list = response1.data.data;
                this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id;
                console.log("1: ", this.cboTocDoTC.SelectedValue);
              }
            }
            else{
              let response2 = await api.laydmtocdotinhtctheomatocdo(this.axios, {
                "tinhthicong_id": this.tinhtc,
                "loaitb_id": this._ploaitb_td,
                "maTocDo": maTocDoTinhBan
              })
              if(response2.data.error_code === 'BSS-00000000' && response2.data.data){
                this.cboTocDoTC.list = response2.data.data;
                this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id;
                console.log("2: ", this.cboTocDoTC.SelectedValue);
              }
            }
          }
          catch (e){
            console.log(e);
            this.cboTocDoTC.list= [];
          }
          try{
            if (this.thongso_tc.tocdo_id == 0)
              this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id;
            else
            {
              this.cboTocDoTC.SelectedValue = this.thongso_tc.tocdo_id;
              if (this.cboTocDoTC.list.length > 0)
                this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id;
            }
            console.log("3: ", this.cboTocDoTC.SelectedValue);
          }
          catch (e){
            this.$toast.error("Đơn vị thi công không có tốc độ này. Vui lòng chọn một tốc độ khác.")
          }
        }
      },
      deep: true

    },
    cboDoiTuongBan:{
      handler:async function () {
        if(!this.kt_load){
         await this.Lay_DS_MucCuoc_TB_Ban(this.pLoaihinhTB,this.cboDoiTuongBan.SelectedValue, moment().format("DD/MM/YYYY"),this.cboTrangBiBan.SelectedValue)
        }
      },
      deep: true

    },
    cboKieuTraBan:{
      handler:async function () {
        if(this.cboKieuTraBan.SelectedValue != null){
          this.cboKieuTraTC.SelectedValue = this.cboKieuTraBan.SelectedValue;
        }
      },
      deep: true

    },
    cboMucCuocBan:{
      handler:async function () {
        if(this.cboMucCuocBan.SelectedValue != null){
          this.cboMucCuocTC.SelectedValue = this.cboMucCuocBan.SelectedValue;
        }
      },
      deep: true

    },
    cboTocDoTC:{
      handler:async function () {
        if (this.cboTocDoTC.list.length > 0)
        {
          if (this.cboTocDoTC.SelectedValue == null) return;

          let response = await api.ht_tocdo_thuc_adsl_theo_tocdo_id(this.axios,{
            "tinhthicong_id": this.tinhtc,
            "tocdo_id": this.cboTocDoTC.SelectedValue
          })
          if(response.data.error_code === 'BSS-00000000' && response.data.data){
            this.cboTocDoThucTC.list = response.data.data;
            this.cboTocDoThucTC.SelectedValue = this.cboTocDoThucTC.list[0].tocdo_id
          }
          this.cboTocDoThucTC.SelectedValue = this.cboTocDoTC.SelectedValue;
          if (!this.kt_load)
           await this.Lay_DS_MucCuoc_TB_TC(this.pLoaihinhTB, this.cboDoiTuongTC.SelectedValue, moment().format("DD/MM/YYYY"), this.cboTrangBiTC.SelectedValue);
          
        }
      },
      deep: true

    },
    cboTrangBiBan:{
      handler:async function () {
        this.cboTrangBiTC.SelectedValue = this.cboTrangBiBan.SelectedValue;
        if (!this.kt_load)
         await this.Lay_DS_MucCuoc_TB_Ban(this.pLoaihinhTB, this.cboDoiTuongBan.SelectedValue, moment().format("DD/MM/YYYY"), this.cboTrangBiBan.SelectedValue);
      },
      deep: true

    },

    cboKieuLDTC:{
      handler:async function () {
        if (!this.kt_load)
        {
          this.layMaTN_CD();
        }
      },
      deep: true

    },
    cboDoiTuongTC:{
      handler:async function () {
        if (!this.kt_load)
        {
          await this.Lay_DS_MucCuoc_TB_TC(this.pLoaihinhTB, this.cboDoiTuongTC.SelectedValue, moment().format("DD/MM/YYYY"), this.cboTrangBiTC.SelectedValue);
        }
      },
      deep: true

    },
    cboTrangBiTC:{
      handler:async function () {
        if (!this.kt_load)
        {
          await this.Lay_DS_MucCuoc_TB_TC(this.pLoaihinhTB, this.cboDoiTuongTC.SelectedValue, moment().format("DD/MM/YYYY"), this.cboTrangBiTC.SelectedValue);
        }
      },
      deep: true

    },
    cboChuQuanLD_Ban:{
      handler:async function () {
        if (this.cboChuQuanLD_Ban.list.length > 0)
        {
          if (this.cboChuQuanLD_Ban.SelectedValue == null) return;

          this.cboChuQuanLD_TC.SelectedValue = this.cboChuQuanLD_Ban.SelectedValue;
        }
      },
      deep: true

    },


  },
  data: function () {
    return {
      position: {X: 'center', Y: 'top'},
      targetTop: '#app .main-wrapper',
      KieuLapDat: {
        THAYDOI_CONGNGHE_TOCDO_AON_GPON: 885,
        THAYDOI_MUCCUOC_ADSL_TK: 13081,
        THAYDOI_KIEUTRA_MYTV: 270,
        THAYDOI_TOCDO_ADSL_TK: 13080,
        THAYDOI_TOCDO_MUCCUOC_ADSL_TK: 13082,
        THAYDOI_CONGNGHE_TOCDO_GPON_AON: 886,
        THAYDOI_TOCDOTHUC_ADSL: 655,
        DOIGOI_FSECURE: 668,
      },
      MUCCUOC: {
        TRON_GOI: 1,
        LUU_LUONG: 2,
      },
      KieuTra: {
        TRATRUOC: 1,
        TRASAU: 2,
      },
      tinh_chuquan_id:-1,
      khachhang_id: 0,
      hdkh_id: 0,
      dichvuvt_id : 0,
      pLoaihinhTB:0,
      diachi_ban :{
        diachi_id:0,
        diachi:0,
        quan_id:0,
        phuong_id:0,
        pho_id:0,
        ap_id:0,
        khu_id:0,
        dacdiem_id:0,
      },
      diachi_thicong:{
        diachi_id:0,
        diachi:0,
        quan_id:0,
        phuong_id:0,
        pho_id:0,
        ap_id:0,
        khu_id:0,
        dacdiem_id:0,
      },
      kt_load : true,

      cboTocDoBan:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboChuQuanLD_Ban:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },

      cboMucCuocBan:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboChuQuan_Ban:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboCongNgheBan:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboThietBiDCBan:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboTrangBiBan:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboKieuTraBan:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboTocDoThucBan:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboDoiTuongBan:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboMucCuocTBBan:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      btnMucCuocTB:{
        Enabled:true,
      },
      chkTocDoTC:{
        Checked:false,
        Enabled:true,
        Visible:false,
      },
      txtMATNTC:"",
      cboChuQuanLD_TC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboChuQuan_TC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboThietBiDCTC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboKieuLDTC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboTrangBiTC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboKieuTraTC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboCongNgheTC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboMucCuocTC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboTocDoThucTC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboDoiTuongTC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboMucCuocTBTC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
      cboTocDoTC:{
        list:[],
        SelectedValue:null,
        Enabled:true,
        Visible:true,
      },
    }
  },
  computed:{
    vtocdotc_id(){
      return this.cboTocDoTC.SelectedValue?null:0,Number(this.cboTocDoTC.SelectedValue);
    }
  },
  methods: {
    async frmTocDoMucCuoc_Load(){
      console.log(this.thongso_ban);
      console.log(this.thongso_tc);
      let dsLoaiHinhLocTocDo = ",11,";
      if (dsLoaiHinhLocTocDo.indexOf("," + this._ploaitb_td + ",") > 1)
        this.chkTocDoTC.Visible = true;
      await this.ht_kieu_ld_combobox();
      await this.get_map_id();
      this.pLoaihinhTB = this._ploaitb_td;
      this.cboTocDoThucBan.Enabled = false;
      this.cboTocDoThucTC.Enabled = false;
      this.cboKieuTraTC.Enabled = false;
      this.cboMucCuocTC.Enabled = false;


      this.cboKieuTraBan.Enabled = true;
      this.cboCongNgheBan.Visible = false;
      this.cboCongNgheTC.Visible = false;

      await this.formload_frmtocdomuccuoc();

      if(this._vloaihd == LoaiHopDong.DAT_MOI)
        await this.load_dm_tocdo_adsl();
      else
        await this.load_dm_tocdo_adsl_dc();
      await this.ht_loai_tb_lhtb_combobox(1);
      await this.ht_loai_tb_lhtb_combobox(2);
      await this.load_dm_doi_tuong();
      await this.load_dm_trang_bi();
      if (this.thongso_ban.congnghe_id > 0)
        this.cboCongNgheBan.SelectedValue = this.thongso_ban.congnghe_id;

      if (this.thongso_tc.congnghe_id > 0)
        this.cboCongNgheTC.SelectedValue = this.thongso_tc.congnghe_id;

      let diachiban = this.Diachi_null();
      this.diachi_ban = {...diachiban};
      if (this.thuebao_id_tc != 0)
        await this.lay_diachi_dbtb_bancheo();
      else{
        let diachitc = this.Diachi_null();
        this.diachi_thicong = {...diachitc}
      }
      if (this.thongso_ban.kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.thongso_ban.kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON)
      {
        this.cboCongNgheBan.Visible = true;
        this.cboCongNgheTC.Visible = true;
        this.cboMucCuocBan.Visible = false;
        this.cboMucCuocTC.Visible = false;
        this.cboKieuTraBan.Visible = false;
        this.cboKieuTraTC.Visible = false;
        this.cboKieuTraBan.Enabled = false;
        this.cboCongNgheBan.Enabled = true;
        this.cboCongNgheTC.Enabled = true;
        this.cboTocDoBan.Enabled = false;
        this.cboTocDoTC.Enabled = false;
        this.cboMucCuocBan.Enabled = false;
      }
      else
      {
        if (this._ploaitb_td == LoaiHinhTB.INTERNET_MYTV)
        {
          this.cboMucCuocBan.Visible = false;
          this.cboMucCuocTC.Visible = false;
          this.cboCongNgheBan.Visible = false;
          this.cboCongNgheTC.Visible = false;
          this.cboKieuTraBan.Visible = true;
          this.cboKieuTraTC.Visible = true;
        }
        else
        {
          this.cboCongNgheBan.Visible = false;
          this.cboCongNgheTC.Visible = false;
          this.cboMucCuocBan.Visible = true;
          this.cboMucCuocTC.Visible = true;
          this.cboKieuTraBan.Visible = false;
          this.cboKieuTraTC.Visible = false;
        }
      }
      if (this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_MUCCUOC_ADSL)
      {
        this.cboTocDoBan.Enabled = false;
        this.cboTocDoTC.Enabled = false;
        this.cboTocDoBan.EditValue = this.thongso_ban.tocdo_id;
        this.cboTocDoTC.SelectedValue = this.thongso_tc.tocdo_id;
        console.log("4: ", this.cboTocDoTC.SelectedValue);
      }

      if (this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_KIEUTRA_MYTV)
      {
        this.cboTocDoBan.Enabled = false;
        this.cboCongNgheBan.Enabled = false;
        this.cboTocDoTC.Enabled = false;
        this.cboCongNgheTC.Enabled = false;

        if (this.thongso_ban.kieutra_id == this.KieuTra.TRASAU)
          this.cboKieuTraBan.SelectedValue = this.KieuTra.TRATRUOC;
        else
          this.cboKieuTraBan.SelectedValue = this.KieuTra.TRASAU;
      }
      if (this.thongso_ban.kieutra_id == 0)
        this.cboKieuTraBan.SelectedValue = 2;
      else
        this.cboKieuTraBan.SelectedValue = this.thongso_ban.kieutra_id;
      if (this.thongso_ban.kieuld_id == this.KieuLapDat.THAYDOI_TOCDO_ADSL || this.thongso_ban.kieuld_id == this.KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL ||
        this.thongso_ban.kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.thongso_ban.kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON)
      {
        this.cboTocDoBan.Enabled = true;
        this.cboTocDoTC.Enabled = true;
        this.cboMucCuocBan.Enabled = false;
        this.cboKieuTraBan.Enabled = false;
      }
      if (this.thongso_ban.kieuld_id == this.KieuLapDat.THAYDOI_MUCCUOC_ADSL || this.thongso_ban.kieuld_id == this.KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL)
      {
        if (this.thongso_ban.muccuoc_id == this.MUCCUOC.LUU_LUONG)
          this.cboMucCuocBan.SelectedValue = this.MUCCUOC.TRON_GOI;
        this.cboMucCuocTC.SelectedValue = this.MUCCUOC.TRON_GOI;
        if (this.thongso_ban.muccuoc_id == this.MUCCUOC.TRON_GOI)
          this.cboMucCuocBan.SelectedValue = this.MUCCUOC.LUU_LUONG;
        this. cboMucCuocTC.SelectedValue = this.MUCCUOC.LUU_LUONG;
      }
      // end hoang them

      if (this.thongso_ban.kieuld_id == this.KieuLapDat.THAYDOI_TOCDOTHUC_ADSL)
      {
        this.cboTocDoBan.Enabled = false;
        this.cboTocDoTC.Enabled = false;
        this.cboMucCuocBan.Enabled = false;

        this.cboMucCuocTBBan.Enabled = false;
        this.cboMucCuocTBTC.Enabled = false;
        this.cboCongNgheBan.Enabled = false;
        this.cboCongNgheTC.Enabled = false;
        this.cboTocDoThucBan.Enabled = true;
        this.cboTocDoThucTC.Enabled = true;
        this.cboTocDoThucBan.SelectedValue = -1;
        this.cboTocDoThucTC.SelectedValue = -1;
        this.cboKieuTraBan.Enabled = false;
      }

      if (this.thongso_ban.kieuld_id == this.KieuLapDat.DOIGOI_FSECURE)
      {
        this.cboMucCuocBan.Enabled = false;
        this.cboCongNgheBan.Enabled = false;
        this.cboCongNgheTC.Enabled = false;
        this.cboKieuTraBan.Enabled = false;
        this.cboMucCuocBan.SelectedValue = this.thongso_ban.muccuoc_id;
      }

      if (this.thongso_ban.tocdo_id != 0)
      {
        if (this._ploaitb_td == LoaiHinhTB.INTERNET_MYTV && this.thongso_ban.kieuld_id != this.KieuLapDat.THAYDOI_KIEUTRA_MYTV)
        {
          this.cboTocDoBan.SelectedValue = 0;
          this.cboTocDoTC.SelectedValue = this.this.cboTocDoTC.list[0].tocdo_id;
        }
        else
        {
          this.cboTocDoBan.SelectedValue = this.thongso_ban.tocdo_id;
          this.cboTocDoTC.SelectedValue = this.thongso_tc.tocdo_id;
        }
        console.log("5: ", this.cboTocDoTC.SelectedValue);
      }

      await this.Lay_DS_MucCuoc_TB_Ban(this.pLoaihinhTB,this.thongso_ban.doituong_id,moment().format("DD/MM/YYYY"), this.thongso_ban.trangbi_id);
      await this.Lay_DS_MucCuoc_TB_TC(this.pLoaihinhTB,this.thongso_tc.doituong_id,moment().format("DD/MM/YYYY"), this.thongso_tc.trangbi_id)

      if (this.thongso_ban.muccuoctb_id > 0)
        this.cboMucCuocTBBan.SelectedValue = this.thongso_ban.muccuoctb_id;
      if (this.thongso_tc.muccuoctb_id > 0)
        this.cboMucCuocTBTC.SelectedValue = this.thongso_tc.muccuoctb_id;

      this.kt_load = false;

      if (this._vloaihd == LoaiHopDong.KHOIPHUC_SD)
        this.thongso_khoiphuc(this.dichvuvt_id);
      if (this._vloaihd == LoaiHopDong.CHUYEN_QUYEN || this._vloaihd == LoaiHopDong.DI_CHUYEN)
      {
        this.cboTocDoBan.Enabled = false;
        this.cboTocDoThucBan.Enabled = false;
        this.cboThietBiDCBan.Enabled = false;
        this.cboCongNgheBan.Enabled = false;
        this.cboDoiTuongBan.Enabled = false;
        this.cboKieuTraBan.Enabled = false;
        this.cboMucCuocBan.Enabled = false;
        this.cboMucCuocTBBan.Enabled = false;


        this.cboTocDoTC.Enabled = false;
        this.cboTocDoThucTC.Enabled = false;
        this.cboThietBiDCTC.Enabled = false;
        this.cboCongNgheTC.Enabled = false;
        this.cboDoiTuongTC.Enabled = false;
        this.cboKieuTraTC.Enabled = false;
        this.cboMucCuocTC.Enabled = false;
        this.cboMucCuocTBTC.Enabled = false;
      }

      if (this._vloaihd == LoaiHopDong.CHUYEN_QUYEN || this._vloaihd == LoaiHopDong.DI_CHUYEN)
      {
        this.cboThietBiDCTC.Enabled = true;
        //  bangts_tc.Load_DM_TocDo_Adsl_DC(tinhtc, cboTocDoTC, _ploaitb_td);
      }
      //anhnt 11/02/2020 thêm theo yc của khdn
      this.chkTocDoTC.Checked = true;
      this.chkTocDoTC.Visible = false;



    },
    acceptDSTBCungDoiCapBC(data){
      this.$refs.DSTBCungDoiCapBCDialog.hide();
      this.thongso_tc.ma_tn = data.ma_tn;
      this.thongso_tc.ma_doicap = data.ma_doicap;
      this.txtMATNTC = data.ma_tn;
    },
    acceptDSTBLapKemBC(data){
      this.$refs.DSTBLapKemBC.hide();
      this.thongso_tc.ma_tn = data.ma_tn;
      this.thongso_tc.ma_doicap = data.ma_doicap;
      this.txtMATNTC = data.ma_tn;
    },
    acceptMucCuocThueBao(data){
      console.log(data);
      this.$refs.MucCuocThueBaoDialog.hide();
      if (data.trangbi_id != 0 && data.doituong_id != 0)
      {
        this.cboTrangBiTC.SelectedValue = data.trangbi_id;
        this.cboDoiTuongTC.SelectedValue = data.doituong_id;
      }
    },
   async loadData(){
      try{
        this.loading(true)
        await this.frmTocDoMucCuoc_Load();
        this.loading(false)
      }
      catch (e){
        this.loading(false)
      }
    },
    clearData(){
      this.cboTocDoBan.list = [];
      this.cboTocDoBan.SelectedValue = null;

      this.cboChuQuanLD_Ban.list = [];
      this.cboChuQuanLD_Ban.SelectedValue = null;


      this.cboMucCuocBan.list = [];
      this.cboMucCuocBan.SelectedValue= null;

      this.cboChuQuan_Ban.list = [];
      this.cboChuQuan_Ban.SelectedValue = null;

      this.cboCongNgheBan.list = [];
      this.cboCongNgheBan.SelectedValue = null;

      this.cboThietBiDCBan.list = [];
      this.cboThietBiDCBan.SelectedValue = null;

      this.cboTrangBiBan.list = [];
      this.cboTrangBiBan.SelectedValue = null;

      this.cboKieuTraBan.list = [];
      this.cboKieuTraBan.SelectedValue= null;

      this.cboTocDoThucBan.list = [];
      this.cboTocDoThucBan.SelectedValue = null;

      this.cboDoiTuongBan.list = [];
      this.cboDoiTuongBan.SelectedValue = null;

      this.cboMucCuocTBBan.list = [];
      this.cboMucCuocTBBan.SelectedValue = null;


      this.cboChuQuanLD_TC.list = [];
      this.cboChuQuanLD_TC.SelectedValue = null;

      this.cboChuQuan_TC.list = [];
      this.cboChuQuan_TC.SelectedValue = null;

      this.cboThietBiDCTC.list = [];
      this.cboThietBiDCTC.SelectedValue = null;

      this.cboKieuLDTC.list = [];
      this.cboKieuLDTC.SelectedValue = null;

      this.cboTrangBiTC.list = [];
      this.cboTrangBiTC.SelectedValue = null;

      this.cboKieuTraTC.list = [];
      this.cboKieuTraTC.SelectedValue = null;

      this.cboCongNgheTC.list = [];
      this.cboCongNgheTC.SelectedValue = null;

      this.cboMucCuocTC.list =[];
      this.cboMucCuocTC.SelectedValue = null;

      this.cboTocDoThucTC.list = [];
      this.cboTocDoThucTC.SelectedValue = null;

      this.cboDoiTuongTC.list = [];
      this.cboDoiTuongTC.SelectedValue = null;

      this.cboMucCuocTBTC.list = [];
      this.cboMucCuocTBTC.SelectedValue = null;

      this.cboTocDoTC.list = [];
      this.cboTocDoTC.SelectedValue = null;

    },

    Diachi_null()
    {
      let diachi = {
        diachi_id : 0,
        quan_id : 0,
        phuong_id : 0,
        pho_id : 0,
        khu_id : 0,
        ap_id : 0,
        dacdiem_id : 0
      }

      return diachi;
    },
    thongso_khoiphuc(vdichvuvt)
    {
      switch (vdichvuvt)
      {
        case DichVuVienThong.CO_DINH:
          this.cboTocDoBan.Enabled = false;
          this.cboTocDoThucBan.Enabled = false;
          this.cboCongNgheBan.Visible = false;
          this.cboKieuTraBan.Visible = false;
          this.cboMucCuocBan.Visible = true;
          this.cboMucCuocBan.Enabled = false;

          this.cboTocDoTC.Enabled = false;
          this.cboTocDoThucTC.Enabled = false;
          this.cboCongNgheTC.Visible = false;
          this.cboKieuTraTC.Visible = false;
          this.cboMucCuocTC.Visible = true;
          this.cboMucCuocTC.Enabled = false;
          break;
        case DichVuVienThong.ADSL:
          this.cboTocDoBan.Enabled = true;
          this.cboTocDoThucBan.Enabled = false;
          this.cboCongNgheBan.Visible = false;
          this.cboKieuTraBan.Visible = false;
          this.cboMucCuocBan.Visible = true;
          this.cboMucCuocBan.Enabled = true;

          this.cboTocDoTC.Enabled = true;
          this.cboTocDoThucTC.Enabled = false;
          this.cboCongNgheTC.Visible = false;
          this.cboKieuTraTC.Visible = false;
          this.cboMucCuocTC.Visible = true;
          this.cboMucCuocTC.Enabled = true;
          break;
      }
    },
    //Sự kiện button
    tsbtnCapNhat_Click(){
      if(this.cboThietBiDCTC.SelectedValue == 0 || this.cboThietBiDCTC.SelectedValue == undefined || this.cboThietBiDCTC.SelectedValue == null){
        this.$toast.error("Vui lòng chọn thiết bị đầu cuối 2 đơn vị");
        return;
      }
      if(this.cboThietBiDCBan.SelectedValue == 0 || this.cboThietBiDCBan.SelectedValue == undefined || this.cboThietBiDCBan.SelectedValue == null){
        this.$toast.error("Vui lòng chọn thiết bị đầu cuối 2 đơn vị");
        return;
      }
      if (this.cboMucCuocTBBan.SelectedValue != null && this.cboMucCuocTBTC.SelectedValue != null)
      {

        this.thongso_ban.tocdo_id = this.cboTocDoBan.SelectedValue;
        this.thongso_ban.tocdothuc_id = this.cboTocDoThucBan.SelectedValue;
        this.thongso_ban.kieutra_id = this.cboKieuTraBan.SelectedValue;
        this.thongso_ban.muccuoc_id = this.cboMucCuocBan.SelectedValue;
        this.thongso_ban.doituong_id = this.cboDoiTuongBan.SelectedValue;
        this.thongso_ban.muccuoctb_id = this.cboMucCuocTBBan.SelectedValue;
        this.thongso_ban.thietbidc_id = this.cboThietBiDCBan.SelectedValue;
        this.thongso_ban.trangbi_id = this.cboTrangBiBan.SelectedValue;
        this.thongso_ban.chuquan_id = this.cboChuQuan_Ban.SelectedValue;
        this.thongso_ban.chuquanld_id = this.cboChuQuanLD_Ban.SelectedValue;

        this.thongso_tc.kieuld_id = this.cboKieuLDTC.SelectedValue;
        this.thongso_tc.tocdo_id = this.cboTocDoTC.SelectedValue;
        this.thongso_tc.tocdothuc_id = this.cboTocDoThucTC.SelectedValue;
        this.thongso_tc.kieutra_id = this.cboKieuTraTC.SelectedValue;
        this.thongso_tc.muccuoc_id = this.cboMucCuocTC.SelectedValue;
        this.thongso_tc.doituong_id = this.cboDoiTuongTC.SelectedValue;
        this.thongso_tc.muccuoctb_id = this.cboMucCuocTBTC.SelectedValue;
        this.thongso_tc.thietbidc_id = this.cboThietBiDCTC.SelectedValue;
        this.thongso_tc.trangbi_id = this.cboTrangBiTC.SelectedValue;
        this.thongso_tc.chuquan_id = this.cboChuQuan_TC.SelectedValue;
        this.thongso_tc.chuquanld_id = this.cboChuQuanLD_TC.SelectedValue;
        let dataResponse = {
          "thongso_ban": this.thongso_ban,
          "thongso_tc":this.thongso_tc,
        }

        this.$emit("accept", dataResponse);
      }
      else
      {
        this.$toast.error("Bạn chưa chọn mức cước cho thuê bao");
      }
    },
    btnMucCuocTB_Click(){
      this.$refs.MucCuocThueBaoDialog.$children[0].frmMucCuocTB_TC_Load();
      this.$refs.MucCuocThueBaoDialog.show();
    },
   async chkTocDoTC_CheckedChanged(){
      if (this.chkTocDoTC.Checked == true)
      {
        let maTocDoTinhBan = "";
        if(this.cboTocDoBan.list.filter(x=>x.TOCDO_ID == this.cboTocDoBan.SelectedValue).length > 0){
          maTocDoTinhBan = this.cboTocDoBan.list.filter(x=>x.TOCDO_ID == this.cboTocDoBan.SelectedValue)[0].MATOCDO;
        }

        try{
          if (this._vloaihd == LoaiHopDong.CHUYEN_QUYEN || this._vloaihd == LoaiHopDong.DI_CHUYEN)

          {
            let response1 = await api.laydmtocdotinhtctheomatocdo_dc(this.axios, {
              "tinhthicong_id": this.tinhtc,
              "loaitb_id": this._ploaitb_td,
              "maTocDo": maTocDoTinhBan
            })
            if(response1.data.error_code === 'BSS-00000000' && response1.data.data){
              this.cboTocDoTC.list = response1.data.data;
              this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id;
              console.log("6: ", this.cboTocDoTC.SelectedValue);
            }
          }
          else{
            let response2 = await api.laydmtocdotinhtctheomatocdo(this.axios, {
              "tinhthicong_id": this.tinhtc,
              "loaitb_id": this._ploaitb_td,
              "maTocDo": maTocDoTinhBan
            })
            if(response2.data.error_code === 'BSS-00000000' && response2.data.data){
              this.cboTocDoTC.list = response2.data.data;
              this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id;
              console.log("7: ", this.cboTocDoTC.SelectedValue);
            }
          }
        }
        catch (e){
          console.log(e);
          this.cboTocDoTC.list= [];
        }

        if (this.cboTocDoTC.list.length ==  1)
        {
          this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id;
          console.log("8: ", this.cboTocDoTC.SelectedValue);
        }
      }
      else
      {
        await this.load_dm_tocdo_adsl();
        if (this.thongso_tc.tocdo_id == 0)
          this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id
        else
          this.cboTocDoTC.SelectedValue = this.thongso_tc.tocdo_id;
        console.log("9: ", this.cboTocDoTC.SelectedValue);
      }
    },
    //API
    async Lay_DS_MucCuoc_TB_Ban( vloaitb_id,  vdoituong_id,  ngay,  vtrangbi_id){
      try{
        let vtocdo_id = 0;
        let vmuccuoc_id = 0; //2 Lưu lượng
        if (this.pLoaihinhTB == LoaiHinhTB.INTERNET_FTTH ||
          this.pLoaihinhTB == LoaiHinhTB.WIFI_FIBER ||
          this.pLoaihinhTB == LoaiHinhTB.INTERNET_ADSL ||
          this.pLoaihinhTB == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
          this.pLoaihinhTB == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)
        {
          vtocdo_id = this.cboTocDoBan.SelectedValue;
          vmuccuoc_id = this.cboMucCuocBan.SelectedValue;
        }
        else if (this.pLoaihinhTB == LoaiHinhTB.INTERNET_MYTV)
        {
          vtocdo_id = this.cboTocDoBan.SelectedValue;
          vmuccuoc_id = 0;
        }
        else if (this.pLoaihinhTB == LoaiHinhTB.FSECURE)//Lamnt: bổ sung hiển thị mức cước theo tốc độ thuê bao FSecure (09/03/2017)
        {
          vtocdo_id = this.cboTocDoBan.SelectedValue;
          vmuccuoc_id = 0;
        }
        if (this._vloaihd == LoaiHopDong.DAT_MOI || this._vloaihd == LoaiHopDong.THAY_DOI_TOCDO_ADSL || this._vloaihd == LoaiHopDong.CHUYENDOI_LH)
        {
          let response1 = await api.lay_ds_mucuoc_tb_v2(this.axios,{
            "tinhthicong_id": this.$root.token.getPhanVungID(),
            "khuld_id": 0,
            "apld_id": 0,
            "phold_id": 0,
            "phuongld_id": 0,
            "loaitb_id": vloaitb_id,
            "doituong_id": vdoituong_id,
            "tocdo_id": vtocdo_id,
            "muccuoc_id": vmuccuoc_id,
            "trangbi_id": vtrangbi_id,
            "ngay": ngay
          })
          if(response1.data.error_code === 'BSS-00000000' && response1.data.data) {

            this.cboMucCuocTBBan.list = response1.data.data;
            this.cboMucCuocTBBan.list = this.cboMucCuocTBBan.list.map(function (item) {
            for (var key in item) {
              var upper = key.toLowerCase()
              // check if it already wasn't uppercase
              if (upper !== key) {
                item[upper] = item[key]
                delete item[key]
              }
            }
            return item
          })
            this.cboMucCuocTBBan.SelectedValue = this.cboMucCuocTBBan.list[0].muccuoctb_id;
          }
            else {
              this.cboMucCuocTBBan.list = [];
            }
          }

        else{
          let response = await api.lay_ds_muccuoc_tb_ban_frmtocdomuccuoc(this.axios,{
            "ds_para": JSON.stringify({"LOAIHD_ID":this._vloaihd,"LOAITB_ID":vloaitb_id,"DOITUONG_ID":vdoituong_id,
              "TOCDO_ID":vtocdo_id,"MUCCCUOC_ID":vmuccuoc_id,"NGAY":ngay,"TRANGBI_ID":vtrangbi_id,"TOCDO_BAN_ID":this.cboTocDoBan.SelectedValue})
          })
          if(response.data.error_code === 'BSS-00000000' && response.data.data){
            if(response.data.data.ERROR_CODE == 0){
              this.$toast.error(response.data.data.MESSAGE)
            }
            if(response.data.data.ERROR_CODE == 1 &&  response.data.data.RESULT.CBO_MUCCUOC.length > 0){
              this.cboMucCuocTBBan.list = response.data.data.RESULT.CBO_MUCCUOC;
              this.cboMucCuocTBBan.list = this.cboMucCuocTBBan.list.map(function (item) {
              for (var key in item) {
                var upper = key.toLowerCase()
                // check if it already wasn't uppercase
                if (upper !== key) {
                  item[upper] = item[key]
                  delete item[key]
                }
              }
              return item
            })
            }
            else {
              this.cboMucCuocTBBan.list = [];
            }
          }
        }
      }
      catch (e){
        console.log(e);
      }



    },
    async Lay_DS_MucCuoc_TB_TC(vloaitb_id,  vdoituong_id,  ngay,  vtrangbi_id){
      try{
        let vtocdo_id = 0;
        let vmuccuoc_id = 0; //2 Lưu lượng

        if (this.pLoaihinhTB == LoaiHinhTB.INTERNET_FTTH ||
          this.pLoaihinhTB == LoaiHinhTB.WIFI_FIBER ||
          this.pLoaihinhTB == LoaiHinhTB.INTERNET_ADSL ||
          this.pLoaihinhTB == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
          this.pLoaihinhTB == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)
        {
          vtocdo_id = this.cboTocDoTC.SelectedValue;
          vmuccuoc_id = this.cboMucCuocTC.SelectedValue;
        }
        else if (this.pLoaihinhTB == LoaiHinhTB.INTERNET_MYTV)
        {
          vtocdo_id = this.cboTocDoTC.SelectedValue;
          vmuccuoc_id = 0;
        }
        else if (this.pLoaihinhTB == LoaiHinhTB.FSECURE)//Lamnt: bổ sung hiển thị mức cước theo tốc độ thuê bao FSecure (09/03/2017)
        {
          vtocdo_id = this.cboTocDoTC.SelectedValue;
          vmuccuoc_id = 0;
        }
        if (this._vloaihd == LoaiHopDong.DAT_MOI || this._vloaihd == LoaiHopDong.THAY_DOI_TOCDO_ADSL || this._vloaihd == LoaiHopDong.CHUYENDOI_LH) {
          let response1 = await api.lay_ds_mucuoc_tb_v2(this.axios, {

            "tinhthicong_id": this.tinhtc,
            "khuld_id": 0,
            "apld_id": 0,
            "phold_id": 0,
            "phuongld_id": 0,
            "loaitb_id": vloaitb_id,
            "doituong_id": vdoituong_id,
            "tocdo_id": vtocdo_id,
            "muccuoc_id": vmuccuoc_id,
            "trangbi_id": vtrangbi_id,
            "ngay": ngay
          })
          if (response1.data.error_code === 'BSS-00000000' && response1.data.data) {

              this.cboMucCuocTBTC.list = response1.data.data;
              this.cboMucCuocTBTC.SelectedValue = this.cboMucCuocTBTC.list[0].muccuoctb_id
            } else {
              this.cboMucCuocTBTC.list = [];
            }
          }

        else
        {
          let response = await api.lay_ds_mucuoc_tb(this.axios,{
            "tinhthicong_id": this.tinhtc,
            "tocdo_id": vtocdo_id,
          })
          if(response.data.error_code === 'BSS-00000000' && response.data.data){
            this.cboMucCuocTBTC.list = response.data.data
            this.cboMucCuocTC.SelectedValue = this.cboMucCuocTBTC.list[0].muccuoctb_id
          }
          else{
            this.cboMucCuocTBTC.list = [];
          }
        }
      }
      catch (e){
        console.log(e);
      }


    },
    async ht_kieu_ld_combobox(){
      let response = await api.ht_kieu_ld_combobox(this.axios,{
        "tinhthicong_id": this.tinhtc,
        "loaitb_id": this._ploaitb_ld,
        "loaihd_id": this._vloaihd,
      })
      if(response.data.error_code === 'BSS-00000000' && response.data.data){
        this.cboKieuLDTC.list = response.data.data;
        this.cboKieuLDTC.SelectedValue = this.cboKieuLDTC.list[0].kieuld_id
        if (this.thongso_tc.kieuld_id == 0)
          this.cboKieuLDTC.SelectedValue = this.cboKieuLDTC.list[0].kieuld_id
        else
          this.cboKieuLDTC.SelectedValue = this.thongso_tc.kieuld_id;
      }
    },

    async get_map_id(){
      let input1 = {};
      input1.type = 2;
      input1.param = this._ploaitb_td;
      let response = await api.fn_tt_loaihinh_tb(this.axios,input1);
      if(response.data.error_code === 'BSS-00000000' && response.data.data){
        this.dichvuvt_id = response.data.data;
      }
    },

    async load_dm_tocdo_adsl(){
      let response = await api.load_dm_tocdo_adsl(this.axios,{
        "tinhthicong_id": this.tinhtc,
        "loaitb_id": this._ploaitb_td,
      })
      if(response.data.error_code === 'BSS-00000000' && response.data.data){
        this.cboTocDoTC.list = response.data.data;
        this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id

        if (this.thongso_tc.tocdo_id == 0)
          this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id
        else
          this.cboTocDoTC.SelectedValue = this.thongso_tc.tocdo_id;
        console.log("10: ", this.cboTocDoTC.SelectedValue);
      }
    },
    async load_dm_tocdo_adsl_dc(){
      let response = await api.load_dm_tocdo_adsl_dc(this.axios,{
        "tinhthicong_id": this.tinhtc,
        "loaitb_id": this._ploaitb_td,
      })
      if(response.data.error_code === 'BSS-00000000' && response.data.data){
        this.cboTocDoTC.list = response.data.data;
        this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id
        console.log(this.cboTocDoTC.list[0].tocdo_id)
        if (this.thongso_tc.tocdo_id == 0)
          this.cboTocDoTC.SelectedValue = this.cboTocDoTC.list[0].tocdo_id
        else
          this.cboTocDoTC.SelectedValue = this.thongso_tc.tocdo_id;
        console.log("11: ", this.cboTocDoTC.SelectedValue);
      }
    },
    async formload_frmtocdomuccuoc(){
      console.log("thông số bán: ",this.thongso_ban.tocdo_id);
      let response = await api.formload_frmtocdomuccuoc(this.axios,{
        "ds_para": JSON.stringify({"LOAIHD_ID":this._vloaihd,"LOAITB_ID":this._ploaitb_td,"TTND_NV_ID":this.$root.token.getNhanVienID(),
          "TTND_DONVI_ID":this.$root.token.getDonViID(),"TTND_NGUOIDUNG_ID":this.$root.token.getNguoiDungID()})
      })
      if(response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.ERROR_CODE == 1){
        let result = response.data.data.RESULT;
        this.cboTocDoBan.list = result.CBO_TOCDO_BAN;
        if(this.cboTocDoBan.list.length > 0){
          this.cboTocDoBan.SelectedValue = this.cboTocDoBan.list[0].TOCDO_ID
          if (this.thongso_ban.tocdo_id == 0)
            this.cboTocDoBan.SelectedValue = this.cboTocDoBan.list[0].TOCDO_ID
          else
            this.cboTocDoBan.SelectedValue = this.thongso_ban.tocdo_id;
        }
        this.cboKieuTraBan.list = result.DS_KIEUTRA;
        this.cboKieuTraBan.SelectedValue = this.cboKieuTraBan.list[0].KIEUTRA_ID
        // this.cboKieuTraTC.list = result.DS_KIEUTRA;
        // this.cboKieuTraTC.SelectedValue = this.cboKieuTraTC.list[0].KIEUTRA_ID
        if(this.thongso_ban.kieutra_id == 0)
          this.cboKieuTraBan.SelectedValue = this.cboKieuTraBan.list[0].KIEUTRA_ID
        else
          this.cboKieuTraBan.SelectedValue = this.thongso_ban.kieutra_id;
        // if (this.thongso_tc.kieutra_id == 0)
        //   this.cboKieuTraTC.SelectedValue = this.cboKieuTraTC.list[0].KIEUTRA_ID
        // else
        //   this.cboKieuTraTC.SelectedValue = this.thongso_tc.kieutra_id;
        this.cboMucCuocBan.list = result.CBO_MUCCUOC_BAN;
        this.cboMucCuocBan.SelectedValue = this.cboMucCuocBan.list[0].MUCCUOC_ID
        if (this.thongso_ban.muccuoc_id == 0)
          this.cboMucCuocBan.SelectedValue = this.cboMucCuocBan.list[0].MUCCUOC_ID
        else
          this.cboMucCuocBan.SelectedValue = this.thongso_ban.muccuoc_id;

        this.cboMucCuocTC.list = result.CBO_MUCCUOC_BAN;
        this.cboMucCuocTC.SelectedValue = this.cboMucCuocTC.list[0].MUCCUOC_ID
        if (this.thongso_tc.muccuoc_id == 0)
          this.cboMucCuocTC.SelectedValue = this.cboMucCuocTC.list[0].MUCCUOC_ID
        else
          this.cboMucCuocTC.SelectedValue = this.thongso_tc.muccuoc_id;

        this.tinh_chuquan_id = result.TINHCHU_QUAN_ID;

        this.cboChuQuan_Ban.list= result.DS_CQ_BAN;
        this.cboChuQuan_Ban.SelectedValue = this.cboChuQuan_Ban.list[0].CHUQUAN_ID

        if(result.DS_CQ_BAN.filter(x=>x.TINH_ID == this.$root.token.getPhanVungID()).length > 0){
          this.cboChuQuan_Ban.SelectedValue = result.DS_CQ_BAN.filter(x=>x.TINH_ID == this.$root.token.getPhanVungID())[0].CHUQUAN_ID
        }
        this.cboChuQuanLD_Ban.list= result.DS_CQ_BAN;
        this.cboChuQuanLD_Ban.SelectedValue = this.cboChuQuanLD_Ban.list[0].CHUQUAN_ID
        if(result.DS_CQ_BAN.filter(x=>x.TINH_ID == this.tinhtc).length > 0){
          this.cboChuQuanLD_Ban.SelectedValue = result.DS_CQ_BAN.filter(x=>x.TINH_ID == this.tinhtc)[0].CHUQUAN_ID;
        }
        this.cboChuQuan_TC.list= result.DS_CQ_TC;
        this.cboChuQuan_TC.SelectedValue = this.cboChuQuan_TC.list[0].CHUQUAN_ID
        if(result.DS_CQ_TC.filter(x=>x.TINH_ID == this.$root.token.getPhanVungID()).length > 0)
          this.cboChuQuan_TC.SelectedValue = result.DS_CQ_TC.filter(x=>x.TINH_ID == this.$root.token.getPhanVungID())[0].CHUQUAN_ID
        this.cboChuQuanLD_TC.list= result.DS_CQ_TC;
        this.cboChuQuanLD_TC.SelectedValue = this.cboChuQuanLD_TC.list[0].CHUQUAN_ID
        if(result.DS_CQ_TC.filter(x=>x.TINH_ID == this.tinhtc).length > 0)
          this.cboChuQuanLD_TC.SelectedValue = result.DS_CQ_TC.filter(x=>x.TINH_ID == this.tinhtc)[0].CHUQUAN_ID

        this.cboDoiTuongBan.list= result.CBO_DOITUONG_BAN;
        this.cboDoiTuongBan.SelectedValue = this.cboDoiTuongBan.list[0].DOITUONG_ID;
        if (this.thongso_ban.doituong_id == 0)
          this.cboDoiTuongBan.SelectedValue = this.cboDoiTuongBan.list[0].DOITUONG_ID;
        else
          this.cboDoiTuongBan.SelectedValue = this.thongso_ban.doituong_id;

        this.cboTrangBiBan.list = result.CBO_TRANGBI_BAN;
        this.cboTrangBiBan.SelectedValue = this.cboTrangBiBan.list[0].TRANGBI_ID;
        if (this.thongso_ban.trangbi_id > 0)
          this.cboTrangBiBan.SelectedValue = this.thongso_ban.trangbi_id;


      }
    },
    async ht_loai_tb_lhtb_combobox(kieucb){
      let response = await api.ht_loai_tb_lhtb_combobox(this.axios,{
        "tinhthicong_id": this.tinhtc,
        "loaitb_id": this._ploaitb_td,
        "kieucb": kieucb
      })
      if(response.data.error_code === 'BSS-00000000' && response.data.data){
        if(kieucb == 1){
          this.cboThietBiDCBan.list = response.data.data;
          this.cboThietBiDCBan.SelectedValue = this.cboThietBiDCBan.list[0].thietbidc_id;
          if (this.thongso_ban.thietbidc_id == 0)
            this.cboThietBiDCBan.SelectedValue = this.cboThietBiDCBan.list[0].thietbidc_id;
          else
            this.cboThietBiDCBan.SelectedValue = this.thongso_ban.thietbidc_id;
        }
        else if(kieucb == 2){
          this.cboThietBiDCTC.list = response.data.data;
          this.cboThietBiDCTC.SelectedValue = this.cboThietBiDCTC.list[0].thietbidc_id;
          if (this.thongso_tc.thietbidc_id > 0)
            this.cboThietBiDCTC.SelectedValue = this.thongso_tc.thietbidc_id;
          this.cboTrangBiTC.Enabled = true;

        }
      }
    },
    async load_dm_doi_tuong(){
      let response = await api.load_dm_doi_tuong(this.axios,{
        "tinhthicong_id": this.tinhtc
      })
      if(response.data.error_code === 'BSS-00000000' && response.data.data){
        this.cboDoiTuongTC.list = response.data.data;
        this.cboDoiTuongTC.SelectedValue = this.cboDoiTuongTC.list[0].doituong_id;
        if (this.thongso_tc.doituong_id == 0)
          this.cboDoiTuongTC.SelectedValue = this.cboDoiTuongTC.list[0].doituong_id;
        else
          this.cboDoiTuongTC.SelectedValue = this.thongso_tc.doituong_id;
      }
    },
    async load_dm_trang_bi(){
      let response = await api.load_dm_trang_bi(this.axios,{
        "tinhthicong_id": this.tinhtc
      })
      if(response.data.error_code === 'BSS-00000000' && response.data.data){
        this.cboTrangBiTC.list = response.data.data;
        this.cboTrangBiTC.SelectedValue = this.cboTrangBiTC.list[0].trangbi_id;
        if (this.thongso_tc.trangbi_id > 0)
          this.cboTrangBiTC.SelectedValue = this.thongso_tc.trangbi_id;
      }
    },
    async lay_diachi_dbtb_bancheo(){
      let response = await api.lay_diachi_dbtb_bancheo(this.axios,{
        "tinhthicong_id": this.tinhtc,
        "kieu_tinh": 2,
        "thuebao_id": this.thuebao_id_tc,
      })
      if(response.data.error_code === 'BSS-00000000' && response.data.data){
        this.diachi_thicong = {...response.data.data}
      }
    },
    async layMaTN_CD(){
      let kieu = 0;
      let input1 = {};
      input1.type = 1;
      input1.param = this.cboKieuLDTC.SelectedValue;
      let response = await api.fn_tt_kieu_ld(this.axios,input1)
      if(response.data.error_code === 'BSS-00000000' && response.data.data){
        kieu = response.data.data;
      }
      if (kieu == 2) //Lắp mới trên đường có sẵn
      {
        if (this.khachhang_id > 0)
        {

          await this.$refs.DSTBCungDoiCapBCPopup.$children[0].loadData();
          this.$refs.DSTBCungDoiCapBCPopup.show();
        }
        else
        {
          this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !");
          return;
        }
      }
      else if (kieu == 1) //Lắp kèm
      {
        if (this.hdkh_id > 0)
        {
          // frmDSTBLapKemBC frm = new frmDSTBLapKemBC();
          // frm.hdkh_id = hdkh_id;
          // frm.vkieuld_id = thongso_tc.kieuld_id;
          // frm.ShowDialog();
          // if (frm.chapnhan)
          // {
          //   thongso_tc.ma_tn = frm.ma_tn;
          //   thongso_tc.ma_doicap = frm.ma_doicap;
          //   txtMATNTC.Text = frm.ma_tn;
          // }
        }
        else
        {
          //Kiểm tra xem có thuê bao mega đang thực hiện chuyển đổi không
          this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !");
          return;
        }
      }
    },
  },
}

</script>

<style scoped>

</style>
