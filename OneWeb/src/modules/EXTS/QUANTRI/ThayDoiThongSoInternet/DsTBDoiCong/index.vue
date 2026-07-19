<template src="./index.html"></template>
<script>
import vdcService from "../../../../ECMS/capnhat-port/service/visaService";
import vascService from "../../../HOPDONG/DongBoVisaVasc/service/vascService";
// constant
const
  DONGBO_TEST = {
    TEST: false
  },
  TRANGTHAI_DONGBO = {
    DONGBO_CM:5,
  },
  LoaiHinhTB = {
    INTERNET_FTTH:58,
    INTERNET_MYTV:61,
    MEGA:11,
 },
  DichVuVienThong = {
    METRONET:7,
    MEGAWAN:8,
    IMS:11,
  }
export default {
  name: "DS_TB_DOICONG",
  data:function (){
    return {
      dt:[],
      promiseResolve: null
    }
  },
  methods: {
    onModalHidden() {
      this.promiseResolve()
      this.promiseResolve = null
    },
    show(ds) {
      this.dt = ds
      this.$refs['DS_TB_DOICONG'].show()
      return new Promise(resolve => this.promiseResolve = resolve)
    },
    hide() {
      this.$refs['DS_TB_DOICONG'].hide()
    },
    queryCellInfo(args) {
      switch (args.column.field) {
        case 'ketqua_kh':
          if (args.data.ketqua_kh == "Thất bại.")
            args.cell.style.color = 'red'
      }
    },
    async tsbtnKichHoat_Click() {
      await this.DoiCongAcccountVisaVasc()
    },
    async DoiCongAcccountVisaVasc() {
      try {
        let selectedrowindex = this.$refs['dsDoiCong'].grid.getSelectedRowIndexes();
        console.log(selectedrowindex)
        for (let i = 0; i < this.dt.length; i++) {
          if (selectedrowindex.includes(i))
            this.dt[i].selected = 1
          else this.dt[i].selected = 0
        }
        this.loading(true)
        if (DONGBO_TEST.TEST == false) {
          vdcService.setupRoot(this.$root)
          let login = {
            userId: this.$root.context.user.getProperty('user_visa'),
            password: this.$root.context.user.getProperty('pass_visa')
          }
          vdcService.setLogin(login)

          let _visa = this.dt.filter(i => i.loaitb_id == LoaiHinhTB.INTERNET_FTTH && i.selected == 1 && i.status == TRANGTHAI_DONGBO.DONGBO_CM)
          let _vasc = this.dt.filter(i => i.loaitb_id == LoaiHinhTB.INTERNET_MYTV && i.selected == 1 && i.status == TRANGTHAI_DONGBO.DONGBO_CM)
          if (_visa.length > 0) {
            for (let i = 0; i < _visa.length; i++) {
              let account = _visa[i].ma_tb
              let port = _visa[i].port
              let vci = _visa[i].vci
              let vpi = _visa[i].vpi
              let slot = _visa[i].slot
              let brasId = _visa[i].bras_vdc_id

              let dslamId = _visa[i].dslam_id
              let dslam_vdc_Id = _visa[i].dslam_vdc_id

              //Tham so them cho dang ky 7 tham so
              let systemId = _visa[i].system
              let rackId = _visa[i].rack
              let shelfId = _visa[i].shelf
              // them cho dang ky 9 tham so
              let self01 = _visa[i].selfid01
              let self02 = _visa[i].selfid02
              let self03 = _visa[i].selfid03
              let self04 = _visa[i].selfid04
              //Kiem tra xem dang ky 4 tham so hay 7 tham so
              let thamso = await this.$root.context.get("web-quantri/ds-tb-doicong/map-id-thamso_dslam", {dslam_id: dslamId})
              thamso = thamso.data
              if (thamso == 4) {
                if (!await this.DoiCongMegavnn4(account, port, vci, vpi, slot, brasId, dslam_vdc_Id)) {
                  _visa[i].ketqua_kh = 'Thất bại.'
                  _visa[i].trangthai = -1
                } else {
                  _visa[i].ketqua_kh = 'Thành công.'
                  _visa[i].trangthai = 1
                }
              } else if (thamso == 7) {
                if (!await this.DoiCongMegavnn7(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, rackId, shelfId)) {
                  _visa[i].ketqua_kh = 'Thất bại.'
                  _visa[i].trangthai = -1
                } else {
                  _visa[i].ketqua_kh = 'Thành công.'
                  _visa[i].trangthai = 1
                }
              } else if (thamso == 9) {
                if (_visa[i].loaitb_id == LoaiHinhTB.MEGA) {
                  if (!await this.DoiCongMegavnn9_Mega(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04)) {
                    _visa[i].ketqua_kh = 'Thất bại.'
                    _visa[i].trangthai = -1
                  } else {
                    _visa[i].ketqua_kh = 'Thành công.'
                    _visa[i].trangthai = 1
                  }
                } else {
                  if (this.$auth.getMaTinh() == 'HTH') {
                    if (dslam_vdc_Id == "42537" || dslam_vdc_Id == "40701") {
                      if (!await this.DoiCongMegavnn9_FE(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04)) {
                        _visa[i].ketqua_kh = 'Thất bại.'
                        _visa[i].trangthai = -1
                      } else {
                        _visa[i].ketqua_kh = 'Thành công.'
                        _visa[i].trangthai = 1
                      }
                    } else {
                      if (!await this.DoiCongMegavnn9_ADSL(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04)) {
                        _visa[i].ketqua_kh = 'Thất bại.'
                        _visa[i].trangthai = -1
                      } else {
                        _visa[i].ketqua_kh = 'Thành công.'
                        _visa[i].trangthai = 1
                      }
                    }
                  } else {
                    if (!await this.DoiCongMegavnn9_ADSL(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04)) {
                      _visa[i].ketqua_kh = 'Thất bại.'
                      _visa[i].trangthai = -1
                    } else {
                      _visa[i].ketqua_kh = 'Thành công.'
                      _visa[i].trangthai = 1
                    }
                  }
                }
              }

            }
          }
          if (_vasc.length > 0) {
            let sPortDSL = "";
            let shelf = "0";
            let vloaitbi_id = await this.$root.context.get("web-quantri/ds-tb-doicong/map-id-loaitbi", {dslam_id: _vasc[0].dslam_id})
            vloaitbi_id = vloaitbi_id.data
            for (let i = 0; i < _vasc.length; i++) {
              if (this.$auth.getMaTinh() == 'BDG') {
                if (vloaitbi_id == "1") {
                  let slot_moi = await this.$root.context.get("web-quantri/ds-tb-doicong/map-id-slot", {
                    loaitbi_id: 1,
                    port_id: _vasc[i].port_id,
                  })
                  slot_moi = slot_moi.data
                  sPortDSL = _vasc[i].ip + '/' + _vasc[i].rack + '/' + slot_moi + '/' + _vasc[i].port + '/' + _vasc[i].vpi
                } else if (vloaitbi_id == "3") {
                  sPortDSL = _vasc[i].ip + '/' + _vasc[i].rack + '/' + _vasc[i].slot + '/' + _vasc[i].port + '/' + _vasc[i].vpi
                } else if (vloaitbi_id == "2") {
                  let slot_moi = await this.$root.context.get("web-quantri/ds-tb-doicong/map-id-slot", {
                    loaitbi_id: 2,
                    port_id: _vasc[i].port_id,
                  })
                  slot_moi = slot_moi.data
                  sPortDSL = _vasc[i].ip + '/' + _vasc[i].rack + '/' + _vasc[i].slot + '/' + shelf + '/' + slot_moi
                } else if (vloaitbi_id == "-1") {
                  _vasc[i].ketqua_kh = "Không xác định được loại thiết bị của dslam tỉnh. Mời bạn chọn lại.";
                  _vasc[i].trangthai = -1;
                }
              } else {
                if (_vasc[i].shelf !== null && _vasc[i].self != '')
                  shelf = _vasc[i].shelf
                if (vloaitbi_id == "3")
                  sPortDSL = _vasc[i].ip + '/' + shelf + '/' + _vasc[i].slot + '/' + _vasc[i].port + '/' + _vasc[i].vpi
                else sPortDSL = _vasc[i].ip + '/' + shelf + '/' + _vasc[i].slot + '/' + _vasc[i].port
              }
              let sDeviceName = _vasc[i].system ? _vasc[i].system : ''
              vascService.setupRoot(this.$root)
              let result = await vascService.UpdateDeviceInfo(_vasc[i].ma_tb, sDeviceName, sPortDSL)
              if (result != '0') {
                _vasc[i].ketqua_kh = 'Thất bại.'
                _vasc[i].trangthai = -1
              } else {
                _vasc[i].ketqua_kh = 'Thành công.'
                _vasc[i].trangthai = 1
              }
            }
          }
        }
        let _tb = this.dt.filter(i => i.dichvuvt_id == DichVuVienThong.METRONET
          || i.dichvuvt_id == DichVuVienThong.MEGAWAN
          || i.dichvuvt_id == DichVuVienThong.IMS
          && i.selected == 1)
        if (_tb.length > 0) {
          for (let item of _tb) {
            item.ketqua_kh = "Cập nhật port thành công.";
            item.trangthai = 1;
          }
        }
        let kq = await this.cn_port_mgwan_mtnet(this.dt)
        if (kq && kq.error_code == "BSS-00000000")
          this.$root.toastSuccess("Kích hoạt thành công")
        else {
          let error = kq.message + '\n' + kq.message_detail
          this.$root.toastError("Có lỗi khi kích hoạt cn_port_mgwan_mtnet:"+ error)
        }
        this.dt = [...this.dt]
        this.$refs['dsDoiCong'].grid.refresh()
      } catch (e) {
        if (e.response && e.response.data.message_detail)
          this.$root.toastError("Có lỗi xảy ra trong quá trình đổi cổng account trên Visa.\n" + e.response.data.message_detail)
      } finally {
        this.loading(false)
      }

    },
    async cn_port_mgwan_mtnet(dt) {
      let dt_copy = []
      dt.forEach(item => {
        let row = {}
        for (let key in item) {
          let k = key.toUpperCase()
          row[k] = item[key]
        }
        dt_copy.push(row)
      })
      try {
        return await this.$root.context.post("web-quantri/ds-tb-doicong/cn-port-mgwan-mtnet", dt_copy)
      } catch (e) {
        console.log(e)
        throw e
      }
    },
    DoiCongMegavnn4: async function (account, PORT, VCI, VPI, SLOT, BrasId, DslamId) {
      try {
        let serviceObj = [
          {
            type: "PORT",
            value: PORT ? PORT : ""
          },
          {
            type: "VCI",
            value: VCI ? VCI : ""
          },
          {
            type: "VPI",
            value: VPI ? VPI : ""
          },
          {
            type: "SLOT",
            value: SLOT ? SLOT : ""
          },
          {
            type: "DSLAM-ID",
            value: DslamId ? DslamId : ""
          },
          {
            type: "BRAS-ID",
            value: BrasId ? BrasId : ""
          },
          {
            type: "PARAMETER",
            value: "VISANEW"
          }
        ]
        //await visa.changePortMegaVNN(account, serviceObj)
        let rs = await this.changePortMegaVNN(account, serviceObj)
        if (rs.errorCode == 0 && rs.data == "ok") return true
        else {
          let error = rs.faultString
          if (error.includes("Không có sự thay đổi") || error.includes("Đối tượng [Dịch vụ khách hàng] không tồn tại")) //Đã tồn tại ngày rồi
            return true;
          else {
            this.$root.$toast.error("Có lỗi xảy ra: " + error)
            return false;
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    DoiCongMegavnn7: async function (account, PORT, VCI, VPI, SLOT, BrasId, DslamId, SYSTEMID, RACKID, SHELFID) {
      try {
        let serviceObj = [
          {
            type: "PORT",
            value: PORT ? PORT : ""
          },
          {
            type: "VCI",
            value: VCI ? VCI : ""
          },
          {
            type: "VPI",
            value: VPI ? VPI : ""
          },
          {
            type: "SLOT",
            value: SLOT ? SLOT : ""
          },
          {
            type: "DSLAM-ID",
            value: DslamId ? DslamId : ""
          },
          {
            type: "BRAS-ID",
            value: BrasId ? BrasId : ""
          },
          {
            type: "RACKID",
            value: RACKID ? RACKID : ""
          },
          {
            type: "SYSTEMID",
            value: SYSTEMID ? SYSTEMID : ""
          },
          {
            type: "SELFID",
            value: SHELFID ? SHELFID : ""
          },
          {
            type: "PARAMETER",
            value: "VISANEW"
          }
        ]

        // await visa.changePortMegaVNN(account, serviceObj)
        let rs = await this.changePortMegaVNN(account, serviceObj)
        if (rs.errorCode == 0 && rs.data == "ok") return true
        else {
          let error = rs.faultString
          if (error.includes("Không có sự thay đổi") || error.includes("Đối tượng [Dịch vụ khách hàng] không tồn tại")) //Đã tồn tại ngày rồi
            return true;
          else {
            this.$root.$toast.error("Có lỗi xảy ra: " + error)
            return false;
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    DoiCongMegavnn9_Mega: async function (account, PORT, VCI, VPI, SLOT, BrasId, DslamId, SYSTEMID, SELFID01, SELFID02, SELFID03, SELFID04) {
      try {
        let serviceObj = [
          {
            type: "PORT",
            value: PORT ? PORT : ""
          },
          {
            type: "VCI",
            value: VCI ? VCI : ""
          },
          {
            type: "VPI",
            value: VPI ? VPI : ""
          },
          {
            type: "SLOT",
            value: SLOT ? SLOT : ""
          },
          {
            type: "DSLAM-ID",
            value: DslamId ? DslamId : ""
          },
          {
            type: "BRAS-ID",
            value: BrasId ? BrasId : ""
          },
          {
            type: "RACKID",
            value: RACKID ? RACKID : ""
          },
          {
            type: "SYSTEMID",
            value: SYSTEMID ? SYSTEMID : ""
          },
          {
            type: "SELFID01",
            value: SELFID01 ? SELFID01 : ""
          },
          {
            type: "SELFID02",
            value: SELFID02 ? SELFID02 : ""
          },
          {
            type: "SELFID03",
            value: SELFID03 ? SELFID03 : ""
          },
          {
            type: "SELFID04",
            value: SELFID04 ? SELFID04 : ""
          },
          {
            type: "PARAMETER",
            value: "VISANEW"
          },
        ]

        let rs = await this.changePortMegaVNN(account, serviceObj)
        if (rs.errorCode == 0 && rs.data == "ok") return true
        else {
          let error = rs.faultString
          if (error.includes("Không có sự thay đổi") || error.includes("Đối tượng [Dịch vụ khách hàng] không tồn tại")) //Đã tồn tại ngày rồi
            return true;
          else {
            this.$root.$toast.error("Có lỗi xảy ra: " + error)
            return false;
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    DoiCongMegavnn9_ADSL: async function (account, PORT, VCI, VPI, SLOT, BrasId, DslamId, SYSTEMID, SELFID01, SELFID02, SELFID03, SELFID04) {
      try {
        let serviceObj = [
          {
            type: "PORT",
            value: PORT ? PORT : ""
          },
          {
            type: "VLAN_ID",
            value: VCI ? VCI : ""
          },
          {
            type: "AXCI",
            value: VPI ? VPI : ""
          },
          {
            type: "SLOT",
            value: SLOT ? SLOT : ""
          },
          {
            type: "DSLAM-ID",
            value: DslamId ? DslamId : ""
          },
          {
            type: "BRAS-ID",
            value: BrasId ? BrasId : ""
          },
          {
            type: "SYSTEMID",
            value: SYSTEMID ? SYSTEMID : ""
          },
          {
            type: "SELFID",
            value: SELFID01 ? SELFID01 : ""
          },
          {
            type: "OLT_SLOT",
            value: SELFID02 ? SELFID02 : ""
          },
          {
            type: "OLT_PORT",
            value: SELFID03 ? SELFID03 : ""
          },
          {
            type: "ONUID",
            value: SELFID04 ? SELFID04 : ""
          },
          {
            type: "PARAMETER",
            value: "VISANEW"
          },
        ]
        let rs = await this.changePortMegaVNN(account, serviceObj)
        if (rs.errorCode == 0 && rs.data == "ok") return true
        else {
          let error = rs.faultString
          if (error.includes("Không có sự thay đổi") || error.includes("Đối tượng [Dịch vụ khách hàng] không tồn tại")) //Đã tồn tại ngày rồi
            return true;
          else {
            this.$root.$toast.error("Có lỗi xảy ra: " + error)
            return false;
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    DoiCongMegavnn9_FE: async function (account, PORT, VLAND_ID, AXCI, SLOT, BrasId, DslamId, SYSTEMID, SELFID01, SELFID02, SELFID03, SELFID04) {
      try {
        let serviceObj = [
          {
            type: "PORT",
            value: PORT ? PORT : ""
          },
          {
            type: "VLAN_ID",
            value: VLAND_ID ? VLAND_ID : ""
          },
          {
            type: "AXCI",
            value: AXCI ? AXCI : ""
          },
          {
            type: "DSLAM-ID",
            value: DslamId ? DslamId : ""
          },
          {
            type: "BRAS-ID",
            value: BrasId ? BrasId : ""
          },
          {
            type: "SYSTEMID",
            value: SYSTEMID ? SYSTEMID : ""
          },
          {
            type: "SELFID01",
            value: SELFID01 ? SELFID01 : ""
          },
          {
            type: "SELFID02",
            value: SELFID02 ? SELFID02 : ""
          },
          {
            type: "SELFID03",
            value: SELFID03 ? SELFID03 : ""
          },
          {
            type: "SELFID04",
            value: SELFID04 ? SELFID04 : ""
          },
          {
            type: "PARAMETER",
            value: "VISANEW"
          },
        ]
        let rs = await this.changePortMegaVNN(account, serviceObj)
        if (rs.errorCode == 0 && rs.data == "ok") return true
        else {
          let error = rs.faultString
          if (error.includes("Không có sự thay đổi") || error.includes("Đối tượng [Dịch vụ khách hàng] không tồn tại")) //Đã tồn tại ngày rồi
            return true;
          else {
            this.$root.$toast.error("Có lỗi xảy ra: " + error)
            return false;
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    changePortMegaVNN: async function (account, attributes) {
      try {
        return await this.$root.context.post(
          "/tichhop/visa-app/changePortMegaVNN",
          {
            accountModel: {accountName: account},
            // login: login,
            attributes: attributes
          }
        )
      } catch (error) {
        throw error
      } finally {
      }
    },
  }
}
</script>

<style scoped>

</style>
