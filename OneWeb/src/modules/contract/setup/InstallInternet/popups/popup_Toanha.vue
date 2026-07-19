<template>
  <b-modal
    id="popup_Toanha"
    title="Gán thuê bao vào tòa nhà"
    modal-class="toanha"
    header-class="popup-header"
    content-class="tracuu-content"
    body-class="none-padding tracuu-body"
    size="xl"
    hide-footer
  >
    <div class="list-actions-top">
      <ul class="list">
        <li ref="toanha_tsbtnGhiLai" @click="tsbtnGhiLai_Click">
          <a>
            <span class="icon one-save"></span>Ghi lại
          </a>
        </li>
        <li ref="toanha_tsbtnXoa" @click="tsbtnXoa_Click">
          <a>
            <span class="icon one-trash"></span>Xoá toà nhà
          </a>
        </li>
        <li ref="toanha_tsbtnTimKiem" @click="tsbtnTimKiem_Click">
          <a>
            <span class="icon one-search"></span>Tìm kiếm
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="title fw6 upp">{{ label29 }}</div>
      </div>
      <div class="box-form">
        <div class="legend-title">Thông tin</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Mã khách hàng</div>
              <div class="value">
                <input
                  ref="toanha_txtMaKH"
                  v-model="txtMaKH"
                  type="text"
                  class="form-control highlight fw6"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Số máy/Acc</div>
              <div class="value">
                <input ref="toanha_txtMaTB" v-model="txtMaTB" type="text" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Tên thuê bao</div>
              <div class="value">
                <input type="text" ref="toanha_txtTenTB" v-model="txtTenTB" class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">
                <div class="check-action">
                  <input @change="chkQuan_CheckedChanged" ref="chkQuan" v-model="chkQuan" type="checkbox" class="check" />
                  <span class="name">Quận/Huyện</span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    v-model="cboHuyen.Value"
                    :allowFiltering="true"
                    :disabled="!cboHuyen.Enabled"
                    data-text-field="TEN_QUAN"
                    data-value-field="QUAN_ID"
                    @change="getCboHuyen_change"
                    :dataSource="cboHuyen.DataSource"
                  ></SelectExt>
                </div>
              </div>
            </div>

            <div class="info-row">
              <div class="key w100">
                <div class="check-action">
                  <input @change="chkPhuong_CheckedChanged" ref="chkPhuong" v-model="chkPhuong" type="checkbox" class="check" />
                  <span class="name">Phường/Xã</span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    @change="getCboToanha"
                    v-model="cboXa.Value"
                    :allowFiltering="true"
                    :disabled="!cboXa.Enabled"
                    data-text-field="TEN_PHUONG"
                    data-value-field="PHUONG_ID"
                    :dataSource="cboXa.DataSource"
                  ></SelectExt>
                </div>
              </div>
            </div>

            <div class="info-row">
              <div class="key w100">Toà nhà</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    v-model="cboToanha.Value"
                    :allowFiltering="true"
                    :disabled="cboToanha.Disabled"
                    data-text-field="ten_toanha"
                    data-value-field="toanha_id"
                    :dataSource="cboToanha.DataSource"
                  ></SelectExt>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="desc red fw6 mart10">{{ lblThongBao }}</div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách thuê bao</div>
        <div class="table-content">
          <DataGrid
            :columns="grvDanhSach.columns"
            ref="toanha_grvDanhSach"
            :dataSource="grvDanhSach.DataSource"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            @selectedRowChanged="grvDanhSach_Focus"
          ></DataGrid>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
