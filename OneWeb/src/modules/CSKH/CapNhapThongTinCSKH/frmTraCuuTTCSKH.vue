<template>
<ejs-dialog
    :enableResize="true"
    :allowDragging="true"
    ref="frmTraCuuTTCSKH"
    :header="'Tra cứu thông tin chăm sóc khách hàng'"
    showCloseIcon="true"
    width="60%"
    :visible="false"
    :open="dialogOpen"
    :close="closeDialog"
    :overlayClick="closeDialog"
    :isModal="true"
>
  <div class="">
  <!-- <b-modal
    id="frmTraCuuTTCSKH"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="onShow"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Tra cứu thông tin chăm sóc khách
          hàng
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('frmTraCuuTTCSKH')"
        ></div>
      </div> -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)" @click="onTimKiemClicked()"> <span class="icon one-search"></span>Tìm kiếm </a>
          </li>
          <li>
            <a href="javascript: void(0)">

              <downloadexcel :fields="this.exportExcelFields" :data="this.pDsCSKH" worksheet="export"
                  name="export.xls" :before-generate="onStartDownload">
                 <span class="icon one-excel-download"></span>Xuất Excel
              </downloadexcel>
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Chọn tiêu chí tìm kiếm</div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w120">
                  <div class="check-action -color">
                    <input type="checkbox" class="check" v-model="eCboLoaiDoiTuong"/>
                    <span class="name">Loại đối tượng</span>
                  </div>
                </div>
                <div class="value">
                  <div class="">
                    <select2 v-model="pLoaiDoiTuongSelected"
                        ref="cboDoiTuong"
                        :options="pDsDoiTuongChamSoc"
                        :disabled="!eCboLoaiDoiTuong"
                    />
                    <!-- <b-form-select
                      :disabled="!eCboLoaiDoiTuong"
                      v-model="pLoaiDoiTuongSelected"
                      :options="pDsDoiTuongChamSoc"
                      ref="cboDoiTuong"
                      value-field="doiTuongCsId"
                      text-field="tenDoiTuongCs"
                      class="form-control">
                    </b-form-select> -->
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w120">
                  <div class="check-action -color">
                    <input type="checkbox" class="check" v-model="eCboTenDT"/>
                    <span class="name">Tên đối tượng</span>
                  </div>
                </div>
                <div class="value">
                  <input type="text" value="" class="form-control" v-model="pTxtTenDT" :disabled="!eCboTenDT"/>
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w120">
              <div class="check-action -color">
                <input type="checkbox" class="check" v-model="eCboDiaChiDT"/>
                <span class="name">Địa chỉ</span>
              </div>
            </div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" v-b-modal.idDiaChiDoiTuong title="Địa chỉ" :disabled="!eCboDiaChiDT">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" class="form-control" v-model="pTxtDiaChiDT" :disabled="!eCboDiaChiDT"/>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-8 col-12">
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input type="checkbox" class="check" v-model="eCboNgaySN" />
                        <span class="name">Ngày sinh</span>
                      </div>
                    </div>
                    <div class="value">
                      <ejs-datepicker v-model="pTxtNgaySN" :value="today" :max="today" :format="'dd/MM/y'"
                                        :allowEdit="true" ref="txtNgaySN" placeholder="__/__/____">
                      </ejs-datepicker>
                      <!-- <input
                        type="text"
                        value="___/___/_____"
                        class="form-control"
                        v-model="pTxtNgaySN" :disabled="!eCboNgaySN"
                      /> -->
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input type="checkbox" class="check" v-model="eCboGioiTinh"/>
                        <span class="name">Giới tính</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="list-checks mart5">
                        <div class="item" style="width: 110px">
                          <div class="check-action">
                            <input type="radio" class="check" name="gioiTinh_TraCuu" :value="1" v-model="pRdoGTNam_TraCuu" :disabled="!eCboGioiTinh"/>
                            <span class="name">Nam</span>
                          </div>
                        </div>
                        <div class="item" style="width: 110px">
                          <div class="check-action">
                            <input type="radio" class="check" name="gioiTinh_TraCuu" :value="0" v-model="pRdoGTNam_TraCuu" :disabled="!eCboGioiTinh"/>
                            <span class="name">Nữ</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input type="checkbox" class="check" v-model="eCboQuocTich"/>
                        <span class="name">Quốc tịch</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="">
                        <select2 v-model="pQuocTichSelected"
                            ref="cboQuocTich"
                            :options="pDsQuocTich"
                            :disabled="!eCboQuocTich"
                        />
                        <!-- <b-form-select
                          :disabled="!eCboQuocTich"
                          v-model="pQuocTichSelected"
                          :options="pDsQuocTich"
                          value-field="quocTichId"
                          text-field="tenQuocTich"
                          class="form-control">
                        </b-form-select> -->
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input type="checkbox" class="check" v-model="eCboHonNhan"/>
                        <span class="name">Hôn nhân</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="list-checks mart5">
                        <div class="item" style="width: 110px">
                          <div class="check-action">
                            <input type="radio" class="check" :value="0" v-model="pRdoChuaKH" :disabled="!eCboHonNhan"/>
                            <span class="name">Chưa kết hôn</span>
                          </div>
                        </div>
                        <div class="item" style="width: 110px">
                          <div class="check-action">
                            <input type="radio" class="check" :value="1" v-model="pRdoChuaKH" :disabled="!eCboHonNhan"/>
                            <span class="name">Đã kết hôn</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input type="checkbox" class="check" v-model="eCboDienThoai"/>
                        <span class="name">Điện thoại</span>
                      </div>
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="pTxtDienThoai" :disabled="!eCboDienThoai"/>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input type="checkbox" class="check" v-model="eCboDanToc"/>
                        <span class="name">Dân tộc</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="">
                        <select2 v-model="pDanTocSelected"
                            ref="cboDanToc"
                            :options="pDsDanToc"
                            :disabled="!eCboDanToc"
                        />
                        <!-- <b-form-select
                          :disabled="!eCboDanToc"
                          v-model="pDanTocSelected"
                          :options="pDsDanToc"
                          value-field="danTocId"
                          text-field="tenDanToc"
                          class="form-control">
                        </b-form-select> -->
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input type="checkbox" class="check" v-model="eCboEmail"/>
                        <span class="name">Email</span>
                      </div>
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="pTxtEmail" :disabled="!eCboEmail"/>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input type="checkbox" class="check" v-model="eCboFax" />
                        <span class="name">Fax</span>
                      </div>
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="pTxtFax" :disabled="!eCboFax" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input type="checkbox" class="check" v-model="eCboTrinhDo"/>
                        <span class="name">Trình độ</span>
                      </div>
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="pTxtTrinhDo" :disabled="!eCboTrinhDo" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input type="checkbox" class="check" v-model="eCboNganhNghe"/>
                        <span class="name">Ngành nghề</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="">
                        <select2 v-model="pNganhNgheSelected"
                             ref="cboNganhNghe"
                            :options="pDsNganhNghe"
                            :disabled="!eCboNganhNghe"
                        />
                        <!-- <b-form-select
                          :disabled="!eCboNganhNghe"
                          v-model="pNganhNgheSelected"
                          :options="pDsNganhNghe"
                          value-field="NGANHNGHE_ID"
                          text-field="TENNGANHNGHE"
                          class="form-control">
                        </b-form-select> -->
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">
                  <div class="check-action -color">
                    <input type="checkbox" class="check" v-model="eCboBoPhanCT"/>
                    <span class="name">Bộ phận CT</span>
                  </div>
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="pTxtBoPhanCT" :disabled="!eCboBoPhanCT"/>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="check-action -color marl10 marb10">
                <input type="checkbox" class="check" v-model="eCboSoThich"/>
                <span class="name">Sở thích</span>
              </div>
              <div class="box-form" style="border-radius: 0px; box-shadow: 0px">
                <div
                  class="list-checks-box"
                  style="height: 155px; overflow: auto"
                >
                  <div class="item marb10" v-for="soThich in this.pDsSoThich" :key="soThich.soThichId">
                    <div class="check-action">
                      <input type="checkbox" class="check" v-model="soThich.CHECKED" :disabled="!eCboSoThich"/>
                      <span class="name">{{soThich.tenSoThich}}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách đối tượng chăm sóc</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="gridColumns"
              :gridLines="'Both'"
              ref="gridDsCSKH"
              dataKeyField="STT"
              :dataSource="pDsCSKH"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              @selectedRowChanged="onGridDsCSKH_RowSelected"
            >
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
  <!-- </b-modal> -->
    <ModalChonDiaChi modalId="idDiaChiDoiTuong" @xacnhan="getDiaChiKH"/>
