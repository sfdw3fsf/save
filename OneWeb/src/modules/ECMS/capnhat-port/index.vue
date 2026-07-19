<template src="./index.template.html">
</template>
<script>
import visa from "./service/visaService";
export default {
    data: function () {
        return {
           indexGrid: -1,
           arrayDataToCbo: {
            dataDVQL: [],
            dataDslamTinh: [],
            dataTramTC: [],
            dataCard: [],
            dataTramTB: [],
            dataBras: [],
            dataDslam: [],
           },
           arrayCBO: {
            cboDVQL: 0,
            cboTramTC: -1,
            chkCard: false,
            cboDslamTinh: 0,
            cboCard: -1,
            cboTramTB: -1,
            cboBras: -1,
            cboDslam: -1
           },
           arrayDataGrids: {
            dsPort: [],
            dsThueBao: []
           },
           kt_load: true,
           thamso: "0",
           lblTongSo: '',
           ds_id_port: []
        }
    },
    created: async function () {
        try
        {
            this.loading(true)
            await this.layDsDVQL()
            this.kt_load = false
            await this.layDsBras()
            await this.layDsDslamVDC()
            visa.setupRoot(this.$root)
            let ttnd= await this.lay_thongtin_nguoidung()
            let login = {
                 userId: ttnd.user_visa,
                 password:ttnd.pass_visa
            }
            visa.setLogin(login)
        }
        catch (ex)
        {
            this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu: " +ex)
        }
        finally{
            this.loading(false)
        }
    },
    methods: {
        layDsDVQL: async function(){
            let donvi_id = 0
            let loaidv_id = 3
            let rs = await this.$root.context.get(`/web-ecms/danhmuc/cap-nhat-port/lietke-donvi-qli?p_donvi_id=${donvi_id}&p_loaidv_id=${loaidv_id}`)
            if  (rs.error_code === 'BSS-00000000'){
                this.arrayDataToCbo.dataDVQL = rs.data
                if (rs.data.length > 0) {
                    this.arrayCBO.cboDVQL = rs.data[0].DONVI_ID
                }
                //if (this.arrayCBO.cboDVQL > 0)
                {
                    await this.layDsTramTC()
                }
            }
            else{
                this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu.")
            }
        },
        layDsTramTC: async function(){
          try {
            this.loading(true)
            let donvi_id = this.arrayCBO.cboDVQL
            let loaidv_id = 5
          //  this.arrayCBO.cboTramTC = 
            let rs = await this.$root.context.get(`/web-ecms/danhmuc/cap-nhat-port/lietke-donvi-qli?p_donvi_id=${donvi_id}&p_loaidv_id=${loaidv_id}`)
            if  (rs.error_code === 'BSS-00000000'){
              this.arrayDataToCbo.dataTramTC = rs.data
              if (rs.data.length > 0) {
                this.arrayCBO.cboTramTC = rs.data[0].DONVI_ID
              }
              //if (this.arrayCBO.cboTramTC > 0)
              {
                await this.layDsTramTB()
              }
            }
            else{
              this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu.")
            }
          }catch (e) {

          }finally {
            this.loading(false)
          }

        },
        layDsTramTB: async function(){
          try {
            this.loading(true)
            let donvi_id = this.arrayCBO.cboTramTC
            let loaidv_id = 39
            this.arrayCBO.cboTramTB = []
            let rs = await this.$root.context.get(`/web-ecms/danhmuc/cap-nhat-port/lietke-tramtbi?p_donvi_id=${donvi_id}&p_loaidv_id=${loaidv_id}`)
            if  (rs.error_code === 'BSS-00000000'){
              this.arrayDataToCbo.dataTramTB = rs.data
              if (rs.data.length > 0) {
                this.arrayCBO.cboTramTB = rs.data[0].DONVI_ID
              }
              //if (this.arrayCBO.cboTramTB > 0)
              {
                await this.layDsDslamTinh()
                let ds_dslam = this.arrayDataToCbo.dataDslamTinh
                if (ds_dslam.length <= 0) {
                  this.arrayDataGrids.dsPort = []
                  this.arrayDataGrids.dsThueBao = []
                  this.arrayCBO.chkCard = false
                  this.arrayDataToCbo.dataCard = []
                }
              }
            }
            else{
              this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu.")
            }
          }catch (e) {

          }finally {
            this.loading(false)
          }
        },
        cboDslamTinh_Changed: async function(){
            try {
              this.loading(true)
              let dslamTinh = this.arrayDataToCbo.dataDslamTinh
                let selectedDslamTemp = dslamTinh.filter(obj=>obj.DSLAM_ID == this.arrayCBO.cboDslamTinh);

                console.log('dslamTinh',dslamTinh)
                console.log('cboDslamTinh',this.arrayCBO.cboDslamTinh)

                let selectedDslam = [...selectedDslamTemp][0]
                console.log('selectedDslam',selectedDslam)
                if (dslamTinh.length > 0) {
                    if (selectedDslam != []) {
                        // if (selectedDslam.id == -1 || dslamCbo.id == null) {
                        //     return
                        // }
                        this.thamso = selectedDslam.THAMSO
                        await this.layDsDslamVDC()
                        let loaitbi_id = 0
                        loaitbi_id = selectedDslam.LOAITBI_ID

                        if (loaitbi_id == 3 || loaitbi_id == 1) {
                            let gpon_id = selectedDslam.DSLAM_ID //selectedDslam.DSLAM_ID
                            let rs = await this.$root.context.get(`/web-ecms/danhmuc/cap-nhat-port/lietke-dscard-theo-dslam?p_dslam_id=${gpon_id}&p_loaitbi_id=${loaitbi_id}`)
                            if  (rs.error_code === 'BSS-00000000'){
                                console.log(rs)
                                this.arrayDataToCbo.dataCard = rs.data
                                if (rs.data.length > 0) {
                                    this.arrayCBO.cboCard = rs.data[0].CARD_ID
                                }
                            }
                            else{
                                this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu.")
                            }
                        } else {
                            this.arrayDataToCbo.dataCard = []
                        }
                        await this.hienthi_ds_port()
                    }

                }

            } catch (error) {
                this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu." +error)
            } finally {
              this.loading(false)
            }
        },
        cboCard_Changed: async function(){
            try
            {
                this.loading(true)
                if (this.arrayCBO.cboCard > 0)
                {
                    this.arrayDataGrids.dsThueBao = []
                    await this.hienthi_ds_port()
                }
            }
            catch (ex)
            {
                this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu: "+ex)
            }
            finally{
                this.loading(false)
            }
        },
        chkCard_Changed: async function(){
            try
            {
                this.loading(true)
                 this.arrayCBO.chkCard = !this.arrayCBO.chkCard
                this.arrayDataGrids.dsThueBao = []
                await this.hienthi_ds_port()
            }
            catch (ex)
            {
                this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu: " +ex)
            }
            finally{
                this.loading(false)
            }
        },
        layDsDslamTinh: async function(){
          try {
            this.loading(true)
            let donvi_id = this.arrayCBO.cboTramTB//3
            let rs = await this.$root.context.get(`/web-ecms/danhmuc/cap-nhat-port/lietke-dslam-tinh?p_donvi_id=${donvi_id}`)
            if  (rs.error_code === 'BSS-00000000'){
              this.arrayDataToCbo.dataDslamTinh = rs.data
              if (rs.data.length > 0) {
                this.arrayCBO.cboDslamTinh = rs.data[0].DSLAM_ID
                await this.hienthi_ds_port()
              }
            }
            else{
              this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu.")
            }
          }catch (e) {

          }finally {
            this.loading(false)
          }
        },
        layDsBras: async function(){
            let rs = await this.$root.context.get("/web-ecms/danhmuc/cap-nhat-port/lietke-bras")
            if  (rs.error_code === 'BSS-00000000'){
                this.arrayDataToCbo.dataBras = rs.data
                if(rs.data!=null && rs.data.length > 0)
                this.arrayCBO.cboBras = rs.data[0].BRAS_ID
            }
            else{
                this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu.")
            }
        },
        layDsDslamVDC: async function(){
            if (!this.kt_load) {
              try {
                this.loading(true)
                await this.HT_DSLAM()
              }catch (e) {

              }finally {
                this.loading(false)
              }
            }
        },
        HT_DSLAM: async function(){
            try {
                let bras_id = this.arrayCBO.cboBras
                let kieu = 2
               // let rs = await this.$root.context.get(`/web-ecms/danhmuc/cap-nhat-port/lietke-dslam?p_bras_id=${bras_id}&p_kieu=${kieu}&p_thamso=${this.thamso}`)
               let rs = await this.$root.context.get('/web-ecms/danhmuc/cap-nhat-port/lietke-dslam',{
                p_bras_id:bras_id,
                p_kieu:kieu,
                p_thamso:this.thamso
               });           
                if  (rs.error_code === 'BSS-00000000'){
                    this.arrayDataToCbo.dataDslam = rs.data
                    if (rs.data.length > 0) {
                        this.arrayCBO.cboDslam = rs.data[0].DSLAM_ID
                    }
                }
                else{
                    this.$root.$toast.error("Lỗi: "+rs.message_detail)
                }
            } catch (error) {
                this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu: "+error)
            }
        },
        HT_DS_THUEBAO: async function(){
            try {

                if (this.arrayDataGrids.dsPort.length == 0){
                    return
                }
                let dslam_id = this.arrayCBO.cboDslamTinh//40326
                let ds_id = this.ds_id_port//'667134,667147'

                if (ds_id.length > 0) {
                    let rs = await this.$root.context.post(`/web-ecms/danhmuc/cap-nhat-port/lay-ds-thuebao-theo-port?p_dslam_id=${dslam_id}&p_ds_port_id=${ds_id}`)

                    if  (rs.error_code === 'BSS-00000000'){
                        this.arrayDataGrids.dsThueBao = rs.data
                    }
                }
                else {
                    this.arrayDataGrids.dsThueBao = []
                }
            }
            catch (error) {
                this.$root.$toast.error("Có lỗi: "+error)
            }

        },
        hienthi_ds_port: async function(){
            try
            {
                this.arrayDataGrids.dsPort = []
                let card_id = -1;
                if (this.arrayCBO.chkCard && this.arrayCBO.cboCard > 0)
                    card_id = this.arrayCBO.cboCard
                let dslam_id = this.arrayCBO.cboDslamTinh//this.arrayCBO.cboDslamTinh//39325
                let tt_port_id = ''
                let donvi_id = this.arrayCBO.cboTramTB//this.arrayCBO.cboTramTB//482
                let dt_list = await this.$root.context.post(`/web-ecms/danhmuc/cap-nhat-port/lay-ds-port-logic-v3?p_donvi_id=${donvi_id}&p_tt_port_id=${tt_port_id}&p_dslam_id=${dslam_id}&p_card_id=${card_id}`)
                if (dt_list.data.length > 0)
                {
                    let listfilter = dt_list.data
                    this.arrayDataGrids.dsPort = dt_list.data
                    let chuasd = listfilter.filter(function(item) {
                        return item.TT_PORT_ID == 1}).length
                    let dasd = listfilter.filter(function(item) {
                        return item.TT_PORT_ID == 3}).length
                    let duphong = listfilter.filter(function(item) {
                        return item.TT_PORT_ID == 5}).length
                    let hong = listfilter.filter(function(item) {
                        return item.TT_PORT_ID === 4}).length
                    let tamcap = listfilter.filter(function(item) {
                        return item.TT_PORT_ID === 2}).length
                    this.lblTongSo = "Tổng số : " + dt_list.data.length + " - Chưa SD : " + chuasd + " - Đã SD : " + dasd + " - Dự phòng : " + duphong + " - Hỏng : " + hong + " - Tạm cấp : " + tamcap;
                }
                else
                {
                    this.arrayDataGrids.dsPort = []
                }
            }
            catch (ex)
            {
                this.$root.$toast.error("Có lỗi xẩy ra trong quá trình lấy dữ liệu. " + ex)
            }
        },
      selectedItemsDsHoSo: async function (data) {
        this.ds_id_port = data
        // this.indexGrid = this.$refs['grid'].getCurrentSelectedRow()
        // console.log('index ', this.$refs['grid'].getCurrentSelectedRow())
        try {
          this.loading(true)
          await this.HT_DS_THUEBAO()
        } catch (e) {

        } finally {
          this.loading(false)
        }
      },
        onClickdsThueBao: async function(data){

        },
        capNhat: async function(){
            let gridviewDanhSach = this.arrayDataGrids.dsPort
            let selected_item = this.ds_id_port
            if (gridviewDanhSach.length == 0) return
            if (selected_item <= 0)
            {
                this.$root.$toast.error("Bạn chưa chọn port để cập nhật !")
                return
            }
            if (!this.arrayCBO.cboBras || this.arrayCBO.cboBras == -1 || !this.arrayCBO.cboDslam || this.arrayCBO.cboDslam == -1) {
              this.$root.$toast.error("Bạn chưa chọn Bras VDC hoặc Dslam VDC để cập nhật !")
              return
            }

            this.$bvModal.msgBoxConfirm("Bạn có chắc chắn thực hiện thay đổi Dslam VDC cho port không ?",
            {
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
            title: "Xác nhận hành động",
            centered: true,
            size: "sm"
            }).then(async (value) => {
                if (value){
                   await this.confirmUpdate(gridviewDanhSach)
                }
                else{
                    return
                }
            })
        },
      confirmUpdate: async function (gridviewDanhSach) {
        try {
          this.loading(true)
          let vport_id = 0
          let selected_Item = this.ds_id_port
          let gridviewThueBao = this.arrayDataGrids.dsThueBao

          let dslam_id = this.arrayCBO.cboDslamTinh
          let dslam_vdc_id_moi = this.arrayCBO.cboDslam
          let logObj = {}
          let dsSelectedTB = gridviewDanhSach.filter(i => selected_Item.includes(i.PORT_ID))
          let success_count = 0, failed_count = 0;
          for (let i = 0; i < dsSelectedTB.length; i++) {
            if (dsSelectedTB.length > 0) {
              vport_id = dsSelectedTB[i].PORT_ID
              for (let j = 0; j < gridviewThueBao.length; j++) {

                if (gridviewThueBao[j].PORT_ID == vport_id) {
                  if (gridviewThueBao[j].KIEU_MEGA != 0) {

                    let vaccount = gridviewThueBao[j].MA_TB
                    let vport = dsSelectedTB[i].PORT
                    let vvci = gridviewThueBao[j].VCI
                    let vvpi = gridviewThueBao[j].VPI
                    let vslot = dsSelectedTB[i].SLOT
                    let vsystemId = dsSelectedTB[i].SYSTEM
                    let vrackid = dsSelectedTB[i].RACK
                    let vshelfId = dsSelectedTB[i].SHELF
                    let vself01 = dsSelectedTB[i].SELFID01
                    let vself02 = dsSelectedTB[i].SELFID02
                    let vself03 = dsSelectedTB[i].SELFID03
                    let vself04 = dsSelectedTB[i].SELFID04
                    let vloaitb_id = gridviewThueBao[j].LOAITB_ID
                    let check = await this.DoiCongAcccountVisa(vaccount, vport, vvci, vvpi, vslot, vsystemId, vrackid, vshelfId, vself01, vself02, vself03, vself04, vloaitb_id)
                    if (!check) {
                      this.$root.$toast.error("Có lỗi trong quá trình đồng bộ port cho Account " + vaccount + " trên Visa !")
                      return
                    }

                    logObj = {
                      "port_id": vport_id,
                      "dslam_id": dslam_id,
                      "dslam_vdc_id_moi": dslam_vdc_id_moi,
                      "dslam_vdc_id_cu": gridviewThueBao[j].DSLAM_VDC_ID,
                      "thuebao_id": gridviewThueBao[j].THUEBAO_ID,
                      "hdtb_id": gridviewThueBao[j].HDTB_ID,
                      "ma_tb": gridviewThueBao[j].MA_TB
                    }
                    try {
                      let rs = await this.$root.context.post('/web-ecms/danhmuc/cap-nhat-port/log-ins', logObj)
                      if (rs.data[0].KETQUA != 1) this.$root.toastError("Lỗi xảy ra khi ghi log")
                    } catch (error) {
                      this.$root.$toast.error("Có lỗi: " + error)
                    }
                  }
                }
              }
              logObj = {
                "port_id": vport_id,
                "dslam_id": dslam_id,
                "dslam_vdc_id_moi": dslam_vdc_id_moi,
                "dslam_vdc_id_cu": gridviewDanhSach[i].DSLAM_VDC_ID,
                "thuebao_id": 0,
                "hdtb_id": 0,
                "ma_tb": ""
              }
              try {
                let rs = await this.$root.context.post('/web-ecms/danhmuc/cap-nhat-port/log-ins', logObj)
                if (rs.data[0].KETQUA != 1) this.$root.toastError("Lỗi xảy ra khi ghi log")
              } catch (error) {
                this.$root.$toast.error("Có lỗi: " + error)
              }
              try {
                let rs = await this.$root.context.post(`/web-ecms/danhmuc/cap-nhat-port/cap-nhat?p_dslam_vdc_id=${dslam_vdc_id_moi}&p_port_id=${vport_id}`)
                // theo source cũ. ko catch thông báo thành công
                // if (rs.data && rs.data.length > 0)
                //   if (rs.data[0].KETQUA != 0) {
                //     success_count +=1
                //     // this.$toast.success('Cập nhật thành công.')
                //   } else {
                //     failed_count +=1
                //     // this.$root.toastError("Cập nhật thất bại.")
                //   }

              } catch (error) {
                this.$root.$toast.error("Có lỗi: " + error)

              }
            }
          }
          // if (success_count == dsSelectedTB.length)
            this.$toast.success('Cập nhật Dslam VDC cho port thành công !')
          // else this.$root.toastError("Cập nhật Dslam VDC cho port thất bại.")
          await this.hienthi_ds_port()
          // this.arrayDataGrids.dsPort = []
        } catch (e) {

        } finally {
          this.loading(false)
        }
      },
      dsPort_queryCellInfo: function (args) {
        switch (args.column.field) {
          case 'PORT':
            args.cell.style.color = 'blue'
            args.cell.style.fontWeight = 'bold'
            break
          case 'TRANGTHAI_PORT':
            args.cell.style.color = 'red'
            args.cell.style.fontWeight = 'bold'
            break
          case 'SLOT':
            args.cell.style.fontWeight = 'bold'
        }
      },
        DoiCongAcccountVisa: async function(account, port, vci, vpi, slot, systemId, rackid, shelfId, self01, self02, self03, self04, loaitb_id){
            try {
                let brasId = this.arrayCBO.cboBras
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/cap-nhat-port/map-bras-vdc?bras_id=${brasId}`)
                if (rs.error_code == "BSS-00000000" && rs.data != null) {
                    brasId = rs.data.BRAS_VDC_ID
                }
                let dslamId = this.arrayCBO.cboDslam
                let dslam = await this.$root.context.get(`/web-ecms/danhmuc/cap-nhat-port/map-dslam-vdc?dslam_id=${dslamId}`)
                let dslam_vdc_Id
                let thamso
                if (dslam.error_code == "BSS-00000000" && dslam.data != null) {
                    dslam_vdc_Id = dslam.data.DSLAM_VDC_ID
                    thamso = dslam.data.THAMSO
                }
                //Kiem tra xem dang ky 4 tham so hay 7 tham so
                if (thamso == 4)
                {
                    if (!await this.DoiCongMegavnn4(account, port, vci, vpi, slot, brasId, dslam_vdc_Id))
                        return false;
                    else
                    {
                        this.$root.$toast.success("Đổi cổng Account " + account + " trên Visa thành công !")
                        return true;
                    }
                }
                else if (thamso == 7)
                {
                    if (!await this.DoiCongMegavnn7(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, rackid, shelfId))
                        return false;
                    else
                    {
                        this.$root.$toast.success("Đổi cổng Account " + account + " trên Visa thành công !")
                        return true;
                    }
                }
                else if (thamso == 9)
                {
                    if (loaitb_id == 11)
                    {
                        if (!await this.DoiCongMegavnn9_Mega(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04))
                            return false;
                        else
                        {
                            this.$root.$toast.success("Đổi cổng Account " + account + " trên Visa thành công !")
                            return true;
                        }
                    }
                    else
                    {
                        if (!await this.DoiCongMegavnn9_ADSL(account, port, vci, vpi, slot, brasId, dslam_vdc_Id, systemId, self01, self02, self03, self04))
                            return false;
                        else
                        {
                            this.$root.$toast.success("Đổi cổng Account " + account + " trên Visa thành công !")
                            return true;
                        }
                    }

                }
                return true;
            } catch (error) {
                this.$root.$toast.error("Có lỗi xảy ra trong quá trình đổi cổng account trên Visa.\n" + error)
                return false;
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
          let rs = await visa.changePortMegaVNN(account, serviceObj)
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
          let rs = await visa.changePortMegaVNN(account, serviceObj)
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

          let rs = await visa.changePortMegaVNN(account, serviceObj)
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
          let rs = await visa.changePortMegaVNN(account, serviceObj)
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
      lay_thongtin_nguoidung: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung"
        );     
        return rs.data[0]
      } catch (error) {
        return null;
      } 
    },
    }
}
</script>
