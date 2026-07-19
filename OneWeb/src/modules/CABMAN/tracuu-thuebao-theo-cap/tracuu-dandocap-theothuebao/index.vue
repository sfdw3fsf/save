<template src="./index.html">

</template>

<script>

import ModalLichSuCap from './ModalLichSuCap'
import ModalChonThueBao from './ModalChonThueBao'
import InLyLichDayMay from './InLyLichDayMay'
import report from './report'
import ToaDo from './ToaDo/Modal'

export default {
  props: {
    id: String,
    ma_thuebao: String,
  },
  components: {
    ModalLichSuCap,
    ModalChonThueBao,
    InLyLichDayMay,
    report,
    ToaDo
  },
  data() {
    return {
      thueBaoId: null,
      hdtbId: null,
      soMay: '',
      loaiTimKiem: 0,
      tenThueBao: '',
      loaiHinh: '',
      trangThai: '',
      diaChi: '',
      toaDo: '',
      toQL: '',
      tramTB: '',
      ghichu: '',
      ketCuoi: '',
      cap: '',
      soi: '',
      vitri1: '',
      vitri2: '',
      culy: '',
      isThueBao: '',
      daucuoi_id: '',
      viDo_LD: '',
      kinhDo_LD: '',
      soDoTuyenCap: [],
      tempSoDoTuyenCap: [],
      pageTraCuu: true,
      modalShowLichSuCap: false,
      modalShowInLyLich: false,
      dsThueBao: [],
      isDisabled: true,
      isAnDayNhayTrong: false,
      thongTinTBTraCuu: {},
      thongTinGianDo: [],
      thongTinBaoHong: [],
      doRenderPDF: false,
      showPanelReport: false
    }
  },
  computed: {
    objToaDo: function () {
      return {
        diachi: '',
        toado: this.viDo_LD + "," + this.kinhDo_LD,
      };
    },
  },
  async created() {

  },
  methods: {
    async onShownModal() {
      if (this.ma_thuebao != '') {
        console.log('thuebao_id', this.ma_thuebao)
        try {
          this.loading(true)
          this.soMay = this.ma_thuebao
          this.timKiemThueBao()
        }
        catch (e) { }
        finally {
          this.loading(false)
        }
      }

    },
    getDSThongTinThueBao: async function () {
      return await this.$root.context.post('/web-ecms/tracuu/dodancaptheosomayao', { so_may: this.soMay, loai_tra_cuu: this.loaiTimKiem });
    },
    getDSThongTinCap: async function (id, loai_tra_cuu, dau_cuoi_id) {
      return await this.$root.context.post('/web-ecms/tracuu/thongtincap', { id, loai_tra_cuu, dau_cuoi_id });
    },
    setThongTinThueBao: function (tttb) {
      this.thueBaoId = tttb.THUEBAO_ID;
      this.hdtbId = tttb.HDTB_ID;
      this.tenThueBao = tttb.TEN_TB;
      this.loaiHinh = tttb.LOAIHINH_TB;
      this.trangThai = tttb.TRANGTHAI_TB;
      this.diaChi = tttb.DIACHI_LD;
      this.toaDo = tttb.TOADO_LD;
      this.toQL = tttb.TEN_TOQL;
      this.tramTB = tttb.TEN_TRAMTBI;
      this.ghichu = tttb.GHICHU;
      this.isThueBao = tttb.IS_THUEBAO;
      this.daucuoi_id = tttb.DAUCUOI_ID;
      this.viDo_LD = tttb.VIDO_LD;
      this.kinhDo_LD = tttb.KINHDO_LD;
    },
    timKiemThueBao: async function () {
      this.soMay = this.soMay.trim();
      if (this.soMay) {
        try {
          this.loading(true);
          var rsThueBao = await this.getDSThongTinThueBao();
          if (rsThueBao.data.length > 0) {
            var tttb = rsThueBao.data[0];
            this.setThongTinThueBao(tttb);
            var rsCap = await this.getDSThongTinCap(this.isThueBao ? this.thueBaoId : this.hdtbId, this.loaiTimKiem, this.daucuoi_id);
            if (rsCap.data.TTCAP.length > 0) {
              var ttcap = rsCap.data.TTCAP[0];
              this.ketCuoi = ttcap.KYHIEU_KC;
              this.cap = ttcap.KYHIEU_CAP;
              this.soi = ttcap.SOI;
              this.vitri1 = ttcap.VITRI;
              this.vitri2 = ttcap.VITRI_2;
              this.culy = ttcap.CULY;
              this.soDoTuyenCap = rsCap.data.TTSDCAP;
              if (this.isAnDayNhayTrong) {
                this.tempSoDoTuyenCap = this.soDoTuyenCap.filter(x => x.KYHIEU != 'Dây nhảy trong' && x.LOAI_DT != 'Dây nhảy');
              }
              else {
                this.tempSoDoTuyenCap = this.soDoTuyenCap;
              }
            }
            else {
              this.tempSoDoTuyenCap = [];
              this.ketCuoi = null;
              this.cap = null;
              this.soi = null;
              this.vitri1 = null;
              this.vitri2 = null;
              this.culy = null;
              this.soDoTuyenCap = [];
            }
          }
          else {
            this.setThongTinThueBao({});
            this.tempSoDoTuyenCap = [];
            this.ketCuoi = null;
            this.cap = null;
            this.soi = null;
            this.vitri1 = null;
            this.vitri2 = null;
            this.culy = null;
            this.soDoTuyenCap = [];
            this.$root.toastError('Không tìm thấy thông tin thuê bao');
          }
        } catch (error) {
          this.$toast.error('Xảy ra lỗi');
        } finally {
          this.loading(false);
        }
      }
    },
    closeModalLichSuCap: function () {
      this.modalShowLichSuCap = !this.modalShowLichSuCap;
    },
    closeModalInLyLich: function () {
      this.modalShowInLyLich = !this.modalShowInLyLich;
    },
    grid_selectedRowChanged: function () {

    },
    ModalChonThueBao_selectedRowChanged: function (dataItem) {
      this.setThongTinThueBao(dataItem);
    },
    showModalChonThueBao: async function () {
      this.soMay = this.soMay.trim();
      if (this.soMay) {
        try {
          this.loading(true);
          var rs = await this.getDSThongTinThueBao();
          this.dsThueBao = rs.data;
          this.$bvModal.show("modal-ChonThueBao");
        } catch (error) {
          this.$toast.error('Xảy ra lỗi');
        } finally {
          this.loading(false);
        }
      }
      else {
        this.$root.toastError('Chưa nhập thuê bao tìm kiếm');
      }
    },
    chonThueBao: async function () {
      try {
        this.loading(true);
        var rsCap = await this.getDSThongTinCap(this.isThueBao ? this.thueBaoId : this.hdtbId, this.loaiTimKiem, this.daucuoi_id);
        if (rsCap.data.TTCAP.length > 0) {
          var ttcap = rsCap.data.TTCAP[0];
          this.ketCuoi = ttcap.KYHIEU_KC;
          this.cap = ttcap.KYHIEU_CAP;
          this.soi = ttcap.SOI;
          this.vitri1 = ttcap.VITRI;
          this.vitri2 = ttcap.VITRI_2;
          this.culy = ttcap.CULY;
          this.soDoTuyenCap = rsCap.data.TTSDCAP;
        }
      } catch (error) {
        this.$toast.error('Xảy ra lỗi');
      } finally {
        this.loading(false);
      }
    },
    nhapGhiChu: function () {
      if (this.thueBaoId || this.hdtbId) {
        if (this.isDisabled) {
          this.isDisabled = !this.isDisabled
        }
        else {
          if (this.ghichu) {
            try {
              this.loading(true);
              this.$root.context.post('/web-ecms/tracuu/capnhatghichu', {
                id: this.isThueBao ? this.thueBaoId : this.hdtbId,
                loai_tra_cuu: this.loaiTimKiem,
                ghichu: this.ghichu,
                dau_cuoi_id: this.daucuoi_id
              })
              this.$root.toastSuccess('Cập nhật ghi chú thành công');
            } catch (error) {
              this.$toast.error('Lỗi cập nhật ghi chú');
            } finally {
              this.loading(false);
            }
            this.isDisabled = !this.isDisabled;
          }
          else {
            this.$toast.error('Bạn chưa nhập ghi chú');
          }
        }
      }
      else {
        this.$toast.error('Chưa chọn thuê bao');
      }
    },
    getLyLichDayMay: async function (thueBaoID) {
      let rs = await this.$root.context.post(
        "/web-cabman/tracuu/traCuuLyLichDayMay",
        {
          id: thueBaoID,
        }
      );
      return rs.data;
    },
    getThongTinBaoHong: async function (thueBaoID) {
      //thueBaoID = 1680477
      let rs = await this.$root.context.post(
        "/web-cabman/tracuu/traCuuLichSuBaoHongThueBao",
        {
          id: thueBaoID,
        }
      );
      return rs.data;
    },
    showReport: async function () {
      this.modalShowInLyLich = true;
      try {
        this.loading(true);
        if (this.thueBaoId) {
          let rs = await this.getLyLichDayMay(this.thueBaoId);
          this.thongTinTBTraCuu = rs.ThongTinThueBao[0];
          this.thongTinGianDo = rs.DanDoCap;

          let rsBaoHong = await this.getThongTinBaoHong(this.thueBaoId);
          this.thongTinBaoHong = rsBaoHong;

          this.doRenderPDF = !this.doRenderPDF;
          this.showPanelReport = true;
        }
      } catch (error) {

      } finally {
        this.loading(false);
      }
    },
    traCuu: async function (inputThueBao, inputLoaiTimkiem) {
      try {
        this.loading(true);
        this.soMay = inputThueBao;
        this.loaiTimKiem = inputLoaiTimkiem;
        var rsThueBao = await this.getDSThongTinThueBao();
        if (rsThueBao.data.length > 0) {
          var tttb = rsThueBao.data[0];
          this.thueBaoId = tttb.THUEBAO_ID;
          await this.showReport();
        }
        else {
          this.$root.toastError('Không tìm thấy thông tin thuê bao');
        }
      } catch (error) {

      }
      finally {
        this.loading(false);
      }
    },
    getToaDo: function (data) {
      this.toaDo = data;
      // tách chuỗi data ở dấu ; hoặc ,
      //   và gán vào this.viDo_LD và this.kinhDo_LD
      if (/^(\-?\d+(\.\d+)?),\s*(\-?\d+(\.\d+)?)$/.test(data)) {
        var pair = data.split(',');
        this.viDo_LD = pair[0];
        this.kinhDo_LD = pair[1];
      }
      else if (/^(\-?\d+(\.\d+)?);\s*(\-?\d+(\.\d+)?)$/.test(data)) {
        var pair = data.split(';');
        this.viDo_LD = pair[0];
        this.kinhDo_LD = pair[1];
      }
      else {
        this.$root.toastError("Cấu trúc tọa độ không đúng định dạng");
      }
    },
    showMap: function () {
      this.$bvModal.show('popupToaDo')
    },
    capNhatToaDoThueBao: function () {
      this.$bvModal.msgBoxConfirm('Cập nhật tọa độ thuê bao vào danh bạ?', {
        size: 'sm',
        centered: true,
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy',
      })
        .then(async val => {
          if (val) {
            if (this.thueBaoId) {
              var lat;
              var lng;
              if (/^(\-?\d+(\.\d+)?),\s*(\-?\d+(\.\d+)?)$/.test(this.toaDo)) {
                var pair = this.toaDo.split(',');
                lat = pair[0];
                lng = pair[1];
              }
              else if (/^(\-?\d+(\.\d+)?);\s*(\-?\d+(\.\d+)?)$/.test(this.toaDo)) {
                var pair = this.toaDo.split(';');
                lat = pair[0];
                lng = pair[1];
              }
              else {
                this.$root.toastError("Cấu trúc tọa độ không đúng định dạng");
                return;
              }
              var id = this.isThueBao ? this.thueBaoId : this.hdtbId;
              try {
                this.loading(true);
                var rs = await this.$root.context.post('/web-cabman/danhmuc/toadothuebao/capnhat', {
                  kieu: this.isThueBao ? 0 : 1,
                  tb_id: id,
                  latitude: lat,
                  longtitude: lng,
                  update_tb_cung_cap: 1,
                  force_update: 1,
                  daucuoi_id: this.daucuoi_id
                })
                if (rs.data[0].KETQUA) {
                  this.viDo_LD = lat;
                  this.kinhDo_LD = lng;
                  this.$toast.success("Cập nhật tọa độ thuê bao thành công");
                }
                else {
                  this.$toast.error("Cập nhật tọa độ thuê bao thất bại");
                }
              } catch (error) {
                this.$toast.error("Cập nhật tọa độ thuê bao thất bại");
              }
              finally {
                this.loading(false);
              }
            }
            else {
              this.$root.toastError("Chưa nhập thuê bao tìm kiếm");
            }
          }
        })
        .catch(err => {
          this.$toast.error('Xảy ra lỗi');
        })
    }
  },
  watch: {
    isAnDayNhayTrong: function (newVal) {
      if (newVal) {
        this.tempSoDoTuyenCap = this.soDoTuyenCap.filter(x => x.KYHIEU != 'Dây nhảy trong' && x.LOAI_DT != 'Dây nhảy');
      }
      else {
        this.tempSoDoTuyenCap = this.soDoTuyenCap;
      }
    }
  }
}
</script>

<style scoped>
.check-action .check_xacnhan {
  position: relative;
  display: block;
  padding-left: 22px;
  min-height: 20px;
  background: #0d6efd;
}

.check-action .check_xacnhan:hover {
  cursor: pointer;
  opacity: 0.7;
}

.check-action .check_xacnhan::before {
  content: '';
  left: 6px;
  position: absolute;
  border-width: 0 2px 2px 0;
  border-color: white;
  border-style: solid;
  width: 10px;
  height: 16px;
  transform: rotate(45deg);
}
</style>
