<template>
  <div>
    <!-- breadcrumb -->
    <breadcrumb :header="header"></breadcrumb>

    <!-- menu -->
    <b-nav id="tlstpTop" pills="" class="menu">
      <!-- <b-nav-item @click="tsbtnGhiLai_Click"
        ><span class="icon one-save"></span> Ghi lại</b-nav-item
      > -->
      <b-nav-item @click="tsbtnTimKiem_Click"
        ><span class="one-search"></span> Tra cứu</b-nav-item
      >
    </b-nav>

    <!-- form -->
    <b-container fluid="" class="p-2 page-content">
      <b-row class="m-0">
        <b-col class="box-form p-2 mr-1 overflow-hidden">
          <div class="legend-title mb-2">Thông tin cập nhật</div>
          <b-form-group
            label="Tài khoản MyTV"
            label-cols="1"
            label-class="label-col"
          >
            <input
              type="text"
              class="form-control fw6 text"
              v-model="txtMaTB"
              @keyup.enter="txtMaTB_KeyPress"
              ref="inputMaTB"
            />
          </b-form-group>
          <b-form-group
            label="Khóa theo dịch vụ"
            label-cols="1"
            label-class="label-col"
          >
            <div class="row row-centered">
              <!-- <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox
                    class="check"
                    v-model="chkAll"
                    @change="checkAll"
                  >
                    Chọn tất cả
                  </b-form-checkbox>
                </div>
              </div> -->
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="Ceeme">
                    Ceeme
                  </b-form-checkbox>
                </div>
              </div>
            </div>
            <div class="row row-centered">
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="CloudGame">
                    Cloud Game
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="PhimTruyen">
                    Phim truyện
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="TroChoi">
                    Game
                  </b-form-checkbox>
                </div>
              </div>
            </div>
            <div class="row row-centered">
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="Karaoke">
                    Karaoke
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="CaNhac">
                    Ca nhạc
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="DocTruyen">
                    Đọc truyện
                  </b-form-checkbox>
                </div>
              </div>
            </div>
            <div class="row row-centered">
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="TheThao">
                    Thể thao
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="SucKhoeLamDep">
                    Sức khỏe làm đẹp
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="NhipCauMyTV">
                    Nhịp cầu MyTV
                  </b-form-checkbox>
                </div>
              </div>
            </div>
            <div class="row row-centered">
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="PhongCachSong">
                    Phong cách sống
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="ThieuNhi">
                    Thiếu nhi
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="GiaoDucDaoTao">
                    Giáo dục đào tạo
                  </b-form-checkbox>
                </div>
              </div>
            </div>
            <div class="row row-centered">
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="DaoTaoTuXa">
                    Đào tạo từ xa
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="KetQuaXoSo">
                    Kết quả xổ số
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="HBOFlg">
                    HBO on demand
                  </b-form-checkbox>
                </div>
              </div>
            </div>
            <div class="row row-centered">
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="FafilmFLG">
                    Fafilm
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="BinhChon">
                    Bình chọn
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="VTVcabFlg">
                    Kênh VTVCab
                  </b-form-checkbox>
                </div>
              </div>
            </div>
            <div class="row row-centered">
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="KplusFLG">
                    Kênh K+
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="FimPlus">
                    FIM+
                  </b-form-checkbox>
                </div>
              </div>
              <div class="col-sm-3 col-9">
                <div class="info-row">
                  <b-form-checkbox class="check" v-model="Danet">
                    Danet
                  </b-form-checkbox>
                </div>
              </div>
            </div>
          </b-form-group>
          <b-form-group label="Lý do" label-cols="1" label-class="label-col">
            <input
              type="text"
              class="form-control fw6 text"
              v-model="txtLyDo"
              ref="inputLyDo"
            />
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="m-0">
        <b-col class="box-form p-2 mr-1 overflow-hidden">
          <div class="legend-title mb-2">Lịch sử cập nhật</div>
          <DataGrid
            v-bind:columns="[
              {
                fieldName: 'kieu',
                headerText: 'Kiểu tác động',
                allowFiltering: true,
                allowSorting: false,
                textAlign: 'right',
              },
              {
                fieldName: 'giatri',
                headerText: 'Giá trị',
                allowFiltering: true,
                allowSorting: false,
                textAlign: 'left',
              },
              {
                fieldName: 'nguoi_cn',
                headerText: 'Người CN',
                allowFiltering: true,
                allowSorting: false,
                textAlign: 'left',
              },
              {
                fieldName: 'ten_nv',
                headerText: 'Nhân viên',
                allowFiltering: true,
                allowSorting: false,
                textAlign: 'left',
              },
              {
                fieldName: 'ten_dv',
                headerText: 'Đơn vị',
                allowFiltering: true,
                allowSorting: false,
                textAlign: 'left',
              },
              {
                fieldName: 'ip_cn',
                headerText: 'IP',
                allowFiltering: true,
                allowSorting: false,
                textAlign: 'right',
              },
              {
                fieldName: 'may_cn',
                headerText: 'Máy CN',
                allowFiltering: true,
                allowSorting: false,
                textAlign: 'left',
              },
              {
                fieldName: 'ngay_cn',
                headerText: 'Ngày CN',
                allowFiltering: true,
                allowSorting: false,
                textAlign: 'right',
              },
            ]"
            v-bind:dataSource="dgvLSCapNhat"
            :showFilter="true"
            :showColumnCheckbox="false"
            @selectedRowChanged="focusRow"
            :defaultColumnCheckboxChecked="false"
            :allowPaging="true"
            :enablePagingServer="false"
            :enabled-select-first-row="true"
          >
          </DataGrid>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<style scope>
