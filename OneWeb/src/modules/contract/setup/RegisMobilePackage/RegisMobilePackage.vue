<!--suppress LanguageDetectionInspection -->
<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="onClickSearch">
            <span class="icon one-search"></span>Tra cứu
          </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <section class="box-form">
        <div class="row" style="font-size: 0.875rem;">
          <div class="col-6">
            <div class="legend-title">Thông tin thuê bao</div>
            <div class="form-row">
              <div class="col-6">
                <div class="info-row">
                  <div class="key w110">Số TB</div>
                  <div class="value d-flex flex-row align-items-center">
                    <div class="border p-2 bg-secondary text-white">84</div>
                    <input type="text" class="form-control" v-model="soThueBao" @keyup.enter="onClickSearch"/>
                  </div>
                </div>
              </div>
              <div class="col-6">
                <div class="info-row">
                  <div class="key w80">MSIN</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="msin"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-row">
              <div class="col-6">
                <div class="info-row">
                  <div class="key w110">Loại TB</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="loaiThueBao"/>
                  </div>
                </div>
              </div>
              <div class="col-6">
                <div class="info-row">
                  <div class="key w80">Ngày KH</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="ngayKH"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-row">
              <div class="col-6">
                <div class="info-row">
                  <div class="key w110">Hạng hội viên</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="hangHoiVien"/>
                  </div>
                </div>
              </div>
              <div class="col-6">
                <div class="info-row">
                  <div class="key w80">Mã CQ</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="maCQ"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-row">
              <div class="col-6">
                <div class="info-row">
                  <div class="key w110">Tỉnh</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="tenTinh"/>
                  </div>
                </div>
              </div>
              <div class="col-6">
                <div class="info-row">
                  <div class="key w80">Ngày sinh</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="ngaySinh"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-row">
              <div class="col-6">
                <div class="info-row">
                  <div class="key w110">Mã KH</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="maKH"/>
                  </div>
                </div>
              </div>
              <div class="col-6">
                <div class="info-row">
                  <div class="key w80">Ngày cấp</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="ngayCapGiayTo"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-row">
              <div class="col-6">
                <div class="info-row">
                  <div class="key w110">Tên thuê bao</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="tenThueBao"/>
                  </div>
                </div>
              </div>
              <div class="col-6">
                <div class="info-row">
                  <div class="key w85">PIN/PUK</div>
                  <div class="form-row">
                    <div class="col-6">
                      <input type="text" readonly class="form-control" v-model="pin1"/>
                    </div>
                    <div class="col-6">
                      <input type="text" readonly class="form-control" v-model="puk1"/>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-row">
              <div class="col-6">
                <div class="info-row">
                  <div class="key w110">Số GT</div>
                  <div class="value">
                    <input type="text" readonly class="form-control" v-model="soGiayTo"/>
                  </div>
                </div>
              </div>
              <div class="col-6">
                <div class="info-row">
                  <div class="key w85">PIN2/PUK2</div>
                  <div class="form-row">
                    <div class="col-6">
                      <input type="text" readonly class="form-control" v-model="pin2"/>
                    </div>
                    <div class="col-6">
                      <input type="text" readonly class="form-control" v-model="puk2"/>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w110">Đối tượng</div>
              <div class="value">
                <input type="text" readonly class="form-control" v-model="doiTuong"/>
              </div>
            </div>
            <div class="info-row">
              <div class="key w110">Địa chỉ chứng từ</div>
              <div class="value">
                <input type="text" readonly class="form-control" v-model="diaChiChungTu"/>
              </div>
            </div>
            <div class="info-row">
              <div class="key w110">Địa chỉ thanh toán</div>
              <div class="value">
                <input type="text" readonly class="form-control" v-model="diaChiThanhToan"/>
              </div>
            </div>
            <div class="info-row">
              <div class="key w110">Địa chỉ thường trú</div>
              <div class="value">
                <input type="text" readonly class="form-control" v-model="diaChiThuongTru"/>
              </div>
            </div>
          </div>
          <div class="col-3">
            <div class="legend-title">&nbsp;</div>
            <div class="text-center mb-3">
              <b-form-checkbox class="mr-3" disabled v-model="checkGoiDi">Gọi đi</b-form-checkbox>
              <b-form-checkbox disabled v-model="checkGoiDen">Gọi đến</b-form-checkbox>
            </div>
            <div class="info-row">
              <div class="key w150">Tài khoản chính</div>
              <div class="value">
                <input type="text" readonly class="form-control" v-model="taiKhoanChinh"/>
              </div>
            </div>
            <div class="info-row">
              <div class="key w150">Hạn sử dụng</div>
              <div class="value">
                <input type="text" readonly class="form-control" v-model="hanSuDung"/>
              </div>
            </div>
            <div class="info-row">
              <div class="key w150">TB TT được hưởng KM</div>
              <div class="value">
                <input type="text" readonly class="form-control" v-model="khuyenMaiTT"/>
              </div>
            </div>
            <div class="info-row">
              <div class="key w150">Gói TT ưu tiên mới ĐK</div>
              <div class="value">
                <input type="text" readonly class="form-control" v-model="khuyenMaiTT"/>
              </div>
            </div>
            <div class="info-row">
              <div class="key w150">Loại thiết bị sử dụng</div>
              <div class="value">
                <input type="text" readonly class="form-control" v-model="loaiThietBiSd"/>
              </div>
            </div>
            <div class="text-right mt-4">
              <b-button variant="outline-primary" class="mr-3" @click="onClickTuVanDichVu" :disabled="!soThueBao_result">
                Tư vấn dịch vụ
              </b-button>
              <ejs-dialog :isModal="true" :ref="thongTinTiepThiModalId" :showCloseIcon="true" width="80%" :visible="false"
                          header="Thông tin tiếp thị"
                          :animationSettings="{ effect: null, duration: 0, delay:0 }"
                          @open="thongTinTiepThiModalIsOpen = true" @close="thongTinTiepThiModalIsOpen = false">
                <ThongTinTiepThiPopup
                  v-if="thongTinTiepThiModalIsOpen"
                  :modalId="thongTinTiepThiModalId"
                  :soThueBao="soThueBao_result"
                  :tenThueBao="tenThueBao"
                  :loaiThueBao="loaiThueBao"
                  :diaChi="diaChiThanhToan"
                  :khuyenMaiTT="khuyenMaiTT"
                  :goiTtUuTienDk="goiTtUuTienDk"
                  :didong_goi="didong_goi"
                ></ThongTinTiepThiPopup>
              </ejs-dialog>
              <b-modal :id="thongTinTiepThiModalId" size="xl"
                       hide-footer hide-header hide-header-close body-class="modal-body p-0">

              </b-modal>
              <b-button variant="outline-primary" @click="onClickDichVuGTGT" :disabled="!soThueBao_result">
                Quản lý dịch vụ GTGT
              </b-button>
              <b-modal :id="listDichVuGTGTModalId" size="xl"
                       hide-footer hide-header hide-header-close body-class="modal-body p-0">
                <ListDichVuGTGT :modal-id="listDichVuGTGTModalId" :so_tb="soThueBao_result"></ListDichVuGTGT>
              </b-modal>
            </div>
          </div>
          <div class="col-3">
            <div class="legend-title">Các dịch vụ đang sử dụng</div>
            <b-table hover :items="listDichVu" :fields="dichVuTableFields" thead-class="d-none"></b-table>
          </div>
        </div>
      </section>
      <b-tabs>
        <b-tab title="Lịch sử thuê bao" active>
          <DataGrid :columns="gridLichSuTBFields" :dataSource="dsLichSuTB"
                    :enable-paging-server="false" :allowPaging="true" :showFilter="true"></DataGrid>
        </b-tab>
        <b-tab title="Lịch sử 3G">
          <DataGrid :columns="gridLichSu3gFields" :dataSource="dsLichSu3g"
                    :enable-paging-server="false" :allowPaging="true" :showFilter="true"></DataGrid>
        </b-tab>
        <b-tab title="Lịch sử nạp thẻ">
          <DataGrid :columns="gridLichSuNapTheFields" :dataSource="dsLichSuNapThe"
                    :enable-paging-server="false" :allowPaging="true" :showFilter="true"></DataGrid>
        </b-tab>
      </b-tabs>
    </div>
  </div>