export default {
  name: 'popup-toanha',
  data() {
    return {
      tag: '',
      grvDanhSach: {
        DataSource: [],
        columns: [
          {
            fieldName: "ma_kh",
            headerText: "Mã khách hàng",
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên thuê bao",
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: "ten_dvvt",
            headerText: "Dịch vụ VT",
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: "ma_kh",
            headerText: "Loại TB",
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: "ma_toanha",
            headerText: "Mã toàn nhà",
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: "ten_toanha",
            headerText: "Tên tòa nhà",
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          }
        ]
      },
      cboHuyen: {
        DataSource: [],
        Value: null,
        Enabled: true
      },
      cboXa: {
        DataSource: [],
        Value: null,
        Enabled: true
      },
      cboToanha: {
        DataSource: [],
        Value: null,
        Enabled: true
      },
      lblThongBao: "Thông báo",
      label29: "GÁN THUÊ BAO VÀO TÒA NHÀ",
      txtMaKH: "",
      txtMaTB: "",
      txtTenTB: "",
      Ma_GD: "",
      vma_kh: "",
      chkPhuong: false,
      chkQuan: false
    }
  },
  mounted() {

  },
  methods: {
    chkQuan_CheckedChanged() {
      if (this.chkQuan == false) {
        this.chkPhuong = false;
        this.$refs.chkPhuong.readOnly = false;
        this.cboHuyen.Enabled = true;
      }
      else {
        this.chkPhuong = true;
        this.$cboHuyen.Enabled = false;
      }
      this.getCboToanha();
    },
    chkPhuong_CheckedChanged() {
      this.cboXa.Enabled = !this.chkPhuong;
      this.getCboToanha();
    },
    tsbtnGhiLai_Click() {
      if (!this.KiemTra())
        return false
      this.CapNhat(1);
    },
    CapNhat(vloai) {
      //vloai = 1-Cap Nhat, 0-Xoa
      let vkieu = 0;
      let selected = this.$refs.toanha_grvDanhSach.getSelectedRecords()
      let item = selected && selected.length > 0 ? selected[0] : {}
      let vdichvuvt_id = item.dichvuvt_id ? item.dichvuvt_id : 0
      let vhdtb_id = 0;
      let vthuebao_id = 0;
      let vtoanha_id = (vloai == 0) ? 0 : this.cboToanha.Value

      if (this.tag == "capnhat_danhba") {
        vkieu = 2;      //Cập nhật Danh bạ con: 1. db_cd, 4. db_adsl, 8. db_mgwan, 9. db_tsl, 11. db_ims
        vthuebao_id = item.thuebao_id ? item.thuebao_id : 0
      }
      else {
        vkieu = 1;      //Cập nhật Hợp đồng con: 1. hdtb_cd, 4. hdtb_adsl, 8. hdtb_mgwan, 9. hdtb_tsl, 11. hdtb_ims
        vhdtb_id = item.hdtb_id ? item.hdtb_id : 0
      }
      this.axios.post("web-tracuu/tracuu_chitiet_thicong/sp_capnhat_toanha",
        {
          "type": vloai, "dichvuvt_id": vdichvuvt_id, "hdtb_id": vhdtb_id, "thuebao_id": vthuebao_id, "toanha_id": vtoanha_id,
          "may_cn": this.$root.token.getMaNhanVien(), "nguoi_cn": this.$root.token.getMaNhanVien(), "ip_cn": "1.1.1.1", "kieu": vkieu
        })

      this.showData()
    },
    tsbtnXoa_Click() {
      if (!this.KiemTra())
        return false
    },
    tsbtnTimKiem_Click() {
      this.showData()
    },
    show() {
      //this.txtMaTB = ma_tb;
      //this.tag = tag;
      this.$nextTick(() => {
        this.$bvModal.show("popup_Toanha");
        if (this.tag == "tracuu_danhba" || this.tag == "tracuu_hopdong") {
          this.label29 = "TRA CỨU THÔNG TIN TÒA NHÀ";
          this.$refs.toanha_tsbtnGhiLai.style.display = 'none';
          this.$refs.toanha_tsbtnXoa.style.display = 'none';

          this.cboXa.Enabled = true;
          this.cboHuyen.Enabled = true;
          this.cboToanha.Enabled = true;
          //this.$refs.txtMaKH.Disabled = false;
          this.$refs.toanha_txtMaTB.readOnly = true;
          this.$refs.toanha_txtTenTB.readOnly = true;

          this.txtMaKH = this.vma_kh;
        }
        else {
          //this.$refs.tsbtnTimKiem.style.display = 'none';
          this.lblThongBao = "(*)Gán thuê bao vào tòa nhà với các dịch vụ: Cố định, Băng rộng cố định, Megawan, Metronet, Truyền số liệu, IMS";
          if (this.$refs.toanha_txtMaKH)
            this.$refs.toanha_txtMaKH.disabled = true;
          if (this.$refs.toanha_txtMaTB)
            this.$refs.toanha_txtMaTB.disabled = true;
          if (this.$refs.toanha_txtTenTB)
            this.$refs.toanha_txtTenTB.disabled = true;
          this.cboXa.Enabled = true;
          this.cboHuyen.Enabled = true;
          this.chkQuan = true;
          if (this.tag == "capnhat_danhba")
            this.txtMaKH = this.vma_kh;
        }
        this.getCboHuyen()
        this.showData()
      })
    },
    grvDanhSach_Focus() {

    },
    getCboHuyen_change() {
      this.getCboXa()
      this.getCboToanha()
    },
    async getCboHuyen() {
      this.cboHuyen.Value = 0
      try {
        let data = await this.axios.get("web-quantri/danhmuc-chung/CSS_QUAN")
        this.cboHuyen.DataSource = data.data.data
      } catch (err) {
        this.cboHuyen.DataSource = []
      }
    },
    async getCboXa() {
      this.cboXa.Value = 0
      try {
        let data = await this.axios.post("web-quantri/danhmuc-chung/CSS_PHUONG", { ID: this.cboHuyen.Value })
        this.cboXa.DataSource = data.data.data
      } catch (err) {
        this.cboXa.DataSource = []
      }
    },
    async getCboToanha() {
      this.cboToanha.Value = 0
      try {
        let data = await this.axios.post("web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_toanha_by_quanphuong", { quan_id: this.cboHuyen.Value, phuong_id: this.cboXa.Value ? this.cboXa.Value : 0 })
        this.cboToanha.DataSource = data.data.data
      } catch (err) {
        this.cboToanha.DataSource = []
      }

    },

    KiemTra() {
      if (this.grvDanhSach.DataSource.length <= 0) {
        this.$toast.error("Không có dữ liệu!");
        return false;
      }
      if (!this.cboToanha.Value) {
        this.$toast.error("Không có thông tin tòa nhà!");
        return false;
      }
      if (!this.tag && !this.hdtb_id) {
        this.$toast.error("Không có thông tin hợp đồng thuê bao!");
        return false;
      }
      return true;
    },

    async showData() {
      try {
        let data = await this.axios.post("web-ccdv/tien_trinh_bao_hong/sp_lay_ds_thuebao_toanha_by_tag",
          {
            "p_ma_tb": this.txtMaTB,
            "p_ma_kh": this.txtMaKH,
            "p_ma_gd": this.Ma_GD,
            "p_tag": this.tag
          })
        this.grvDanhSach.DataSource = data.data.data
      } catch (err) {
        this.grvDanhSach.DataSource = []
      }
    }
  }
}
</script>