.row-centered {
  margin-left: 10px;
}
.menu {
  background: white;
  border-bottom: 1px solid #9090;
  box-shadow: 0 0 3px #808080;
  height: 40px;
  padding-top: 5px;
  font-weight: 600;
  line-height: 1;
}

.menu .nav-link {
  border-radius: 0;
  border-right: 1px solid #a5c1d0;
}

.menu .nav-link:active {
  color: white;
  background: #007bff;
}

.menu .nav-item:last-child .nav-link {
  border-right: 0;
}

.hide-header thead {
  display: none;
}

.form-group {
  margin-bottom: 0.333rem;
}

.e-grid.e-responsive .e-headercelldiv {
  text-overflow: clip;
}

.c-red {
  color: red;
  font-weight: bold;
}

.overflow-hidden {
  overflow: hidden;
  flex-wrap: none;
}

.label-col {
  width: 120px;
  flex: 0 0 120px;
  max-width: 120px;
}

.custom-control-inline {
  width: 30%;
  flex: 0 0 30%;
  max-width: 30%;
}
</style>
<script>
import breadcrumb from "@/components/breadcrumb";
export default {
  name: "FrmTraCuuKhoaMoPayTV",
  components: { breadcrumb },
  created: function () {},
  data: function () {
    return {
      header: {
        title: "TRA CỨU KHÓA MỞ PAY TV",
        list: [
          {
            name: "Phát triển thuê bao",
            link: "/pttb",
          },
        ],
      },
      chkAll: false,
      CloudGame: false,
      PhimTruyen: false,
      TroChoi: false,
      Karaoke: false,
      CaNhac: false,
      DocTruyen: false,
      TheThao: false,
      SucKhoeLamDep: false,
      NhipCauMyTV: false,
      PhongCachSong: false,
      ThieuNhi: false,
      GiaoDucDaoTao: false,
      DaoTaoTuXa: false,
      KetQuaXoSo: false,
      BinhChon: false,
      Danet: false,
      FimPlus: false,
      KplusFLG: false,
      FafilmFLG: false,
      HBOFlg: false,
      VTVcabFlg: false,
      Ceeme: false,
      dgvLSCapNhat: [],
      txtMaTB: "",
      txtLyDo: "",
    };
  },
  methods: {
    checkDichVu() {
      this.selected = this.dsdichvu[2];
    },
    async timkiem() {
      this.loading(true);
      var rs = await this.$root.context.post(
        "/web-ccdv/khoamo_paytv/tracuu_ls_khoamo_paytv",
        {
          vma_tb: this.txtMaTB.trim() ? this.txtMaTB.trim() : "",
          vkieu: 1,
        }
      );
      if (
        rs.error_code === "BSS-00000000" &&
        rs.data !== undefined &&
        rs.data.length > 0
      ) {
        this.dgvLSCapNhat = rs.data;
      } else {
        this.dgvLSCapNhat=[];
        this.TaoDuLieuCheck(false);
        this.$toast.error("Không tìm thấy thông tin thuê bao");
      }
      this.loading(false);

      //console.log(rs.data);
    },
    focusRow(data) {
      this.TaoDuLieuCheck(false);

      let gtri = data.giatri.split(";");
      if (gtri[1].substr(gtri[1].length - 1, 1) == 1) this.CloudGame = true;
      if (gtri[2].substr(gtri[2].length - 1, 1) == 1) this.PhimTruyen = true;
      if (gtri[3].substr(gtri[3].length - 1, 1) == 1) this.TroChoi = true;
      if (gtri[4].substr(gtri[4].length - 1, 1) == 1) this.Karaoke = true;
      if (gtri[5].substr(gtri[5].length - 1, 1) == 1) this.CaNhac = true;
      if (gtri[6].substr(gtri[6].length - 1, 1) == 1) this.DocTruyen = true;

      if (gtri[7].substr(gtri[7].length - 1, 1) == 1) this.TheThao = true;
      if (gtri[8].substr(gtri[8].length - 1, 1) == 1) this.SucKhoeLamDep = true;
      if (gtri[9].substr(gtri[9].length - 1, 1) == 1) this.NhipCauMyTV = true;
      if (gtri[10].substr(gtri[10].length - 1, 1) == 1)
        this.PhongCachSong = true;
      if (gtri[11].substr(gtri[11].length - 1, 1) == 1) this.ThieuNhi = true;
      if (gtri[12].substr(gtri[12].length - 1, 1) == 1)
        this.GiaoDucDaoTao = true;
      if (gtri[13].substr(gtri[13].length - 1, 1) == 1) this.DaoTaoTuXa = true;
      if (gtri[14].substr(gtri[14].length - 1, 1) == 1) this.KetQuaXoSo = true;

      if (gtri[15].substr(gtri[15].length - 1, 1) == 1) this.BinhChon = true;
      if (gtri[16].substr(gtri[16].length - 1, 1) == 1) this.Danet = true;
      if (gtri[17].substr(gtri[17].length - 1, 1) == 1) this.FimPlus = true;
      if (gtri[18].substr(gtri[18].length - 1, 1) == 1) this.KplusFLG = true;
      if (gtri[19].substr(gtri[19].length - 1, 1) == 1) this.FafilmFLG = true;
      if (gtri[20].substr(gtri[20].length - 1, 1) == 1) this.HBOFlg = true;
      if (gtri[21].substr(gtri[21].length - 1, 1) == 1) this.VTVcabFlg = true;
      if (gtri[22].substr(gtri[22].length - 1, 1) == 1) this.Ceeme = true;
    },
    tsbtnTimKiem_Click() {
      if (this.txtMaTB.trim() == "") {
        this.$toast.error("Vui lòng nhập mã thuê bao");
        this.$refs.inputMaTB.focus();
        return false;
      }
      this.timkiem();
    },
    txtMaTB_KeyPress() {
      if (this.txtMaTB.trim() == "") {
        this.$toast.error("Vui lòng nhập mã thuê bao");
        this.$refs.inputMaTB.focus();
        return false;
      }
      this.timkiem();
    },
    checkAll() {
      if (this.chkAll) {
        this.TaoDuLieuCheck(true);
      } else {
        this.TaoDuLieuCheck(false);
      }
    },
    TaoDuLieuCheck(check) {
      this.CloudGame = check;
      this.PhimTruyen = check;
      this.TroChoi = check;
      this.Karaoke = check;
      this.CaNhac = check;
      this.DocTruyen = check;

      this.TheThao = check;
      this.SucKhoeLamDep = check;
      this.NhipCauMyTV = check;
      this.PhongCachSong = check;
      this.ThieuNhi = check;
      this.GiaoDucDaoTao = check;
      this.DaoTaoTuXa = check;
      this.KetQuaXoSo = check;

      this.BinhChon = check;
      this.Danet = check;
      this.FimPlus = check;
      this.KplusFLG = check;
      this.FafilmFLG = check;
      this.HBOFlg = check;
      this.VTVcabFlg = check;
      this.Ceeme = check;
    },
    /* async tsbtnGhiLai_Click(){
      if(this.txtMaTB.trim() ==""){
        this.$toast.error("Vui lòng nhập tài khoản MyTV");
        this.$refs.inputMaTB.focus();
        return false;
      }
      if(this.txtLyDo.trim() ==""){
        this.$toast.error("Vui lòng nhập lý do");
        this.$refs.inputLyDo.focus();
        return false;
      }
      this.loading(true);
      var rs = await this.$root.context.post("/tichhop/vasc/blockPayTV",
        {
        "objRequest": {
          "allService": this.chkAll?"1":"0",
          "binhChon": this.BinhChon?"1":"0",
          "caNhac": this.CaNhac?"1":"0",
          "ceeme": this.Ceeme?"1":"0",
          "cloudGame": this.CloudGame?"1":"0",
          "danet": this.Danet?"1":"0",
          "daoTaoTuXa": this.DaoTaoTuXa?"1":"0",
          "docTruyen": this.DocTruyen?"1":"0",
          "fafilmFLG": this.FafilmFLG?"1":"0",
          "fimPlus": this.FimPlus?"1":"0",
          "giaoDucDaoTao": this.GiaoDucDaoTao ?"1":"0",
          "hboflg": this.HBOFlg?"1":"0",
          "karaoke": this.Karaoke?"1":"0",
          "ketQuaXoSo": this.KetQuaXoSo?"1":"0",
          "kplusFLG": this.KplusFLG?"1":"0",
          "myTVID": this.txtMaTB,
          "myTVShow": "0",
          "nhipCauMyTV": this.NhipCauMyTV?"1":"0",
          "operator": "1",
          "phimTruyen": this.PhimTruyen?"1":"0",
          "phongCachSong": this.PhongCachSong?"1":"0",
          "reason": this.txtLyDo,
          "sucKhoeLamDep": this.SucKhoeLamDep?"1":"0",
          "theThao": this.TheThao?"1":"0",
          "thieuNhi": this.ThieuNhi?"1":"0",
          "troChoi": this.TroChoi?"1":"0",
          "vtvcabFlg": this.VTVcabFlg?"1":"0"
        }
      });
      if(rs.data == "ok"){
        this.$toast.success("Cập nhật thành công!")
      }else{
        this.$toast.error("Đã xảy ra lỗi, vui lòng thử lại! \n"+ rs.data)
      }
      
      this.loading(false);
    } */
  },
};
</script>