</ejs-dialog>
</template>
<style>
#frmTraCuuTTCSKH .modal-dialog {
    max-width: 70%;
    height: 60vh;
    display: flex;
}
.legend-title {
    font-weight: 600 !important;
}
</style>
<script>
import moment from "moment";
import downloadexcel from "vue-json-excel";
import ModalChonDiaChi from "../../CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonDiaChi/Modal";

export default {
  name: "frmTraCuuTTCSKH",
  components: {downloadexcel,ModalChonDiaChi},
  data() {
    return {
      eCboLoaiDoiTuong: false,
      eCboQuocTich: false,
      eCboDanToc: false,
      eCboNganhNghe: false,
      eCboSoThich: false,
      eCboTenDT: false,
      eCboDiaChiDT: false,
      eCboNgaySN: false,
      eCboDienThoai: false,
      eCboFax: false,
      eCboEmail: false,
      eCboTrinhDo: false,
      eCboBoPhanCT: false,
      eCboGhiChu: false,
      eCboGioiTinh: false,
      eCboHonNhan: false,

      pTxtTenDT: '',
      pTxtDiaChiDT: '',
      pTxtNgaySN: '',
      pTxtDienThoai: '',
      pTxtFax: '',
      pTxtEmail: '',
      pTxtTrinhDo: '',
      pTxtBoPhanCT: '',
      pTxtGhiChu: '',

      today: new Date(),

      pRdoGTNam_TraCuu: 2,
      pRdoChuaKH: 2,

      pLoaiDoiTuongSelected: 0,
      pQuocTichSelected: 0,
      pDanTocSelected: 0,
      pNganhNgheSelected: 0,
      pSoThichSelected: 0,

      pDsDoiTuongChamSoc: [],
      pDsQuocTich: [],
      pDsDanToc: [],
      pDsNganhNghe: [],
      pDsSoThich: [],

      pDsCSKH: [],
      gridColumns: [
        {
          fieldName: "TENDOITUONG",
          headerText: "Tên đối tượng",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "DIACHI",
          headerText: "Địa chỉ",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "EMAIL",
          headerText: "Email",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "TRINHDO",
          headerText: "Trình độ",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "BOPHANCONGTAC",
          headerText: "Bộ phận",
          allowFiltering: true,
          allowSorting: true,
        },
      ],
      exportExcelFields: {
          "THONGTIN_ID": "THONGTIN_ID",
          "MA_KH": "MA_KH",
          "DOITUONGCS_ID": "DOITUONGCS_ID",
          "TENDOITUONG": "TENDOITUONG",
          "DIACHI": "DIACHI",
          "DIENTHOAI": "DIENTHOAI",
          "EMAIL": "EMAIL",
          "FAX": "FAX",
          "NGAYSINH": "NGAYSINH",
          "GIOITINH_ID": "GIOITINH_ID",
          "QUOCTICH_ID": "QUOCTICH_ID",
          "DANTOC_ID": "DANTOC_ID",
          "NGANHNGHE_ID": "NGANHNGHE_ID",
          "TRINHDO": "TRINHDO",
          "BOPHANCONGTAC": "BOPHANCONGTAC",
          "KETHON": "KETHON",
          "QUAN_ID": "QUAN_ID",
          "PHUONG_ID": "PHUONG_ID",
          "PHO_ID": "PHO_ID",
          "SO_NHA": "SO_NHA",
          "DONVI_ID": "DONVI_ID",
          "MAY_CN": "MAY_CN",
          "NGAY_CN": "NGAY_CN",
          "NGUOI_CN": "NGUOI_CN",
          "GHICHU": "GHICHU",
          "MA_KH_CS": "MA_KH_CS",
          "THONGTIN_ID1": "THONGTIN_ID1",
          "SOTHICH_ID": "SOTHICH_ID",
      },
    };
  },
  computed: {

  },
  methods: {
    async onShow() {
      this.pDsCSKH = []
      this.pDsDoiTuongChamSoc = []
      this.pDsQuocTich = []
      this.pDsDanToc = []
      this.pDsNganhNghe = []
      this.pDsSoThich = []

      this.eCboLoaiDoiTuong = false
      this.eCboQuocTich = false
      this.eCboDanToc = false
      this.eCboNganhNghe = false
      this.eCboSoThich = false
      this.eCboTenDT = false
      this.eCboDiaChiDT = false
      this.eCboNgaySN = false
      this.eCboDienThoai = false
      this.eCboFax = false
      this.eCboEmail = false
      this.eCboTrinhDo = false
      this.eCboBoPhanCT = false
      this.eCboGhiChu = false
      this.eCboGioiTinh = false
      this.eCboHonNhan = false

      this.pTxtTenDT = ''
      this.pTxtDiaChiDT = ''
      this.pTxtNgaySN = ''
      this.pTxtDienThoai = ''
      this.pTxtFax = ''
      this.pTxtEmail = ''
      this.pTxtTrinhDo = ''
      this.pTxtBoPhanCT = ''
      this.pTxtGhiChu = ''

      this.pRdoGTNam_TraCuu = 2
      this.pRdoChuaKH = 2

      this.pLoaiDoiTuongSelected = 0
      this.pQuocTichSelected = 0
      this.pDanTocSelected = 0
      this.pNganhNgheSelected = 0
      this.pSoThichSelected = 0
      try {
        this.$root.showLoading(true)
        await this.getDsDoiTuongChamSoc()
        await this.getDsQuocTich()
        await this.getDsNganhNghe()
        await this.getDsSoThich()
        if (this.pDsDoiTuongChamSoc.length > 0) this.pLoaiDoiTuongSelected = this.pDsDoiTuongChamSoc[0].id
        if (this.pDsNganhNghe.length > 0) this.pNganhNgheSelected = this.pDsNganhNghe[0].id
        if (this.pDsQuocTich.length > 0) this.pQuocTichSelected = this.pDsQuocTich[0].id
      }
      catch (e) {
      }
      finally {
        this.$root.showLoading(false)
      }
    },
    async getDsDoiTuongChamSoc() {
      try {
        this.pDsDoiTuongChamSoc = []
        let response = await this.$root.context.get('/web-cskh/api/danh-muc/loai-doi-tuong')
        if (response.error_code === "BSS-00000000") {
          // this.pDsDoiTuongChamSoc = response.data || []
          // this.pDsDoiTuongChamSoc.sort((a, b) => a.doiTuongCsId > b.doiTuongCsId ? 1 : -1)
          let ds = response.data || []
          ds.sort((a, b) => a.doiTuongCsId > b.doiTuongCsId ? 1 : -1 );
          ds.forEach(e => this.pDsDoiTuongChamSoc.push({ id: e.doiTuongCsId, text: e.tenDoiTuongCs }))
        }
      }
      catch (e) {
        this.pDsDoiTuongChamSoc = []
      }
    },
    async getDsQuocTich() {
      try {
        this.pDsQuocTich = [];
        let response = await this.$root.context.get('/web-cskh/api/danh-muc/quoc-tich')
        if (response.error_code === "BSS-00000000") {
          // this.pDsQuocTich = response.data || []
          // this.pDsQuocTich.sort((a, b) => a.quocTichId > b.quocTichId ? 1 : -1)
          let ds = response.data || []
          ds.sort((a, b) => a.quocTichId > b.quocTichId ? 1 : -1 );
          ds.forEach(e => this.pDsQuocTich.push({ id: e.quocTichId, text: e.tenQuocTich }))
        }
      }
      catch (e) {
        this.pDsQuocTich = []
      }
    },
    async getDsDanToc(id) {
      try {
        this.pDsDanToc = []
        let response = await this.$root.context.get('/web-cskh/api/danh-muc/dan-toc/' + id)
        if (response.error_code === "BSS-00000000") {
          // this.pDsDanToc = response.data || []
          // this.pDsDanToc.sort((a, b) => a.danTocId > b.danTocId ? 1 : -1)
          let ds = response.data || []
          ds.sort((a, b) => a.danTocId > b.danTocId ? 1 : -1 );
          ds.forEach(e => this.pDsDanToc.push({ id: e.danTocId, text: e.tenDanToc }))
        }
      }
      catch (e) {
        this.pDsDanToc = []
      }
    },
    async getDsNganhNghe() {
      try {
        this.pDsNganhNghe = []
        let response = await this.$root.context.get('/web-cskh/api/danh-muc/nganh-nghe')
        if (response.error_code === "BSS-00000000") {
          // this.pDsNganhNghe = response.data || []
          // this.pDsNganhNghe.forEach(e => e.CHECKED = false)
          // this.pDsNganhNghe.sort((a, b) => a.NGANHNGHE_ID > b.NGANHNGHE_ID ? 1 : -1)
          let ds = response.data || []
          ds.sort((a, b) => a.danTocId > b.danTocId ? 1 : -1 );
          ds.forEach(e => this.pDsNganhNghe.push({ id: e.NGANHNGHE_ID, text: e.TENNGANHNGHE }))
        }
      }
      catch (e) {
        this.pDsNganhNghe = []
      }
    },
    async getDsSoThich() {
      try {
        let response = await this.$root.context.get('/web-cskh/api/danh-muc/so-thich')
        if (response.error_code === "BSS-00000000") {
          this.pDsSoThich = response.data || []
          this.pDsSoThich.sort((a, b) => a.soThichId > b.soThichId ? 1 : -1)
        }
      }
      catch (e) {
        this.pDsSoThich = []
      }
    },
    async onTimKiemClicked() {
      await this.traCuu()
    },
    async traCuu() {
      try {
        this.pDsCSKH = []
        this.$root.showLoading(true)
        let data = {}
        if (this.eCboLoaiDoiTuong) data.doiTuongCsId = this.pLoaiDoiTuongSelected
        if (this.eCboTenDT) data.tenDoiTuong = this.pTxtTenDT.trim()
        if (this.eCboDiaChiDT) data.diaChi = this.pTxtDiaChiDT.trim()
        if (this.eCboDienThoai) data.dienThoai = this.pTxtDienThoai.trim()
        if (this.eCboEmail) data.email = this.pTxtEmail.trim()
        if (this.eCboFax) data.fax = this.pTxtFax.trim()
        if (this.eCboNgaySN) {
          let date = moment(this.pTxtNgaySN, "DD/MM/YYYY", true)
          if (date.isValid()) {
            data.ngaySinh =  moment(this.pTxtNgaySN).format("DD/MM/YYYY")
          }
          else {
            this.$root.toastError("Ngày sinh không hợp lệ!")
            return
          }
        }
        if (this.eCboGioiTinh) data.gioiTinhId = this.pRdoGTNam_TraCuu
        if (this.eCboQuocTich) data.quocTichId = this.pQuocTichSelected
        if (this.eCboDanToc) data.danTocId = this.pDanTocSelected
        if (this.eCboNganhNghe) data.nganhNgheId = this.pNganhNgheSelected
        if (this.eCboTrinhDo) data.trinhDo = this.pTxtTrinhDo.trim()
        if (this.eCboBoPhanCT) data.boPhanCongTac = this.pTxtBoPhanCT.trim()
        if (this.eCboHonNhan) data.ketHon = this.pRdoChuaKH
        if (this.eCboSoThich) data.dsSoThichId = this.pDsSoThich.filter(x => x.CHECKED).map(x => x.soThichId).join()
        let response = await this.$root.context.post('/web-cskh/api/cham-soc-khach-hang/tra-cuu?pageNo=1&pageSize=10000', data)
        if (response.error_code === "BSS-00000000") {
          this.pDsCSKH = response.data.data || []
        }
      }
      catch (e) {

      }
      finally {
        this.$root.showLoading(false)
      }
    },
    onGridDsCSKH_RowSelected(dataItem) {

    },
    onStartDownload() {
        if (this.pDsCSKH.length == 0) {
            this.$root.toastError('Không có dữ liệu để xuất Excel !')
            return
        }
    },
    async dialogOpen() {
      await this.onShow();
    },
    openDialog() {
        this.$refs.frmTraCuuTTCSKH.show();
    },
    closeDialog() {
        this.$refs.frmTraCuuTTCSKH.hide();
    },
    getDiaChiKH(data) {
      this.pTxtDiaChiDT = data.diachimoi || "";
    },
  },
  watch: {
    pQuocTichSelected: {
      handler: async function (value) {
        await this.getDsDanToc(value);
        if (this.pDsDanToc.length > 0) {
          this.pDanTocSelected = this.pDsDanToc[0].id
        }
      },
    },
  }
};
</script>
