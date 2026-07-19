<template src="./index.template.html">
</template>
<script>
export default {
    data: function () {
        return {
            dsPortChanel: [],
            dtPortChanel: [],
            dsTramTB: [],
            dsTB: [],
            dsPortChuaGan: [],
            dsPortDaGan: [],
            // isNew: false,
            tableDsPortChuaGan: { data: [], selected: 0, checked: [] },
            tableDsPortDaGan: { data: [], selected: 0, checked: [] },
            dataSelected:{
                idNhom: '',
                maNhom: '',
                cboTramTB: -1,
                cboTB: -1,
                tendslam:'',
                validated: 1,
            },
            disable: 0,
            buttonState: {
                btnNhapMoiEnabled: true,
                btnCapNhatEnabled: true,
                btnXoaEnabled: true,
                btnHuyBoEnabled: true
            },
            xacNhanXoa: false,
            isCreated: true
        }
    },
    created: async function () {
        // try {
        //     this.validated = 1
        //     this.loading(true)
        //     await this.dsTramTBAction()
        //     await this.layDsPortChanelAllAction()
        // } catch (err) {
        //     console.log(err)
        //     this.$root.$toast.error('error')
        // } finally {
        //     this.loading(false)
        // }

        this.validated = 1
        await this.dsTramTBAction()
        await this.layDsPortChanelAllAction()
    },
  computed:{
      isNew() {
        return this.dataSelected && this.dataSelected.idNhom ? false : true
      }
  },
    methods: {
        layDsPortChanelAllAction: async function(){
            try {
                this.loading(true)
                let rs = await this.$root.context.get('/web-ecms/danhmuc/PortChannel/DanhSach')
                if (rs.data.length > 0) {
                    this.dsPortChanel = rs.data
                }
                else
                {
                    this.dsPortChanel = []
                    this.clear()
                }
            } catch (error) {
                this.$root.$toast.error(error)
            }
            finally{
                this.loading(false)
            }
        },
        dsTramTBAction: async function(){
            try {
                this.loading(true)
                let rs = await this.$root.context.get('/web-ecms/danhmuc/PortChannel/lay-ds-vetinh',{ttvt:39})
                if (rs.data.length > 0) {
                    this.dsTramTB = rs.data
                    this.dataSelected.cboTramTB = rs.data[0].DONVI_ID

                }
            } catch (error) {
                this.$root.$toast.error(error)
            }
            finally{
                this.loading(false)
            }
        },
        layCongPortChuaGan: async function(kenhId, dslamId){
            try {
                this.loading(true)
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/PortChannel/cong-port-channel?kenhId=${kenhId}&dslamId=${dslamId}&kieu=0`)
                if (rs.data !== null) {
                    this.dsPortChuaGan = rs.data
                    this.$refs.chuagan.setCurrentSelectedRow(-1)
                }
                else{
                    this.dsPortChuaGan = []
                }
            } catch (error) {
                this.$root.$toast.error('error: ' +error)
            }
            finally{
                this.loading(false)
            }
        },
        layCongPortDaGan: async function(kenhId, dslamId){
            try {
                this.loading(true)
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/PortChannel/cong-port-channel?kenhId=${kenhId}&dslamId=${dslamId}&kieu=1`)

                if (rs.data !== null) {
                    this.dsPortDaGan = rs.data
                    this.$refs.dagan.setCurrentSelectedRow(-1)

                }
                else {
                    this.dsPortDaGan = []
                }
            } catch (error) {
                this.$root.$toast.error(error)
            }
            finally{
                this.loading(false)
            }
        },
        onChangeTocboTBi: async function(){
            try {
                let donvi_id = this.dataSelected.cboTramTB
                if (donvi_id > 0){
                    let rs = await this.$root.context.get('/web-ecms/danhmuc/PortChannel/lay-ds-thietbi',{tramtbi_id:donvi_id})
                    if (rs.data.length > 0) {
                        this.dsTB = rs.data
                        this.dataSelected.cboTB = rs.data[0].DSLAM_ID
                        this.dataSelected.tendslam = rs.data[0].TENDSLAM
                        //await this.onChangeDsPortGanOrChua(rs.data[0].DSLAM_ID)
                        if (rs.data[0].DSLAM_ID > 0) {
                            this.layCongPortChuaGan(-1, rs.data[0].DSLAM_ID)
                            this.layCongPortDaGan(-1, rs.data[0].DSLAM_ID)
                        } else {

                        }

                    }
                    else {
                        this.dsTB  = []
                        this.dsPortChuaGan = []
                        this.dsPortDaGan = []
                    }
                }
            } catch (error) {
                this.$root.$toast.error(error)
            }
            finally{
                this.loading(false)
            }
        },
        async onclickPortChanel(dataItem) {
            try {
                if(dataItem != null){
                    this.buttonState.btnXoaEnabled = true
                    this.disable = 1
                    this.dataSelected.idNhom = dataItem.KENH_ID
                    this.dataSelected.maNhom = dataItem.MA_KENH
                    this.dataSelected.cboTramTB = dataItem.DONVI_ID
                    let dv_id = dataItem.DONVI_ID
                    if (dv_id > 0) {
                        let rs1 = await this.$root.context.get('/web-ecms/danhmuc/PortChannel/lay-ds-thietbi',{tramtbi_id:dv_id})
                        if (rs1.data.length > 0) {
                            this.dsTB = rs1.data
                            this.dataSelected.cboTB = dataItem.DSLAM_ID
                            this.dataSelected.tendslam = dataItem.TENDSLAM
                        }
                        else {
                            this.dsTB  = []
                            this.dsPortChuaGan = []
                            this.dsPortDaGan = []
                        }
                    }
                    if (dataItem.DSLAM_ID > 0) {
                        this.layCongPortChuaGan(dataItem.KENH_ID, dataItem.DSLAM_ID)
                        this.layCongPortDaGan(dataItem.KENH_ID, dataItem.DSLAM_ID)
                        this.$refs.dagan.setCurrentSelectedRow(-1)
                        this.$refs.chuagan.setCurrentSelectedRow(-1)
                    }
                    this.$refs.dagan.setCurrentSelectedRow(-1)
                    this.$refs.chuagan.setCurrentSelectedRow(-1)
                }

            } catch (err) {
                this.$toast.error('Err ',err )
            } finally {
                this.loading(false);
            }
        },
        onClickDongDsPortDaGan (portvl_id) {
            this.tableDsPortDaGan.selected = portvl_id
        },
        onClickDongDsPortChuaGan (portvl_id) {
            this.tableDsPortChuaGan.selected = portvl_id
        },
        onClickButtonNhapMoi () {
            // this.isNew = true
            this.disable = 0
            this.clear();
            this.setButton(0);
            this.$refs['ma-nhom'].focus();
        },
        setButton (kieu) {
            if (kieu == -1)//Bat dau
            {
                this.buttonState.btnNhapMoiEnabled = false
                this.buttonState.btnCapNhatEnabled = true
                this.buttonState.btnHuyBoEnabled = true
                this.buttonState.btnXoaEnabled = false
            }
            else if (kieu == 0) { //Bat dau
                this.buttonState.btnNhapMoiEnabled = false
                this.buttonState.btnCapNhatEnabled = true
                this.buttonState.btnXoaEnabled = false
                this.buttonState.btnHuyBoEnabled = false
            } else if (kieu == 1) { //Them moi
                this.buttonState.btnNhapMoiEnabled = false
                this.buttonState.btnCapNhatEnabled = true
                this.buttonState.btnXoaEnabled = false
                this.buttonState.btnHuyBoEnabled = true
                this.clear()
            }
            else if (kieu == 2) { //Huy
                this.buttonState.btnNhapMoiEnabled = true
                this.buttonState.btnCapNhatEnabled = false
                this.buttonState.btnXoaEnabled = true
                this.buttonState.btnHuyBoEnabled = false
            } else if (kieu == 3) { //Edit
                this.buttonState.btnNhapMoiEnabled = true
                this.buttonState.btnCapNhatEnabled = true
                this.buttonState.btnXoaEnabled = true
                this.buttonState.btnHuyBoEnabled = true
            }
        },
        onClickButtonGan () {
            let dsChuagan = [...this.dsPortChuaGan]
            let selectedChuagan = this.tableDsPortChuaGan.checked
            try
            {
                if (dsChuagan.length == 0)
                    return
                selectedChuagan.forEach(item => {
                    let index = this.dsPortChuaGan.findIndex((element) => element.PORTVL_ID === item)
                    let daGan = this.dsPortChuaGan.filter((x) => x.PORTVL_ID == item)
                    let dsTam = JSON.parse(JSON.stringify(this.dsPortChuaGan))
                    let dsPortDaGanTam = JSON.parse(JSON.stringify(this.dsPortDaGan))
                    if (index !== -1) {

                    dsTam.splice(index, 1)
                    dsPortDaGanTam.push(daGan[0])
                    }
                    this.dsPortChuaGan = dsTam
                    this.dsPortDaGan = dsPortDaGanTam
                })
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi: " + ex)
            }
        },
        onClickButtonGoGan () {

            let dsDagan = [...this.dsPortDaGan]
            let selectedDagan = this.tableDsPortDaGan.checked
            try
            {
                if (dsDagan.length == 0){
                    return
                }

                selectedDagan.forEach(item => {
                    let index = this.dsPortDaGan.findIndex((element) => element.PORTVL_ID === item)
                    let daGan = this.dsPortDaGan.filter((x) => x.PORTVL_ID == item)

                    let dsTam = JSON.parse(JSON.stringify(this.dsPortChuaGan))
                    let dsPortDaGanTam = JSON.parse(JSON.stringify(this.dsPortDaGan))
                    if (index !== -1) {
                    dsPortDaGanTam.splice(index, 1)
                    dsTam.push(daGan[0])
                    }
                    this.dsPortChuaGan = dsTam
                    this.dsPortDaGan = dsPortDaGanTam
                })
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi: " + ex)
            }
        },

        onClickButtonHuyBo () {
            this.clear()
            this.setButton(0)
        },
        checkInput: function(){
            if (this.dataSelected.maNhom === ''){
               this.$root.toastError('Bạn chưa điền mã nhóm!')
                return false
            }
            if (this.dataSelected.cboTB === -1 || this.dataSelected.cboTB === 0){
                this.$root.toastError('Bạn chưa chọn thiết bị!')
                return false
            }
            if (this.dataSelected.cboTramTB === -1 || this.dataSelected.cboTramTB === 0){
                this.$root.toastError('Bạn chưa chọn trạm thiết bị!')
                return false
            }
            if (this.dsPortDaGan.length === 0){
               this.$root.toastError('Chưa có thông tin Port đã gán.')
                return false
            }
            return true
        },
        onClickButtonCapNhat: async function(){
            try {
                this.loading(true)
                let check = this.checkInput()
                if (check)
                {
                    let result = null
                    if (this.isNew){
                        let dataInsert = {
                            dslamId: this.dataSelected.cboTB,
                            kenhPorts: this.dsPortDaGan.map( s => ({port:s.PORT, portVlId:s.PORTVL_ID})),
                            ma: this.dataSelected.maNhom,
                            ten: this.dataSelected.tendslam + ' (portchannel '+this.dataSelected.maNhom+')',
                            tramTbId: this.dataSelected.cboTramTB
                        }
                        result = await this.themMoi(dataInsert)
                        // this.isNew = false
                    }
                    else {
                        let dataupdate = {
                            kenhId:this.dataSelected.idNhom,
                            dslamId: this.dataSelected.cboTB,
                            kenhPorts: this.dsPortDaGan.map( s => ({port:s.PORT, portVlId:s.PORTVL_ID})),
                            ma: this.dataSelected.maNhom,
                            ten: this.dataSelected.tendslam + ' (portchannel '+this.dataSelected.maNhom+')'
                            //tramTbId: this.dataSelected.cboTramTB
                        }
                        result = await this.capNhat(dataupdate)
                    }
                    await this.layDsPortChanelAllAction()
                    this.$toast.success(result)
                }
            } catch (err) {
                this.$toast.error(err)
            } finally {
                this.loading(false)
            }
        },
        async themMoi (data) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/PortChannel/Them', data)
                if (response.error_code === 'BSS-00000000') {
                return 'Thêm mới thành công.'
                }

                let message = response.message

                if (response.message_detail) {
                message += '\n' + response.message_detail
                }

                if (response.data) {
                let detailMessage = Object.getOwnPropertyNames(response.data).map(item => response.data[item])
                message = message + '\n' +  [...new Set(detailMessage)].join('\n')
                }

                throw message
            } catch (err) {
                let responseData = err.response.data
                let message = responseData.message

                if (responseData.message_detail) {
                message += '\n' + responseData.message_detail
                }

                if (responseData.data) {
                let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                message = message + '\n' +  [...new Set(detailMessage)].join('\n')
                }

                throw message
            }
        },
        async capNhat (data) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/PortChannel/CapNhat', data)
                console.log('response update ',response)
                if (response.error_code === 'BSS-00000000') {
                return 'Cập nhật thành công.'
                }

                let message = response.message

                if (response.message_detail) {
                message += '\n' + response.message_detail
                }

                if (response.data) {
                let detailMessage = Object.getOwnPropertyNames(response.data).map(item => response.data[item])
                message = message + '\n' +  [...new Set(detailMessage)].join('\n')
                }

                throw message
            } catch (err) {
                let responseData = err.response.data
                let message = responseData.message

                if (responseData.message_detail) {
                message += '\n' + responseData.message_detail
                }

                if (responseData.data) {
                let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                message = message + '\n' +  [...new Set(detailMessage)].join('\n')
                }

                throw message
            }
        },
        onChangeDsPortGanOrChua: async function(dslam_id){
            try {
                this.loading(true)
                let kenhId = this.dataSelected.idNhom != null ? this.dataSelected.idNhom : -1
                let dslamTinh = this.dsTB
                let selectedDslam = dslam_id
                if (selectedDslam === -1){
                    return
                }
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/PortChannel/cong-port-channel?kenhId=${kenhId}&dslamId=${selectedDslam}&kieu=0`)
                if (rs.data !== null) {
                    this.dsPortChuaGan = rs.data
                } else {
                    this.dsPortChuaGan = []
                }
                let rs1 = await this.$root.context.get(`/web-ecms/danhmuc/PortChannel/cong-port-channel?kenhId=${kenhId}&dslamId=${selectedDslam}&kieu=1`)
                if (rs1.data !== null) {
                    this.dsPortDaGan = rs1.data
                } else {
                    this.dsPortDaGan = []
                }
            } catch (error) {
                this.$toast.error('error' + error)
            }
            finally{
                this.loading(false)
            }
        },
        async onClickButtonXoa () {
            if (!this.buttonState.btnXoaEnabled) {
                return
            }
            this.xacNhanXoa = true
        },
        async okXoaPortChanel () {
            try {
                this.loading(true)
                let kenh_id = this.dataSelected.idNhom
                let rs = await this.$root.context.post(`/web-ecms/danhmuc/PortChannel/Xoa?kenhId=${kenh_id}`)
                if (rs.data.result) {
                    this.$toast.success('Xóa Port Chanel thành công')
                    await this.layDsPortChanelAllAction()
                    this.xacNhanXoa = false
                }
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }
        },
        async clear () {
            this.dataSelected.idNhom = ''
            this.dataSelected.maNhom = ''
            this.dataSelected.cboTramTB = 0
            this.dataSelected.cboTB = 0
            this.dataSelected.tendslam = ''
            this.tableDsPortChuaGan.data = []
            this.tableDsPortDaGan.data = []
            this.dsPortChuaGan = []
            this.dsPortDaGan = []
            this.dsTB = []
        },
        select_selectedItemsChangedDagan: function(dataKeys){
            this.tableDsPortDaGan.checked = dataKeys
        },
        select_selectedItemsChangedChuagan: function(dataKeys){
            this.tableDsPortChuaGan.checked = dataKeys
        }

    },
    watch: {
        dsPortChuaGan () {
            this.tableDsPortChuaGan.checked = []
        },
        dsPortDaGan () {
            this.tableDsPortDaGan.checked = []
        }
    },
}
</script>
<style>
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
</style>