</template>

<script>
import _ from "lodash";
import moment from "moment";
import breadcrumb from '@/components/breadcrumb';
import api from "./api";
import ListDichVuGTGT from "./popups/ListDichVuGTGT";
import ThongTinTiepThiPopup from "./popups/ThongTinTiepThiPopup";

export default {
  name: "RegisMobilePackage",
  components: {
    breadcrumb,
    ListDichVuGTGT,
    ThongTinTiepThiPopup
  },

  mounted() {

  },

  data() {
    return {
      header: {
        title: 'Tra cứu thông tin thuê bao',
        list: []
      },

      traSau: false,

      soThueBao: "",
      soThueBao_result: "",
      msin: "",
      loaiThueBao: "",
      ngayKH: "",
      hangHoiVien: "",
      maCQ: "",
      tenTinh: "",
      ngaySinh: "",
      maKH: "",
      ngayCapGiayTo: "",
      tenThueBao: "",
      pin1: "",
      puk1: "",
      soGiayTo: "",
      pin2: "",
      puk2: "",
      doiTuong: "",
      diaChiChungTu: "",
      diaChiThanhToan: "",
      diaChiThuongTru: "",

      checkGoiDi: false,
      checkGoiDen: false,

      taiKhoanChinh: "",
      hanSuDung: "",
      khuyenMaiTT: "",
      goiTtUuTienDk: "",
      loaiThietBiSd: "",

      dichVuTableFields: [
        { key: 'ma_dv', label: 'Mã DV' },
        { key: 'ten_dv', label: 'Tên DV' },
      ],
      listDichVu: [],

      gridLichSuTBFields: [
        {fieldName: 'ngay_thang', headerText: 'Ngày thực hiện'},
        {fieldName: 'ma_dv', headerText: 'Mã DV'},
        {fieldName: 'thao_tac', headerText: 'Thao tác'},
        {fieldName: 'ghi_chu', headerText: 'Ghi chú'},
        {fieldName: 'nguoi_dung', headerText: 'Người dùng'},
        {fieldName: 'so_msin_cu', headerText: 'MSIN cũ'},
        {fieldName: 'so_msin_moi', headerText: 'MSIN mới'},
        {fieldName: 'ma_tinh_cu', headerText: 'Tỉnh cũ'},
        {fieldName: 'ma_tinh_moi', headerText: 'Tỉnh mới'},

      ],
      dsLichSuTB: [],

      gridLichSu3gFields: [
        {fieldName: 'service_id', headerText: 'Mã DV'},
        {fieldName: 'dichvu', headerText: 'Dịch vụ'},
        {fieldName: 'goi', headerText: 'Gói'},
        {fieldName: 'thao_tac', headerText: 'Thao tác'},
        {fieldName: 'batdau', headerText: 'Ngày bắt đầu'},
        {fieldName: 'ketthuc', headerText: 'Ngày kết thúc'},
        {fieldName: 'nguoith', headerText: 'Người cập nhật'},
      ],
      dsLichSu3g: [],

      gridLichSuNapTheFields: [
        {fieldName: 'ngay_nap', headerText: 'Ngày nạp'},
        {fieldName: 'menh_gia', headerText: 'Mệnh giá'},
        {fieldName: 'phuong_thuc_nap', headerText: 'Phương thức nạp'},
        {fieldName: 'tk_truoc_khi_nap', headerText: 'TK trước khi nạp'},
      ],
      dsLichSuNapThe: [],

      didong_goi: [],

      listDichVuGTGTModalId: "popup-ListDichVuGTGT",
      thongTinTiepThiModalId: "popup-ThongTinTiepThi",
      thongTinTiepThiModalIsOpen: false
    };
  },

  computed: {
    soThueBaoChuanHoa() {
      const soThueBao = this.soThueBao.trim();
      if (!soThueBao) return;

      let soThueBaoX = soThueBao;

      if (soThueBao.startsWith("84") && soThueBao.length >= 11) {
        soThueBaoX = soThueBao.slice(2);
      } else if (soThueBao.startsWith("0")) {
        soThueBaoX = soThueBao.slice(1);
      }

      const soThueBao84 = "84" + soThueBaoX;
      const soThueBao0 = "0" + soThueBaoX;

      return { soThueBaoX, soThueBao84, soThueBao0 };
    },
  },

  methods: {
    clearAll() {
      this.traSau = false;

      // this.soThueBao = "";
      this.msin = "";
      this.loaiThueBao = "";
      this.ngayKH = "";
      this.hangHoiVien = "";
      this.maCQ = "";
      this.tenTinh = "";
      this.ngaySinh = "";
      this.maKH = "";
      this.ngayCapGiayTo = "";
      this.tenThueBao = "";
      this.pin1 = "";
      this.puk1 = "";
      this.soGiayTo = "";
      this.pin2 = "";
      this.puk2 = "";
      this.doiTuong = "";
      this.diaChiChungTu = "";
      this.diaChiThanhToan = "";
      this.diaChiThuongTru = "";

      this.checkGoiDi = false;
      this.checkGoiDen = false;

      this.taiKhoanChinh = "";
      this.hanSuDung = "";
      this.khuyenMaiTT = "";
      this.goiTtUuTienDk = "";
      this.loaiThietBiSd = "";

      this.listDichVu = [];

      this.dsLichSuTB = [];
      this.dsLichSu3g = [];
      this.dsLichSuNapThe = [];
    },

    async onClickSearch() {
      this.clearAll();

      /*
      {
          "thang_bd": "2021-04-21 06:46:05",
          "ma_so_kh": null,
          "so_tb": "84843131022",
          "so_msin": null,
          "imsi": "452021121624348",
          "trang_thai": 0,
          "goi_di": 1,
          "goi_den": 1,
          "ma_tinh": "TQG",
          "loai": "X",
          "so_sim": null,
          "active": null,
          "pin": "1234",
          "puk": "71363498",
          "pin2": "1234",
          "puk2": "40749407",
          "b_puk": "71363498",
          "b_puk2": "40749407",
          "loai_tb": "Xtra",
          "ngay_kh": null,
          "ten_kh": "  ",
          "sp_name": "-1",
          "tg_sudung": "00",
          "ac_state": null,
          "pk_id": -1,
          "pk_name": "Khong dung goi GPRS",
          "dchi_kh": null,
          "socmt": null,
          "ngaysinh": null,
          "ngaycap": null,
          "tra_sau": 0,
          "idd1714_dk": null,
          "idd1714": null,
          "esim": "0",
          "qrstring": null,
          "ten_tb": "  ",
          "phai": "",
          "diachi_thuongtru": "",
          "diachi_chungtu": "",
          "doituong": "",
          "loaikh_id": "",
          "loai_gt": "",
          "noicap_gt": "",
          "nganhnghe_id": ""
      }
       */
      const tt_thuebao = (await api.app_tracuu_tt_thuebao(this.axios, { so_tb: this.soThueBaoChuanHoa.soThueBao84 })).data.data;
      if (!tt_thuebao) {
        this.$toast("Không tìm thấy thuê bao");
        return;
      }

      if (tt_thuebao.loai_tb === "GPhone") {
        this.$toast("Chức năng chỉ tra cứu với thuê bao di động!");
        return;
      }

      this.traSau = !!tt_thuebao.tra_sau;
      this.soThueBao = tt_thuebao.so_tb;
      this.soThueBao_result = tt_thuebao.so_tb;
      this.msin = tt_thuebao.so_msin;
      this.loaiThueBao = tt_thuebao.loai_tb;
      this.ngayKH = tt_thuebao.ngay_kh;
      this.maKH = tt_thuebao.ma_so_kh;
      this.tenTinh = tt_thuebao.ma_tinh;
      this.pin1 = tt_thuebao.pin;
      this.puk1 = tt_thuebao.puk;
      this.pin2 = tt_thuebao.pin2;
      this.puk2 = tt_thuebao.puk2;
      this.checkGoiDi = !!tt_thuebao.goi_di;
      this.checkGoiDen = !!tt_thuebao.goi_den;

      if (this.traSau) {
        /*
        {
          "DIACHI": "Đường Phạm Huy Thông, Phường An Dương, Quận Lê Chân, Hải Phòng",
          "LOAIKH_ID": 4,
          "NGANHNGHE_ID": 0,
          "PHAI": "Nu",
          "SO_GT": "017022799",
          "NGAYCAP_GT": "16/02/2009",
          "NOICAP_GT": "HPG",
          "DIACHI_THUONGTRU": "Đường Phạm Huy Thông, Phường An Dương, Quận Lê Chân, Hải Phòng",
          "TEN_KH": "Lê Thị Hương",
          "TEN_TB": "Lê Thị Hương",
          "DIACHI_CHUNGTU": "Đường Phạm Huy Thông, Phường An Dương, Quận Lê Chân, Hải Phòng",
          "DOITUONG": "Nhóm khách hàng cá nhân, hộ gia đình",
          "NGAY_SINH": "20/12/1991",
          "LOAI_GT": 1
        }
       */
        const tt_tb_ts = (await api.ts_tracuu_laytt_tb_ts(this.axios, {
          so_tb: this.soThueBaoChuanHoa.soThueBao84,
          ma_tinh: tt_thuebao.ma_tinh
        })).data.data[0];
        this.ngaySinh = tt_tb_ts.NGAY_SINH;
        this.doiTuong = tt_tb_ts.DOITUONG;
        this.soGiayTo = tt_tb_ts.SO_GT;
        this.ngayCapGiayTo = tt_tb_ts.NGAYCAP_GT;
        this.tenThueBao = tt_tb_ts.TEN_TB;
        this.diaChiThanhToan = tt_tb_ts.DIACHI;
        this.diaChiChungTu = tt_tb_ts.DIACHI_CHUNGTU;
        this.diaChiThuongTru = tt_tb_ts.DIACHI_THUONGTRU;

      } else {
        this.ngaySinh = tt_thuebao.ngaysinh;
        this.soGiayTo = tt_thuebao.socmt;
        this.ngayCapGiayTo = tt_thuebao.ngaycap;
        this.tenThueBao = tt_thuebao.ten_kh;
        this.diaChiThanhToan = tt_thuebao.dchi_kh;
      }

      /*
      [
        {
          "ten_dv": "Goi quoc te",
          "ma_dv": "IDD ",
          "checked": null,
          "trang_thai": "0",
          "cm_enable": 1,
          "cm_disable": null
        },
        {
          "ten_dv": "International Roaming",
          "ma_dv": "IR  ",
          "checked": null,
          "trang_thai": "0",
          "cm_enable": 1,
          "cm_disable": null
        }
      ]
       */
      this.listDichVu = (await api.app_tracuu_sudung_dv(this.axios, { so_tb: this.soThueBaoChuanHoa.soThueBao84 })).data.data;

      /*
      [
        {
          "ngay_thang": "01/10/2021 10:37:47",
          "ngay_thang1": "2021-10-01 03:37:47",
          "ma_dv": "IC  ",
          "thaotac10": "0",
          "thao_tac": "Khoa",
          "so_msin_cu": null,
          "so_msin_moi": null,
          "nguoi_dung": "duchtv_vnp",
          "ghi_chu": "test",
          "ma_tinh_cu": null,
          "ma_tinh_moi": "HNI",
          "so_tb": "84856334585",
          "don_vi": null
        },
        {
          "ngay_thang": "01/10/2021 10:37:47",
          "ngay_thang1": "2021-10-01 03:37:47",
          "ma_dv": "OC  ",
          "thaotac10": "0",
          "thao_tac": "Khoa",
          "so_msin_cu": null,
          "so_msin_moi": null,
          "nguoi_dung": "duchtv_vnp",
          "ghi_chu": "test",
          "ma_tinh_cu": null,
          "ma_tinh_moi": "HNI",
          "so_tb": "84856334585",
          "don_vi": null
        }
      ]
       */
      this.dsLichSuTB = (await api.app_tracuu_lsthuebao(this.axios, { so_tb: this.soThueBaoChuanHoa.soThueBao84 })).data.data;

      /*
      [
        {
          "service_code": "MI_BIGKM_7GB",
          "service_id": "MI_BIGKM_7GB",
          "dichvu": null,
          "goi": null,
          "batdau": "10/02/2020 13:24:47",
          "ketthuc": "31/01/2023 23:59:59",
          "active": "#DFEFFF",
          "thao_tac": "Mo",
          "msisdn": "84918486486",
          "time_start": "2020-02-10 06:24:47",
          "nguoith": null
        }
      ]
       */
      this.dsLichSu3g = (await api.app_tracuu_ls_dichvu3g(this.axios, { so_tb: this.soThueBaoChuanHoa.soThueBao84 })).data.data;

      this.didong_goi = (await api.didong_goi(this.axios, { so_tb: this.soThueBaoChuanHoa.soThueBao84 })).data.data;
      api.didong_nhom_taikhoan(this.axios, { so_tb: this.soThueBaoChuanHoa.soThueBao84 });
      api.didong_taikhoan(this.axios, { so_tb: this.soThueBaoChuanHoa.soThueBao84 });

      const ds_taikhoan_tien = (await api.didong_taikhoan_tien(this.axios, { so_tb: this.soThueBaoChuanHoa.soThueBao84 })).data.data;
      for (const taikhoan_tien of ds_taikhoan_tien) {
        if (+taikhoan_tien.ID === 1) {
          this.taiKhoanChinh = taikhoan_tien.REMAIN;
          this.hanSuDung = taikhoan_tien.TIME_END? moment(taikhoan_tien.TIME_END).format("DD/MM/YYYY HH:mm") : "";
        }
      }

    },

    onClickTuVanDichVu() {
      this.$refs[this.thongTinTiepThiModalId].show();
    },

    onClickDichVuGTGT() {
      this.$bvModal.show(this.listDichVuGTGTModalId);
    },
  },
};
</script>
