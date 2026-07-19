<template src="./chonport.html"></template>
<style src='./chonport.scss'></style>
<script>
import ThueBaoCungCap from "../ThueBaoCungCap"
import ChonThueBao from "./chon-thuebao"
import BssErrorMarker from "@/components/BssErrorMarker"
import SearchAccount from "@/modules/search/subscriber/SearchAccount"
import { required } from "vuelidate/lib/validators"

export default {
  name: "chonport",
  components: { ChonThueBao, BssErrorMarker, ThueBaoCungCap, SearchAccount },
  validations: {
    txtOnuPass: {
      Text: {
        required
      }
    }
  },
  data () {
    return {
      ThueBaoCungCapData: null,
      madoicap: 0,
      tramvt_id: 0,
      tramtb_id: 0,
      tramql_id: 0,
      port_id: 0,
      kieu: 0,
      loaitb_id: 0,
      diachi_id: 0,
      vtham_so: "",
      dichvuvt_id: 0,
      hdtb_id: 0,
      thuebao_id: 0,
      _chedoChonSP: 0,
      ds_port_duoc_chon: [],
      cboCongNghe: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboTrangthai: {
        Key: 1,
        DataSource: [],
        Value: null
      },
      cboDonViQLTK: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboTramVT: {
        Key: 1,
        Value: null,
        Text: null
      },
      cboToQL: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboPhuong: {
        DataSource: [],
        Value: null,
        Enabled: false,
        EnableChangeEvent: true
      },
      cboNgo: {
        DataSource: [],
        Value: null,
        Enabled: false,
        EnableChangeEvent: true
      },
      cboKhu: {
        DataSource: [],
        Value: null,
        Text: null,
        Enabled: false,
        EnableChangeEvent: true
      },
      cboDuongPho: {
        DataSource: [],
        Value: null,
        Text: null,
        Enabled: false,
        EnableChangeEvent: true
      },
      cboVetinh: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboDslam: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboDownUpLink: {
        DataSource: [],
        Value: null
      },
      cboKetCuoi: {
        DataSource: [],
        Value: null,
        Enabled: false
      },
      cboViTri: {
        Key: 1,
        DataSource: [],
        Value: null,
        Enabled: false
      },
      chkKetCuoi: {
        Checked: false
      },
      chkViTri: {
        Checked: false
      },
      chkPhuongXa: {
        Checked: false,
        EnableChangeEvent: true
      },
      chkAp: {
        Enabled: false,
        Checked: false,
        EnableChangeEvent: true
      },
      chkKhu: {
        Enabled: false,
        Checked: false,
        EnableChangeEvent: true
      },
      chkPho: {
        Enabled: false,
        Checked: false,
        EnableChangeEvent: true
      },
      txtPort: {
        Text: null
      },
      txtRack: {
        Text: null
      },
      txtShelf: {
        Text: null
      },
      txtSlot: {
        Text: null
      },
      txtSystem: {
        Text: null
      },
      txtVci: {
        Text: null
      },
      txtVpi: {
        Text: null
      },
      txtIP: {
        Text: null
      },
      txtOnuPass: {
        Text: null
      },
      txtAdsl_Port: {
        Text: null
      },
      txtTKVlan: {
        Text: null
      },
      txtThuebao: {
        Text: null
      },
      txtKetCuoi: {
        Text: null
      },
      gridDanhSach: {
        DataSource: []
      },
      tsbtnCapNhat: {
        Enabled: false
      },
      LOAI_DV: {
        DONVIQL_LD: 3,
        TRAM_VT: 5,
        TRAM_PORT: 39
      },
      LOAI_TBI: {
        DSLAM: 1
      },
      DichVuVienThong: {
        TSL: 9,
        ADSL: 4
      },
      CHEDO_CHON_SPLITTER: {
        KoBatBuoc: 1, // Không bắt buộc phải chọn splitter
        BatBuocKhiDaChonSP: 2, // Chỉ bắt buộc chọn vị trí trên sp khi người dùng tick vào ô mà quên chưa tick chọn vị trí
        BatBuoc: 3 // Bắt buộc phải chọn splitter và vị trí nếu port được gán SP.
      },
      NHOMPHO: {
        DUONGPHO: 1,
        AP: 2,
        KHU: 3
      }
    }
  },
  computed: {
    chedoChonSP () {
      if (parseInt(this._chedoChonSP) == 1 && !this.chkKetCuoi.Checked) {
        this.chkKetCuoi.Checked = true
        this.chkViTri.Checked = true
      }

      switch (parseInt(this._chedoChonSP)) {
        case 1:
          return this.CHEDO_CHON_SPLITTER.BatBuoc
        case 2:
          return this.CHEDO_CHON_SPLITTER.BatBuocKhiDaChonSP
      }

      return this.CHEDO_CHON_SPLITTER.KoBatBuoc
    }
  },
  watch: {
    "chkKhu.Checked": function (value) {
      if (this.chkKhu.EnableChangeEvent) {
        this.chkKhu_CheckedChanged(value)
      } else {
        this.chkKhu.EnableChangeEvent = true
      }
    },
    "chkAp.Checked": function (value) {
      if (this.chkAp.EnableChangeEvent) {
        this.chkAp_CheckedChanged(value)
      } else {
        this.chkAp.EnableChangeEvent = true
      }
    },
    "chkPho.Checked": function (value) {
      if (this.chkPho.EnableChangeEvent) {
        this.chkPho_CheckedChanged(value)
      } else {
        this.chkPho.EnableChangeEvent = true
      }
    },
    "chkPhuongXa.Checked": function (value) {
      if (this.chkPhuongXa.EnableChangeEvent) {
        this.chkPhuongXa_CheckedChanged(value)
      } else {
        this.chkPhuongXa.EnableChangeEvent = true
      }
    },
    "cboPhuong.Value": function (value) {
      if (this.cboPhuong.EnableChangeEvent) {
        this.cboPhuong_EditValueChanged(value)
      } else {
        this.cboPhuong.EnableChangeEvent = true
      }
    },
    "cboDuongPho.Value": function (value) {
      if (this.cboDuongPho.EnableChangeEvent) {
        this.cboDuongPho_EditValueChanged(value)
      } else {
        this.cboDuongPho.EnableChangeEvent = true
      }
    },
    "cboNgo.Value": function (value) {
      if (this.cboNgo.EnableChangeEvent) {
        this.cboNgo_EditValueChanged(value)
      } else {
        this.cboNgo.EnableChangeEvent = true
      }
    },
    "cboKhu.Value": function (value) {
      if (this.cboKhu.EnableChangeEvent) {
        this.cboKhu_EditValueChanged(value)
      } else {
        this.cboKhu.EnableChangeEvent = true
      }
    },
    "cboDonViQLTK.Value": function (value) {
      if (this.cboDonViQLTK.EnableChangeEvent) {
        this.cboDonViQLTK_SelectedValueChanged(value)
      } else {
        this.cboDonViQLTK.EnableChangeEvent = true
      }
    },
    "cboToQL.Value": function (value) {
      if (this.cboToQL.EnableChangeEvent) {
        this.cboToQL_SelectedValueChanged(value)
      } else {
        this.cboToQL.EnableChangeEvent = true
      }
    },
    "cboVetinh.Value": function (value) {
      if (this.cboVetinh.EnableChangeEvent) {
        this.cboVetinh_SelectedValueChanged(value)
      } else {
        this.cboVetinh.EnableChangeEvent = true
      }
    },
    "cboCongNghe.Value": function (value) {
      if (this.cboCongNghe.EnableChangeEvent) {
        this.cboCongNghe_EditValueChanged(value)
      } else {
        this.cboCongNghe.EnableChangeEvent = true
      }
    },
    "cboDslam.Value": function (value) {
      this.cboDslam_SelectedValueChanged(value)
    },
    "chkKetCuoi.Checked": function (value) {
      this.chkKetCuoi_CheckedChanged(value)
    },
    "cboKetCuoi.Value": function (value) {
      this.cboKetCuoi_EditValueChanged(value)
    },
    "chkViTri.Checked": function (value) {
      this.chkViTri_CheckedChanged(value)
    }
  },
  methods: {
    show () {
      return this.$refs["ref-modal"].show()
    },
    hide () {
      this.$refs["ref-modal"].hide()
    },
    frmChonPort (_donvi_id, _vtramtb_id, _port_id, _kieu, _dichvuvt_id, _loaitb_id, _diachi_id, _thamso, _hdtb_id, _thuebao_id, _madoicap, _tramql_id) {
      this.tramvt_id = _donvi_id
      this.tramtb_id = _vtramtb_id
      this.port_id = _port_id
      this.kieu = _kieu
      this.loaitb_id = _loaitb_id
      this.diachi_id = _diachi_id
      this.vtham_so = _thamso
      this.dichvuvt_id = _dichvuvt_id
      this.hdtb_id = _hdtb_id
      this.thuebao_id = _thuebao_id
      this.madoicap = _madoicap
      this.tramql_id = _tramql_id
    },
    onModalHidden () {
      this.tramvt_id = 0
      this.tramtb_id = 0
      this.tramql_id = 0
      this.port_id = 0
      this.kieu = 0
      this.loaitb_id = 0
      this.diachi_id = 0
      this.vtham_so = ""
      this.dichvuvt_id = 0
      this.hdtb_id = 0
      this.thuebao_id = 0
      this._chedoChonSP = 0
      this.ds_port_duoc_chon = []
      this.cboCongNghe.DataSource = []
      this.cboCongNghe.Value = null
      this.cboCongNghe.EnableChangeEvent = true
      this.cboTrangthai.DataSource = []
      this.cboTrangthai.Value = null
      this.cboDonViQLTK.DataSource = []
      this.cboDonViQLTK.Value = null
      this.cboDonViQLTK.EnableChangeEvent = true
      this.cboTramVT.Value = null
      this.cboTramVT.Text = null
      this.cboToQL.DataSource = []
      this.cboToQL.Value = null
      this.cboToQL.EnableChangeEvent = true
      this.cboPhuong.DataSource = []
      this.cboPhuong.Value = null
      this.cboPhuong.Enabled = false
      this.cboPhuong.EnableChangeEvent = true
      this.cboNgo.DataSource = []
      this.cboNgo.Value = null
      this.cboNgo.Enabled = false
      this.cboNgo.EnableChangeEvent = true
      this.cboKhu.DataSource = []
      this.cboKhu.Value = null
      this.cboKhu.Enabled = false
      this.cboKhu.EnableChangeEvent = true
      this.cboDuongPho.DataSource = []
      this.cboDuongPho.Value = null
      this.cboDuongPho.Enabled = false
      this.cboDuongPho.EnableChangeEvent = true
      this.cboVetinh.DataSource = []
      this.cboVetinh.Value = null
      this.cboVetinh.EnableChangeEvent = true
      this.cboDslam.DataSource = []
      this.cboDslam.Value = null
      this.cboDslam.EnableChangeEvent = true
      this.cboDownUpLink.DataSource = []
      this.cboDownUpLink.Value = null
      this.cboKetCuoi.DataSource = []
      this.cboKetCuoi.Value = null
      this.cboKetCuoi.Enabled = false
      this.cboViTri.DataSource = []
      this.cboViTri.Value = null
      this.cboViTri.Enabled = false
      this.chkKetCuoi.Checked = false
      this.chkViTri.Checked = false
      this.chkPhuongXa.Checked = false
      this.chkPhuongXa.EnableChangeEvent = true
      this.chkAp.Enabled = false
      this.chkAp.Checked = false
      this.chkAp.EnableChangeEvent = true
      this.chkKhu.Enabled = false
      this.chkKhu.Checked = false
      this.chkKhu.EnableChangeEvent = true
      this.chkPho.Enabled = false
      this.chkPho.Checked = false
      this.chkPho.EnableChangeEvent = true
      this.txtPort.Text = null
      this.txtRack.Text = null
      this.txtShelf.Text = null
      this.txtSlot.Text = null
      this.txtSystem.Text = null
      this.txtVci.Text = null
      this.txtVpi.Text = null
      this.txtIP.Text = null
      this.txtOnuPass.Text = null
      this.txtAdsl_Port.Text = null
      this.txtTKVlan.Text = null
      this.txtThuebao.Text = null
      this.txtKetCuoi.Text = null
      this.gridDanhSach.DataSource = []
      this.tsbtnCapNhat.Enabled = false
      this.$v.$reset()
    },
    onModalShown () {
      ++this.cboDonViQLTK.Key
      ++this.cboToQL.Key
      ++this.cboTrangthai.Key
      ++this.cboVetinh.Key
      ++this.cboCongNghe.Key
      ++this.cboDslam.Key
      ++this.cboViTri.Key
      setTimeout(() => this.frmChonPort_FormLoad(), 300)
    },
    getColumnTemplateChon (parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input 
                                    type="checkbox" 
                                    class="check" 
                                    :value="data.key"
                                    v-model="parent.ds_port_duoc_chon"/>
                                <span class="name"></span>
                            </div>
                        `,
            data () {
              return {
                parent: parent,
                data: { }
              }
            }
          }
        }
      }
    },
    async frmChonPort_FormLoad () {
      try {
        this.loading(true)
        let dsTS = await this.lay_ds_thamso_md_mats("CHON_VITRI_KETCUOI")

        if (dsTS.length > 0) {
          this._chedoChonSP = parseInt(dsTS[0]["ten_ts"])
        }

        this.cboCongNghe.DataSource = await this.lay_ds_congnghe()

        if (this.cboCongNghe.DataSource.length > 0) {
          let congNgheId = null

          if (this.hdtb_id != 0 && this.dichvuvt_id != 0) {
            congNgheId = await this.lay_congnghe_id_theo_hdtb(this.dichvuvt_id, this.hdtb_id)
          }

          this.cboCongNghe.EnableChangeEvent = false

          if (!!congNgheId && this.cboCongNghe.DataSource.some(item => item.congnghe_id == congNgheId)) {
            this.cboCongNghe.Value = congNgheId
          }
        }

        this.cboTrangthai.DataSource = await this.lay_ds_trangthai_port()

        if (this.cboTrangthai.DataSource.length > 0) {
          this.cboTrangthai.Value = 1
        }

        if (this.tramvt_id) {
          let donViInfo = await this.lay_donvi_theo_id(this.tramvt_id)

          if (donViInfo) {
            this.cboTramVT.Value = donViInfo.donvi_id
            this.cboTramVT.Text = donViInfo.ten_dv
          }
        }

        this.cboDonViQLTK.DataSource = await this.lay_donvi_theo_loaidv(this.LOAI_DV.DONVIQL_LD)

        if (this.tramql_id) {
          let iTramVT_TBi_ID = await this.lay_donvi_ql(this.tramql_id, this.LOAI_DV.TRAM_PORT, this.LOAI_DV.TRAM_VT)

          if (iTramVT_TBi_ID) {
            let in_dvql = await this.lay_dvcha_theo_dvcon(iTramVT_TBi_ID, this.LOAI_DV.DONVIQL_LD)

            if (!!in_dvql && this.cboDonViQLTK.DataSource.some(item => item.donvi_id == in_dvql)) {
              this.cboDonViQLTK.EnableChangeEvent = false
              this.cboDonViQLTK.Value = in_dvql
              this.cboToQL.DataSource = await this.lay_dvcon_cua_dvcha_theo_nhieu_loai_dv(this.cboDonViQLTK.Value, this.LOAI_DV.TRAM_VT)

              if (this.cboToQL.DataSource.some(item => item.donvi_id == iTramVT_TBi_ID)) {
                this.cboToQL.EnableChangeEvent = false
                this.cboToQL.Value = iTramVT_TBi_ID
              }
            }
          }
        } else if (this.tramvt_id) {
          let in_dvql = await this.lay_dvcha_theo_dvcon(this.tramvt_id, this.LOAI_DV.DONVIQL_LD)

          if (!!in_dvql && this.cboDonViQLTK.DataSource.some(item => item.donvi_id == in_dvql)) {
            this.cboDonViQLTK.EnableChangeEvent = false
            this.cboDonViQLTK.Value = in_dvql
            this.cboToQL.DataSource = await this.lay_dvcon_cua_dvcha_theo_nhieu_loai_dv(this.cboDonViQLTK.Value, this.LOAI_DV.TRAM_VT)

            if (this.cboToQL.DataSource.some(item => item.donvi_id == this.tramvt_id)) {
              this.cboToQL.EnableChangeEvent = false
              this.cboToQL.Value = this.tramvt_id
            }
          }
        }

        if (!this.cboDonViQLTK.Value) {
          this.cboDonViQLTK.EnableChangeEvent = false
          this.cboDonViQLTK.Value = this.cboDonViQLTK.DataSource[0].donvi_id

          this.cboToQL.DataSource = await this.lay_dvcon_cua_dvcha_theo_nhieu_loai_dv(this.cboDonViQLTK.Value, this.LOAI_DV.TRAM_VT)

          if (this.cboToQL.DataSource.length > 0) {
            this.cboToQL.EnableChangeEvent = false
            this.cboToQL.Value = this.cboToQL.DataSource[0].donvi_id
          }
        }

        if (this.diachi_id != -1 && this.diachi_id != 0) {
          let diachi = await this.lay_thongtin_diachi(this.diachi_id)

          if (!!diachi && !!diachi["quan_id"]) {
            this.cboPhuong.DataSource = await this.lay_ds_phuong(diachi["quan_id"])

            if (!!diachi["phuong_id"] && this.cboPhuong.DataSource.some(item => item.phuong_id == diachi["phuong_id"])) {
              this.chkPhuongXa.EnableChangeEvent = false
              this.chkPhuongXa.Checked = true
              this.cboPhuong.Enabled = true
              this.chkPho.Enabled = true
              this.chkAp.Enabled = true
              this.chkKhu.Enabled = true

              this.cboPhuong.EnableChangeEvent = false
              this.cboPhuong.Value = diachi["phuong_id"]

              setTimeout(() => {
                this.$refs["ref-combo-phuong"].$children[0].selectRowByRowData(diachi["phuong_id"])
              }, 1000)

              this.cboDuongPho.DataSource = await this.lay_ds_pho(diachi["phuong_id"], this.NHOMPHO.DUONGPHO)

              if (!!diachi["pho_id"] && this.cboDuongPho.DataSource.some(item => item.pho_id == diachi["pho_id"])) {
                this.chkPho.EnableChangeEvent = false
                this.chkPho.Checked = true
                this.cboDuongPho.Enabled = true
                this.cboDuongPho.EnableChangeEvent = false
                this.cboDuongPho.Value = diachi["pho_id"]

                setTimeout(() => {
                  this.$refs["ref-combo-pho"].$children[0].selectRowByRowData(diachi["pho_id"])
                }, 1000)
              }

              this.cboNgo.DataSource = await this.lay_ds_pho(diachi["phuong_id"], this.NHOMPHO.AP)

              if (!!diachi["ap_id"] && this.cboNgo.DataSource.some(item => item.pho_id == diachi["ap_id"])) {
                this.chkAp.EnableChangeEvent = false
                this.chkAp.Checked = true
                this.cboNgo.Enabled = true
                this.cboNgo.EnableChangeEvent = false
                this.cboNgo.Value = diachi["ap_id"]

                setTimeout(() => {
                  this.$refs["ref-combo-ngo"].$children[0].selectRowByRowData(diachi["ap_id"])
                }, 1000)
              }

              this.cboKhu.DataSource = await this.lay_ds_pho(diachi["phuong_id"], this.NHOMPHO.KHU)

              if (!!diachi["khu_id"] && this.cboKhu.DataSource.some(item => item.pho_id == diachi["khu_id"])) {
                this.chkKhu.EnableChangeEvent = false
                this.chkKhu.Checked = true
                this.cboKhu.Enabled = true
                this.cboKhu.EnableChangeEvent = false
                this.cboKhu.Value = diachi["khu_id"]

                setTimeout(() => {
                  this.$refs["ref-combo-khu"].$children[0].selectRowByRowData(diachi["khu_id"])
                }, 1000)
              }
            }
          }
        }

        await this.ht_tram_thietbi()

        if (this.cboVetinh.DataSource.length > 0) {
          let veTinhId = this.tramtb_id
          let dslamtinhid = null

          if (this.port_id != 0) {
            dslamtinhid = await this.lay_dslam_id_theo_port(this.port_id)

            if (dslamtinhid) {
              let tramtbid = await this.lay_donvi_id_theo_dslam(dslamtinhid)

              if (tramtbid) {
                veTinhId = tramtbid
              }
            }
          }

          if (!!veTinhId && this.cboVetinh.DataSource.some(item => item.donvi_id == veTinhId)) {
            this.cboVetinh.EnableChangeEvent = false
            this.cboVetinh.Value = veTinhId
          } else {
            this.cboVetinh.EnableChangeEvent = false
            this.cboVetinh.Value = this.cboVetinh.DataSource[0].donvi_id
          }

          let congnghe_id = !this.cboCongNghe.Value ? 0 : this.cboCongNghe.Value
          this.cboDslam.DataSource = await this.ht_dslam_theo_tramtb_id(this.cboVetinh.Value, congnghe_id, this.vtham_so)

          if (this.cboDslam.DataSource.length > 0) {
            if (!!dslamtinhid && this.cboDslam.DataSource.some(item => item.dslam_id == dslamtinhid)) {
              this.cboDslam.EnableChangeEvent = false
              this.cboDslam.Value = dslamtinhid
            } else {
              this.cboDslam.EnableChangeEvent = false
              this.cboDslam.Value = this.cboDslam.DataSource[0].dslam_id
            }
          }

          await this.HienThiDanhSach(this.cboDslam.Value, this.port_id, this.kieu, this.loaitb_id)
        }
      } finally {
        this.loading(false)
      }
    },
    async HienThiDanhSach (dslam_id, port_id, kieu, loaitb_id) {
      try {
        this.loading(true)

        let dsPort = await this.lay_ds_port(dslam_id, port_id, kieu, loaitb_id)

        if (dsPort.length > 0) {
          dsPort.forEach((item, index) => item.key = index + 1)
        }

        this.ds_port_duoc_chon = []
        this.gridDanhSach.DataSource = dsPort

        if (this.gridDanhSach.DataSource.length > 0) {
          this.tsbtnCapNhat.Enabled = true
        } else {
          this.tsbtnCapNhat.Enabled = false
          this.txtIP.Text = ""
          this.txtOnuPass.Text = ""
          this.txtAdsl_Port.Text = ""
          this.txtPort.Text = ""
          this.txtRack.Text = ""
          this.txtShelf.Text = ""
          this.txtSlot.Text = ""
          this.txtSystem.Text = ""
          this.txtVci.Text = ""
          this.txtVpi.Text = ""
        }
      } finally {
        this.loading(false)
      }
    },
    gridviewDanhSach_QueryCellInfo (args) {
      if (args.column.field == "trangthai_port") {
        args.cell.style.color = "red"
      } else if (args.column.field == "port") {
        args.cell.style.color = "blue"
      }

      if (["slot", "port", "port_mdf", "trangthai_port"].includes(args.column.field)) {
        args.cell.style.fontWeight = "bold"
      }
    },
    async tsbtnChapNhan_Click () {
      let selectedRecords = this.$refs["ref-grid-danh-sach"].grid.getSelectedRecords()

      if (!selectedRecords || selectedRecords.length == 0) {
        this.$root.toastError("Bạn chưa chọn Port !")
        return
      }

      if (this.dichvuvt_id == this.DichVuVienThong.ADSL) {
        let message = await this.kiemtra_chon_port(this.hdtb_id, this.thuebao_id, this.cboDslam.Value)

        if (message) {
          this.$root.toastError(message)
          return
        }
      }

      let port = {...selectedRecords[0]}
      port.vadsl_port = this.txtAdsl_Port.Text
      port.onu_password = this.txtOnuPass.Text
      port.tramtb_id = this.cboVetinh.Value
      port.vitri = 0

      if (port.ketcuoi_id) {
        if ((this.chkKetCuoi.Checked && this.chedoChonSP == this.CHEDO_CHON_SPLITTER.BatBuocKhiDaChonSP) || this.chedoChonSP == this.CHEDO_CHON_SPLITTER.BatBuoc) {
          if (!this.cboViTri.Value) {
            this.$root.toastError("Bạn chưa chọn vị trí trên splitter!")
            return
          }

          port.vitri = parseInt(this.cboViTri.Value)
        }
      }

      port.ten_dslam = ""
      port.Thongtin_CT_Dslam = ""
      port.ip_dslam_tinh = "-1"
      port.thamso_dslam = "-1"

      if (this.cboDslam.DataSource.length > 0 && !!this.cboDslam.Value) {
        let dslam = this.cboDslam.DataSource.find(item => item.dslam_id == parseInt(this.cboDslam.Value))

        if (dslam) {
          port.Thongtin_CT_Dslam = dslam.thongtin_chitiet
          port.ten_dslam = dslam.tendslam
        }

        let dslamInfo = await this.lay_dslam_theo_id(this.cboDslam.Value)
        port.ip_dslam_tinh = dslamInfo["ip"]
        port.thamso_dslam = dslamInfo["thamso"]
      }

      port.dslam_id_vdc = 0
      port.bras_id_vdc = 0

      if (this.dichvuvt_id == this.DichVuVienThong.ADSL) {
        let tocDoInfo = await this.lay_tt_tocdo_thuebao(this.hdtb_id, this.thuebao_id, port.thamso_dslam)

        if (tocDoInfo) {
          port.dslam_id_vdc = tocDoInfo["dslam_id"]
          port.bras_id_vdc = tocDoInfo["bras_id"]
        } else {
          port.dslam_id_vdc = port.dslam_vdc_id

          let dslamInfo = await this.lay_dslam_theo_id(port.dslam_id_vdc)

          if (dslamInfo) {
            port.bras_id_vdc = dslamInfo["bras_id"]
          }
        }
      } else {
        port.dslam_id_vdc = port.dslam_vdc_id

        let dslamInfo = await this.lay_dslam_theo_id(port.dslam_id_vdc)

        if (dslamInfo) {
          port.bras_id_vdc = dslamInfo["bras_id"]
        }
      }

      this.$emit("select", port)
      this.hide()
    },
    async lay_tt_tocdo_thuebao (hdtb_id, thuebao_id, thamso_dslam) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/lay_tt_tocdo_thuebao", {hdtb_id, thuebao_id, thamso_dslam})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async kiemtra_chon_port (hdtb_id, thuebao_id, dslam_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/kiemtra_chon_port", {hdtb_id, thuebao_id, dslam_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data.message
        }

        throw {response: { data: response}}
      } catch (err) {
        return "Có lỗi xảy ra khi kiểm tra chọn port"
      }
    },
    async tsbtnCapNhat_Click () {
      if (!this.tsbtnCapNhat.Enabled) {
        return
      }

      let errorMessage = []

      if (parseInt(this.cboTrangthai.Value) == 0) {
        errorMessage.push("Bạn không được chọn trạng thái là 'Tất cả' !")
      }

      if (this.ds_port_duoc_chon.length <= 0) {
        errorMessage.push("Bạn chưa chọn port trong danh sách để cập nhật trạng thái !")
      }

      if (errorMessage.length > 0) {
        this.$root.toastError(errorMessage.join("\n"))
        return
      }

      try {
        this.loading(true)

        for (let i = 0; i < this.ds_port_duoc_chon.length; i++) {
          let port = this.gridDanhSach.DataSource.find(item => item.key == this.ds_port_duoc_chon[i])
          await this.capnhat_trangthai_port(port.port_id, port.vci_vpi_id, this.cboTrangthai.Value)
        }

        this.$root.toastSuccess("Cập nhật trạng thái port thành công !")
        this.Clean()
        await this.TimKiem_Port(0)
      } catch (err) {
        this.$root.toastError(err)
        return
      } finally {
        this.loading(false)
      }
    },
    async capnhat_trangthai_port (port_id, vci_vpi, trangthai) {
      let errorMessage = null

      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/capnhat_trangthai_port", {port_id, vci_vpi, trangthai})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          if (response.data.kq == 0) {
            errorMessage = response.data.message
          }
        } else {
          errorMessage = "Có lỗi xảy ra khi cập nhật trạng thái port"
        }
      } catch (err) {
        errorMessage = "Có lỗi xảy ra khi cập nhật trạng thái port"
      }

      if (errorMessage) {
        throw errorMessage
      }
    },
    tsbtnSinhPass_Click () {
      let maTinh = this.$root.token.getMaTinh()

      if (!["BDG", "DNI"].includes(maTinh)) {
        this.$root.toastError("Chức năng không được sử dụng cho tỉnh")
        return
      }

      let messages = []

      this.$v.$touch()

      if (!this.$v.txtOnuPass.Text.required) {
        messages.push("Hãy nhập Passonu")
        this.$refs["ref-txt-onu-pass"].focus()
      }

      let selectedRecords = this.$refs["ref-grid-danh-sach"].grid.getSelectedRecords()

      if (!selectedRecords || selectedRecords.length == 0) {
        messages.push("Hãy chọn port")
      }

      if (messages.length > 0) {
        this.$root.toastError(messages.join("\n"))
        return
      }

      this.$refs["ref-modal-xacnhan-sinh-pass"].show()
    },
    async okSinhPass () {
      let gridDanhSach = this.$refs["ref-grid-danh-sach"].grid
      let selectedRecord = gridDanhSach.getSelectedRecords()[0]

      let vrp_id = selectedRecord.rp_id
      let vvpi = selectedRecord.vpi
      let vonupass = this.txtOnuPass.Text

      try {
        this.loading(true)
        await this.capnhat_pass_onu(vrp_id, vvpi, vonupass)
        this.$v.$reset()
        this.$refs["ref-modal-xacnhan-sinh-pass"].hide()
        this.$root.toastSuccess("Cập nhật Passonu thành công!")
        let port = {...selectedRecord}
        port.onu_password = vonupass
        gridDanhSach.batchUpdate({changedRecords: [port]})
      } catch (err) {
        this.$root.toastError(err)
      } finally {
        this.loading(false)
      }
    },
    async capnhat_pass_onu (rp_id, vpi, pass_onu) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/capnhat_pass_onu", {rp_id, vpi, pass_onu})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          if (response.data.kq == 1) {
            return
          }

          throw "Có lỗi xảy khi cập nhật pass onu"
        }

        throw "Có lỗi xảy khi cập nhật pass onu"
      } catch (err) {
        throw err
      }
    },
    tsbtnTBCungDoiCap_Click () {
      this.ThueBaoCungCapData = {
        thuebao_id: this.thuebao_id,
        dichvuvt_id: this.dichvuvt_id,
        ma_tb: null,
        hdtb_id: this.hdtb_id,
        madoicap: this.madoicap
      }
      this.$bvModal.show("frm-chon-port-thue-bao-cung-cap")
    },
    async cboPhuong_EditValueChanged (phuongId) {
      if (!phuongId) {
        return
      }

      this.cboDuongPho.DataSource = await this.lay_ds_pho(phuongId, this.NHOMPHO.DUONGPHO)

      if (this.cboDuongPho.DataSource.length <= 0) {
        if (this.chkPho.Checked) {
          this.chkPho.EnableChangeEvent = false
          this.chkPho.Checked = false
          this.cboDuongPho.Enabled = false
        }
      } else if (this.cboDuongPho.Value != this.cboDuongPho.DataSource[0].pho_id) {
        this.cboDuongPho.EnableChangeEvent = false
        this.cboDuongPho.Value = this.cboDuongPho.DataSource[0].pho_id

        setTimeout(() => {
          this.$refs["ref-combo-pho"].$children[0].selectRowByRowData(this.cboDuongPho.Value)
        }, 300)
      }

      this.cboNgo.DataSource = await this.lay_ds_pho(phuongId, this.NHOMPHO.AP)

      if (this.cboNgo.DataSource.length <= 0) {
        if (this.chkAp.Checked) {
          this.chkAp.EnableChangeEvent = false
          this.chkAp.Checked = false
          this.cboNgo.Enabled = false
        }
      } else if (this.cboNgo.Value != this.cboNgo.DataSource[0].pho_id) {
        this.cboNgo.EnableChangeEvent = false
        this.cboNgo.Value = this.cboNgo.DataSource[0].pho_id

        setTimeout(() => {
          this.$refs["ref-combo-ngo"].$children[0].selectRowByRowData(this.cboNgo.Value)
        }, 300)
      }

      this.cboKhu.DataSource = await this.lay_ds_pho(phuongId, this.NHOMPHO.KHU)

      if (this.cboKhu.DataSource.length <= 0) {
        if (this.chkKhu.Checked) {
          this.chkKhu.EnableChangeEvent = false
          this.chkKhu.Checked = false
          this.cboKhu.Enabled = false
        }
      } else if (this.cboKhu.Value != this.cboKhu.DataSource[0].pho_id) {
        this.cboKhu.EnableChangeEvent = false
        this.cboKhu.Value = this.cboKhu.DataSource[0].pho_id

        setTimeout(() => {
          this.$refs["ref-combo-khu"].$children[0].selectRowByRowData(this.cboKhu.Value)
        }, 300)
      }

      await this.ht_tram_thietbi()
    },
    async cboDuongPho_EditValueChanged (duongPhoId) {
      await this.ht_tram_thietbi()
    },
    async cboNgo_EditValueChanged (ngoId) {
      await this.ht_tram_thietbi()
    },
    async cboKhu_EditValueChanged (khuId) {
      await this.ht_tram_thietbi()
    },
    async lay_ds_pho (phuong_id, nhompho_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/lay_ds_pho", {phuong_id, nhompho_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    chkViTri_CheckedChanged (isCheck) {
      this.cboViTri.Enabled = isCheck
    },
    chkPhuongXa_CheckedChanged (isCheck) {
      if (isCheck) {
        this.cboPhuong.Enabled = true

        this.chkPho.Checked = false
        this.chkPho.Enabled = true
        this.cboDuongPho.Enabled = false

        this.chkAp.Checked = false
        this.chkAp.Enabled = true
        this.cboNgo.Enabled = false

        this.chkKhu.Checked = false
        this.chkKhu.Enabled = true
        this.cboKhu.Enabled = false
      } else {
        this.cboPhuong.Enabled = false

        this.chkPho.Checked = false
        this.chkPho.Enabled = false
        this.cboDuongPho.Enabled = false

        this.chkAp.Checked = false
        this.chkAp.Enabled = false
        this.cboNgo.Enabled = false

        this.chkKhu.Checked = false
        this.chkKhu.Enabled = false
        this.cboKhu.Enabled = false
      }

      this.ht_tram_thietbi()
    },
    chkPho_CheckedChanged (isCheck) {
      this.cboDuongPho.Enabled = isCheck
      this.ht_tram_thietbi()
    },
    chkAp_CheckedChanged (isCheck) {
      this.cboNgo.Enabled = isCheck
      this.ht_tram_thietbi()
    },
    chkKhu_CheckedChanged (isCheck) {
      this.cboKhu.Enabled = isCheck
      this.ht_tram_thietbi()
    },
    async cboCongNghe_EditValueChanged (congNgheId) {
      if (!congNgheId || !this.cboVetinh.Value) {
        return
      }

      this.cboDslam.DataSource = await this.ht_dslam_theo_tramtb_id(this.cboVetinh.Value, congNgheId, this.vtham_so)

      if (this.cboDslam.DataSource.length <= 0) {
        this.txtPort.Text = ""
        this.txtRack.Text = ""
        this.txtShelf.Text = ""
        this.txtSlot.Text = ""
        this.txtSystem.Text = ""
        this.txtVci.Text = ""
        this.txtVpi.Text = ""
        this.txtIP.Text = ""
        this.ds_port_duoc_chon = []
        this.gridDanhSach.DataSource = []
      } else {
        this.cboDslam.Value = this.cboDslam.DataSource[0].dslam_id
      }
    },
    chkKetCuoi_CheckedChanged (isChecked) {
      this.cboKetCuoi.Enabled = isChecked
    },
    async cboKetCuoi_EditValueChanged (ketCuoiId) {
      if (!ketCuoiId) {
        this.cboViTri.DataSource = []
        return
      }

      this.cboViTri.DataSource = await this.lay_ds_vitri_nguon(ketCuoiId)

      if (this.cboViTri.DataSource.length > 0) {
        this.cboViTri.Value = this.cboViTri.DataSource[0].id
      }
    },
    gridviewDanhSach_FocusedRowChanged (data) {
      if (!data) {
        return
      }

      this.txtSystem.Text = data["system"]
      this.txtRack.Text = data["rack"]
      this.txtSlot.Text = data["slot"]
      this.txtShelf.Text = data["shelf"]
      this.txtPort.Text = data["port"]
      this.txtVpi.Text = data["vpi"]
      this.txtVci.Text = data["vci"]
      this.txtAdsl_Port.Text = data["adsl_port"]
      this.txtOnuPass.Text = data["onu_password"]

      if (data["port_id"]) {
        this.port_id = data["port_id"]
      }

      if (this.chedoChonSP != this.CHEDO_CHON_SPLITTER.KoBatBuoc) {
        if (!!data["ketcuoi_id"] && this.cboKetCuoi.DataSource.some(item => item.ketcuoi_id == data["ketcuoi_id"])) {
          this.cboKetCuoi.Value = data["ketcuoi_id"]
        } else {
          this.cboKetCuoi.Value = null
        }
      }
    },
    async lay_ds_vitri_nguon (ketcuoi_id) {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_ds_vitri_nguon", {ketcuoi_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    acceptSearchAccount (item) {
      this.$refs["popupSearchAccount"].hide()
      this.txtThuebao.Text = item.ma_tb
      this.txtThuebao_KeyPress()
    },
    btnThueBao_Click () {
      this.$refs["popupSearchAccount"].show()
    },
    async txtTKVlan_KeyPress () {
      let dsTraCuuTB = []

      try {
        this.loading(true)
        dsTraCuuTB = await this.lay_ds_thuebao_theo_ma_tb(this.txtTKVlan.Text, 0)
      } finally {
        this.loading(false)
      }

      if (dsTraCuuTB.length == 0) {
        this.$root.toastError("Không tìm thấy thông tin thuê bao " + this.txtThuebao.Text + " trong danh bạ !")
        return
      }

      if (dsTraCuuTB.length == 1) {
        this.onSelectThueBaoVlan(dsTraCuuTB[0])
      } else {
        let modal = this.$refs["ref-chon-thue-bao-vlan"]
        modal.setDsThueBao(dsTraCuuTB)
        modal.show()
      }
    },
    onSelectThueBaoVlan (data) {
      this.HienThiDanhSach_QuyHoach_vci_vpi()
    },
    HienThiDanhSach_QuyHoach_vci_vpi () {
      this.txtIP.Text = ""
      this.txtOnuPass.Text = ""
      this.txtAdsl_Port.Text = ""
      this.txtPort.Text = ""
      this.txtRack.Text = ""
      this.txtShelf.Text = ""
      this.txtSlot.Text = ""
      this.txtSystem.Text = ""
      this.txtVci.Text = ""
      this.txtVpi.Text = ""
      this.tsbtnCapNhat.Enabled = false
    },
    async txtThuebao_KeyPress () {
      let dsTraCuuTB = []

      try {
        this.loading(true)
        dsTraCuuTB = await this.lay_ds_thuebao_theo_ma_tb(this.txtThuebao.Text, 0)
      } finally {
        this.loading(false)
      }

      if (dsTraCuuTB.length == 0) {
        this.$root.toastError("Không tìm thấy thông tin thuê bao " + this.txtThuebao.Text + " trong danh bạ !")
        return
      }

      if (dsTraCuuTB.length == 1) {
        this.onSelectThueBao(dsTraCuuTB[0])
      } else {
        let modal = this.$refs["ref-chon-thue-bao"]
        modal.setDsThueBao(dsTraCuuTB)
        modal.show()
      }
    },
    async onSelectThueBao (thueBaoInfo) {
      if (thueBaoInfo.ketcuoi_id) {
        let ketCuoiInfo = await this.lay_ketcuoi_theo_id(thueBaoInfo.ketcuoi_id)
        this.txtKetCuoi.Text = !ketCuoiInfo ? null : ketCuoiInfo.ten_kc
      }

      let l_port_id = thueBaoInfo["port_id"]

      if (thueBaoInfo["dichvuvt_id"] == this.DichVuVienThong.TSL) {
        let kieuDvVaPort = await this.lay_kieudv_va_port_cua_tb(thueBaoInfo.thuebao_id, this.LOAI_DV.TRAM_VT, this.cboTramVT.Value)

        if (!kieuDvVaPort["kieudv_id"]) {
          this.$root.toastError("Thuê bao không thuộc tổ thi công " + this.cboTramVT.Text + " !")
          return
        }

        l_port_id = kieuDvVaPort["port_id"]
      }

      if (!l_port_id || l_port_id == -1) {
        this.$root.toastError("Không tìm thấy thông tin port của thuê bao " + this.txtThuebao.Text + " !")
        return
      }

      let dslamtinhid = await this.lay_dslam_id_theo_port(l_port_id)

      if (!dslamtinhid) {
        await this.HienThiDanhSach(this.cboDslam.Value, l_port_id, 3, thueBaoInfo["loaitb_id"])
        return
      }

      let tramtbid = await this.lay_donvi_id_theo_dslam(dslamtinhid)

      if (!!tramtbid && this.cboVetinh.DataSource.some(item => item.donvi_id == tramtbid)) {
        if (this.cboVetinh.Value != tramtbid) {
          this.cboVetinh.EnableChangeEvent = false
          this.cboVetinh.Value = tramtbid
          let congnghe_id = !this.cboCongNghe.Value ? 0 : this.cboCongNghe.Value
          this.cboDslam.DataSource = await this.ht_dslam_theo_tramtb_id(tramtbid, congnghe_id, this.vtham_so)
        }

        if (this.cboDslam.DataSource.length == 0) {
          return
        }

        if (this.cboDslam.DataSource.some(item => item.dslam_id == dslamtinhid)) {
          if (this.cboDslam.Value != dslamtinhid) {
            this.cboDslam.EnableChangeEvent = false
            this.cboDslam.Value = dslamtinhid

            // Lấy thông số DSLAM
            let dslamInfo = await this.lay_dslam_theo_id(this.cboDslam.Value)
            this.txtIP.Text = !dslamInfo ? "" : dslamInfo["ip"]

            // Hiển thị danh sách spliter
            this.cboKetCuoi.DataSource = await this.lay_ds_spliter_theo_dslam(this.cboDslam.Value)
            this.cboKetCuoi.Enabled = false
            this.chkKetCuoi.Checked = false
            this.chkViTri.Checked = false
            this.cboViTri.Enabled = false

            // Hiển thị danh sách Downlink Uplink
            this.cboDownUpLink.DataSource = await this.lay_ds_down_uplink(this.cboDslam.Value)
          }

          await this.HienThiDanhSach(this.cboDslam.Value, l_port_id, 3, thueBaoInfo["loaitb_id"])
        } else {
          setTimeout(() => { this.$refs["ref-cbo-dslam"].$children[0].select2.select2("open") }, 200)
        }
      }
    },
    async lay_kieudv_va_port_cua_tb (thuebao_id, loaidv_id, donvi_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/lay_kieudv_va_port_cua_tb", {thuebao_id, loaidv_id, donvi_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async lay_ketcuoi_theo_id (ketcuoi_id) {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_ketcuoi_theo_id", {ketcuoi_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async lay_ds_thuebao_theo_ma_tb (ma_tb, dichvuvt_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/lay_ds_thuebao_theo_ma_tb", {ma_tb, dichvuvt_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async cboDonViQLTK_SelectedValueChanged (donViQlTkId) {
      if (donViQlTkId) {
        this.cboToQL.DataSource = await this.lay_dvcon_cua_dvcha_theo_nhieu_loai_dv(donViQlTkId, this.LOAI_DV.TRAM_VT)

        if (this.cboToQL.DataSource.length > 0) {
          this.cboToQL.Value = this.cboToQL.DataSource[0].donvi_id
        }
      }
    },
    cboToQL_SelectedValueChanged (toQlId) {
      if (toQlId) {
        this.ht_tram_thietbi()
      }
    },
    async cboDslam_SelectedValueChanged (dslamId) {
      this.Clean()

      if (!dslamId) {
        this.cboKetCuoi.DataSource = []
        this.cboDownUpLink.DataSource = []
        this.ds_port_duoc_chon = []
        this.gridDanhSach.DataSource = []
        this.tsbtnCapNhat.Enabled = false
        return
      }

      // Lấy thông số DSLAM
      let dslamInfo = await this.lay_dslam_theo_id(dslamId)
      this.txtIP.Text = !dslamInfo ? "" : dslamInfo["ip"]

      await this.HienThiDanhSach(dslamId, 0, this.kieu, this.loaitb_id)

      // Hiển thị danh sách spliter
      this.cboKetCuoi.DataSource = await this.lay_ds_spliter_theo_dslam(dslamId)
      this.cboKetCuoi.Enabled = false
      this.chkKetCuoi.Checked = false
      this.chkViTri.Checked = false
      this.cboViTri.Enabled = false

      // Hiển thị danh sách Downlink Uplink
      this.cboDownUpLink.DataSource = await this.lay_ds_down_uplink(dslamId)
    },
    async lay_ds_down_uplink (dslam_id) {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_ds_down_uplink", {dslam_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async lay_ds_spliter_theo_dslam (dslam_id) {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_ds_spliter_theo_dslam", {dslam_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async lay_dslam_theo_id (dslam_id) {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_dslam_theo_id", {dslam_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async cboVetinh_SelectedValueChanged (veTinhid) {
      if (!veTinhid) {
        this.cboDslam.DataSource = []
        return
      }

      let congnghe_id = !this.cboCongNghe.Value ? 0 : this.cboCongNghe.Value
      this.cboDslam.DataSource = await this.ht_dslam_theo_tramtb_id(veTinhid, congnghe_id, this.vtham_so)

      if (this.cboDslam.DataSource.length > 0) {
        setTimeout(() => { this.$refs["ref-cbo-dslam"].$children[0].select2.select2("open") }, 200)
      }
    },
    tsbtnNhapMoi_Click () {
      this.Clean()
    },
    Clean () {
      this.txtSystem.Text = ""
      this.txtRack.Text = ""
      this.txtSlot.Text = ""
      this.txtIP.Text = ""
      this.txtShelf.Text = ""
      this.txtPort.Text = ""
      this.txtVpi.Text = ""
      this.txtVci.Text = ""
      this.txtAdsl_Port.Text = ""
      this.txtOnuPass.Text = ""
      this.txtTKVlan.Text = ""
      this.txtThuebao.Text = ""
      this.txtKetCuoi.Text = ""
    },
    async txtRack_KeyPress () {
      try {
        this.loading(true)
        await this.TimKiem_Port(0)
      } finally {
        this.loading(false)
      }
    },
    async txtShelf_KeyPress () {
      try {
        this.loading(true)
        await this.TimKiem_Port(0)
      } finally {
        this.loading(false)
      }
    },
    async txtSlot_KeyPress () {
      try {
        this.loading(true)
        await this.TimKiem_Port(0)
      } finally {
        this.loading(false)
      }
    },
    async txtSystem_KeyPress () {
      try {
        this.loading(true)
        await this.TimKiem_Port(0)
      } finally {
        this.loading(false)
      }
    },
    async btnLayTTMoi_Click () {
      try {
        this.loading(true)
        await this.TimKiem_Port(1)
      } finally {
        this.loading(false)
      }
    },
    async TimKiem_Port (kieu_tk) {
      if (this.cboDslam.DataSource.length <= 0 || !this.cboDslam.Value) {
        this.$root.toastError("Hãy chọn Dslam!")
        return
      }

      let vdslam_id = this.cboDslam.Value
      let vslot = -1
      let vport = -1
      let vsystem = 0
      let vrack = -1
      let vshelf = -1
      let vvci = -1
      let vvpi = -1
      let vthamso = 0
      let vtrangthai = this.cboTrangthai.Value

      if (kieu_tk != 1) {
        if (this.txtSlot.Text) {
          vslot = this.txtSlot.Text
        }

        if (this.txtPort.Text) {
          vport = this.txtPort.Text
        }

        if (this.txtSystem.Text) {
          vsystem = this.txtSystem.Text
        }

        if (this.txtRack.Text) {
          vrack = this.txtRack.Text
        }

        if (this.txtShelf.Text) {
          vshelf = this.txtShelf.Text
        }

        if (this.txtVci.Text) {
          vvci = this.txtVci.Text
        }

        if (this.txtVpi.Text) {
          vvpi = this.txtVpi.Text
        }
      }

      let dsPort = await this.tracuu_ds_port(vdslam_id, vslot, vport, vsystem, vrack, vshelf, vvci, vvpi, vthamso, vtrangthai, this.loaitb_id)

      if (dsPort.length > 0) {
        dsPort.forEach((item, index) => item.key = index + 1)
      }

      this.ds_port_duoc_chon = []
      this.gridDanhSach.DataSource = dsPort

      if (this.gridDanhSach.DataSource.length > 0) {
        this.tsbtnCapNhat.Enabled = true
      } else {
        this.tsbtnCapNhat.Enabled = false
        this.Clean()
      }
    },
    async tracuu_ds_port (dslam_id, slot, port, system, rack, shelf, vci, vpi, thamso, trangthai, loaitb_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/tracuu_ds_port", {dslam_id, slot, port, system, rack, shelf, vci, vpi, thamso, trangthai, loaitb_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async lay_ds_port (dslam_id, port_id, kieu_id, loaitb_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/lay_ds_port", {dslam_id, port_id, kieu_id, loaitb_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async ht_dslam_theo_tramtb_id (donvi_id, congnghe_id, thamso) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/ht_dslam_theo_tramtb_id", {donvi_id, congnghe_id, thamso})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async lay_donvi_theo_id (donvi_id) {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_donvi_theo_id", {donvi_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async lay_donvi_id_theo_dslam (dslam_id) {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_dslam_theo_id", {dslam_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data.donvi_id
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async lay_dslam_id_theo_port (port_id) {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_dslam_id_theo_port", {port_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data.dslam_id
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async ht_tram_thietbi () {
      let vphuong_id = 0
      let vpho_id = 0
      let vap_id = 0
      let vkhu_id = 0

      if (this.chkPhuongXa.Checked && !!this.cboPhuong.Value) {
        vphuong_id = parseInt(this.cboPhuong.Value)
      }

      if (this.chkPho.Checked && !!this.cboDuongPho.Value) {
        vpho_id = parseInt(this.cboDuongPho.Value)
      }

      if (this.chkAp.Checked && !!this.cboNgo.Value) {
        vap_id = parseInt(this.cboNgo.Value)
      }

      if (this.chkKhu.Checked && !!this.cboKhu.Value) {
        vkhu_id = parseInt(this.cboKhu.Value)
      }

      let tramtc_id = 0

      if (this.cboToQL.Value) {
        tramtc_id = parseInt(this.cboToQL.Value)
      } else {
        tramtc_id = parseInt(this.cboTramVT.Value)
      }

      this.cboVetinh.DataSource = await this.lay_ds_vetinh(tramtc_id, this.LOAI_DV.TRAM_PORT, vphuong_id, vpho_id, vap_id, vkhu_id)

      if (this.cboVetinh.DataSource.length <= 0) {
        this.cboVetinh.Value = null
        this.cboDslam.DataSource = []
        this.ds_port_duoc_chon = []
        this.gridDanhSach.DataSource = []
      } else {
        this.cboVetinh.Value = this.cboVetinh.DataSource[0].donvi_id
      }
    },
    onSelectDownUpLink (data) {

    },
    onSelectKetCuoi (data) {
      this.cboKetCuoi.Value = !data ? null : data.ketcuoi_id
    },
    onSelectPhuong (data) {
      this.cboPhuong.Value = !data ? null : data.phuong_id
    },
    onSelectPho (data) {
      this.cboDuongPho.Value = !data ? null : data.pho_id
    },
    onSelectNgo (data) {
      this.cboNgo.Value = !data ? null : data.pho_id
    },
    onSelectKhu (data) {
      this.cboKhu.Value = !data ? null : data.pho_id
    },
    async lay_ds_vetinh (donvi_id, loaidv_id, phuong_id, pho_id, ap_id, khu_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/lay_ds_vetinh", {donvi_id, loaidv_id, phuong_id, pho_id, ap_id, khu_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async lay_ds_phuong (quan_id) {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_ds_phuong", {quan_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async lay_thongtin_diachi (diachi_id) {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_thongtin_diachi", {diachi_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async lay_dvcon_cua_dvcha_theo_nhieu_loai_dv (donvi_id, loaidv_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/lay_dvcon_cua_dvcha_theo_nhieu_loai_dv", {donvi_id, loaidv_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async lay_donvi_ql (donvi_id, loaidv_id, loaidv_ql_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/lay_donvi_ql", {donvi_id, loaidv_id, loaidv_ql_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data.donvi_ql_id
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async lay_dvcha_theo_dvcon (donvi_id, loaidv_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/lay_dvcha_theo_dvcon", {donvi_id, loaidv_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data.donvi_id
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async lay_donvi_theo_loaidv (loai_dv) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_donvi_theo_loaidv?loai_dv=${loai_dv}`)

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async lay_ds_trangthai_port () {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_ds_trangthai_port")

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return [{tt_port_id: 0, trangthai_port: "-- Tất cả --"}].concat(response.data)
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async lay_congnghe_id_theo_hdtb (dichvuvt_id, hdtb_id) {
      try {
        let response = await this.$root.context.post("/web-quantri/chon-port/lay_congnghe_id_theo_hdtb", {dichvuvt_id, hdtb_id})

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data.congnghe_id
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async lay_ds_congnghe () {
      try {
        let response = await this.$root.context.get("/web-quantri/chon-port/lay_ds_congnghe")

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data.length == 0 ? [] : [{congnghe: " ", congnghe_id: 0}].concat(response.data)
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    },
    async lay_ds_thamso_md_mats (ma_ts) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_thamso_md_mats?ma_ts=${ma_ts}`)

        if (response.error_code === "BSS-00000000" && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return []
      }
    }
  }
}
</script>
<style>
    .frm-chon-port .disable-btn:hover {
        background-color: white !important;
    }

    .frm-chon-port .disable-btn a {
        color: #6c757d !important;
    }

    .frm-chon-port .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }

    .frm-chon-port.modal-body .page-content {
        position: inherit !important;
    }

    .frm-chon-port #container-cong-nghe .select2-container--open ul.select2-results__options li:first-child {
        height: 30px;
    }

    .frm-chon-port .check-action:focus-within {
        border-width: 1px;
        border-color: #80bdff;
        -webkit-box-shadow: 0 0 0 3px rgb(0 123 255 / 25%);
        box-shadow: 0 0 0 3px rgb(0 123 255 / 25%);
    }
</style>
