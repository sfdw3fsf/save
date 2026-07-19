<template src="./index.template.html">
</template>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required} from 'vuelidate/lib/validators'
import XLSX from "xlsx";
export default {
    components: { BssErrorMarker, BssRequiredMarker},
    validations: {
        cboBras: {
            required
        },
        cboDslam: {
            required
        },
        cboBrasNew: {
            required
        },
        cboDslamVDC: {
            required
        },
        cboPortBras: {
        },
        cboSlotBras: {
        },
        cboDslamNew: {
            required
        }
        
    },
    data: function () {
        return {
            ok:true,
            message:'',
            isError:false,
            dsCatChuyen: [],
            dsThueBao: [],
            dsBras: [],
            dsDlslamWBras: [],
            dsDslamVDC: [],
            dsDslamNew: [],
            dsSlot: [],
            dsPort:[],
            currentRow: {},
            isDisableBtnNhapMoi: true,
            isDisableBtnCapNhat: false,
            isDisableBtnHoanThien: false,
            isDisableBtnXoa: false,
            isCreateNew: true,
            enableCBO: false,
            isSelected: -1,
            isDisableBtn: true,
            dataExcel: null,
            cboBras: null,
            cboDslam: null,
            cboBrasNew: null,
            cboDslamVDC: null,
            chkDSHoanThien: false,
            cboKieuCC: 0,
            cboDslamNew: null,
            cboPortBras: null,
            cboSlotBras: null,
            ts_bras: 0,
            txtTimKiem: '',
            modalXoa: false,
            catchuyen: {
              dslam_id: null,
              bras_id:   null,
              dslam_cha_id: null,
              bras_moi_id:   null,
              dslam_moi_id:  null,
              portbras_id:  null,
              slotbras_id:  null
            },
            hoanthienD : {
              dslam_id: null,
              dslam_moi_id: null,
              bras_moi_id: null,
              portbras_id:  null,
              slotbras_id:  null,
              nguoidung_id: null,
              may_cn: null,
              ip_cn: null,
              bras_ts: null
            },
            xoa:{
              dslam_id: -1,
              trangthai: -1
            },
            tendslam: ''
        }
    },
    computed: {
        
    },
    created: async function () {
        try
        {
            this.loading(true)
            await this.layDsBras()
            await this.layDsCatChuyen()
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
        layDsBras: async function(){
            try {
                this.loading(true)
                let rs = await this.$root.context.get('/web-ecms/catchuyendslam/lietkeDsBras')
                if (rs.data.length > 0) {
                    this.dsBras = rs.data
                }
            } catch (error) {
                this.$root.$toast.error(error)
            }
            finally{
                this.loading(false)
            }
            
        },
        layDsDslamWBras: async function(){
            try {
                this.loading(true)
                let bras_id = this.cboBras
                if (bras_id > 0) {
                    let rs = await this.$root.context.get('/web-ecms/catchuyendslam/lietkeDsDslamTheoBras',{ bras_id: bras_id })
                    if (rs.data.length > 0) {
                        this.dsDlslamWBras = rs.data
                        this.cboDslam = rs.data[0].DSLAM_ID
                        if (rs.data[0].DSLAM_ID > 0) {
                            let rs1 = await this.$root.context.get('/web-ecms/catchuyendslam/lietkeDsDslamVDCCuTheoDslam',{ dslam_id: rs.data[0].DSLAM_ID })
                            if (rs1.data.length > 0) {
                                this.dsDslamVDC = rs1.data
                                this.cboDslamVDC = rs1.data[0].DSLAM_ID
                            }
                            else {
                                this.dsDslamVDC = []
                            }
                        }
                    }
                    else{
                        this.dsDlslamWBras = []
                        this.dsDslamVDC = []
                    }
                }
                else{
                    this.dsDlslamWBras = []
                    this.dsDslamVDC = []
                }
            } catch (error) {
                this.$root.$toast.error(error)
            }
            finally{
                this.loading(false)
            }

        },
        layDsDslamVDC : async function() {
            try {
                this.loading(true)
                let dslam_id = this.cboDslam
                if (dslam_id > 0) {
                    let rs = await this.$root.context.get('/web-ecms/catchuyendslam/lietkeDsDslamVDCCuTheoDslam',{ dslam_id: dslam_id })
                    if (rs.data.length > 0) {
                        this.dsDslamVDC = rs.data
                        this.cboDslamVDC = rs.data[0].DSLAM_ID
                        let brasTS = await this.$root.context.get('/web-ecms/catchuyendslam/laythongtinbras', { dslam_id: rs.data[0].LOAI_DSLAM_ID })
                        if (brasTS.data.length === 0){
                            this.$toast.error('Kiểm tra lại dữ liệu dslam hiện tại !')
                        }
                        else{
                            this.ts_bras = brasTS.data[0].BRAS_TS
                        }
                    }
                }
            } catch (error) {
                this.$root.$toast.error(error)
            }
            finally{
                this.loading(false)
            }  
        },
        onChangBrasNew: async function(){
            try {
                this.loading(true)
                let bras_id = this.cboBrasNew
                if (bras_id > 0) {
                    let rs = await this.$root.context.get('/web-ecms/catchuyendslam/lietkeDsDslamVDCMoiTheoBrasMoi',{ bras_id: bras_id })
                    if (rs.data.length > 0) {
                        this.dsDslamNew = rs.data
                        this.cboDslamNew = rs.data[0].DSLAM_ID
                        let rs1 = await this.$root.context.get('/web-ecms/catchuyendslam/lietkeDsSlotBrasTheoBrasMoi',{ bras_id: bras_id })
                        if (rs1.data.length > 0) {
                            this.dsSlot = rs1.data
                            this.cboSlotBras = rs1.data[0].CARDBRAS_ID
                            if (rs1.data[0].CARDBRAS_ID > 0) {
                                let rs = await this.$root.context.get('/web-ecms/catchuyendslam/lietkeDsPortBrasTheoSlotBras',{ slot_id: rs1.data[0].CARDBRAS_ID })
                                if (rs.data.length > 0) {
                                    this.dsPort = rs.data
                                    this.cboPortBras = rs.data[0].PORTBRAS_ID
                                }
                            }
                            else
                            {
                                this.dsPort = []
                                this.cboPortBras = null
                            }
                        }
                        else 
                        {
                            this.dsPort = []
                            this.dsSlot = []
                            this.cboSlotBras = null
                            this.cboPortBras = null
                        }
                    }
                    else
                    {
                        this.dsDslamNew = []
                        this.dsSlot = []
                        this.dsPort = []
                        this.cboDslamNew = null
                        this.cboSlotBras = null
                        this.cboPortBras = null
                    }
                }
                else
                {
                    this.dsDslamNew = []
                    this.dsSlot = []
                    this.dsPort = []
                }
            } catch (error) {
                this.$root.$toast.error(error)
            }
            finally{
                this.loading(false)
            }
        },
        onChangeToPortBras: async function(){
            try {
                this.loading(true)
                let slot_id = this.cboSlotBras
                if (slot_id > 0) {
                    let rs = await this.$root.context.get('/web-ecms/catchuyendslam/lietkeDsPortBrasTheoSlotBras',{ slot_id: slot_id })
                    if (rs.data.length > 0) {
                        this.dsPort = rs.data
                        this.cboPortBras = rs.data[0].PORTBRAS_ID
                    }
                }
            } catch (error) {
                this.$root.$toast.error(error)
            }
            finally{
                this.loading(false)
            }
        },
        capNhatAction: async function(){
            try {
                this.loading(true)
                let dataIns = {
                        dslam_id: this.cboDslam,
                        bras_id:  this.cboBras,
                        dslam_cha_id: this.cboDslamVDC,
                        bras_moi_id:  this.cboBrasNew,
                        dslam_moi_id: this.cboDslamNew,
                        portbras_id:  this.cboPortBras,
                        slotbras_id:  this.cboSlotBras
                    }
                    console.log('dataIns',dataIns)
                if (this.kiemtraDulieu())
                {
                    let result = null
                    if (dataIns != null) {
                        if (this.isCreateNew){
                            let rs = await this.KIEMTRA_CHUYEN_DSLAM_CHUAHT(dataIns.dslam_id);
                            if(rs){
                                this.$toast.error("DSLAM " + this.tendslam + " có bản ghi cắt chuyển chưa hoàn thành, do user " + this.message +" thực hiện !");
                                return;
                            }
                            else{
                                result = await this.themMoi(dataIns)
                            }
                        }
                        else {
                            this.catchuyen = {
                                    dslam_id: this.cboDslam,
                                    bras_id:  this.cboBras,
                                    dslam_cha_id: this.cboDslamVDC,
                                    bras_moi_id:  this.cboBrasNew,
                                    dslam_moi_id: this.cboDslamNew,
                                    portbras_id:  this.cboPortBras,
                                    slotbras_id:  this.cboSlotBras
                                }
                               console.log('this.catchuyen',this.catchuyen) 
                            result = await this.capNhat(this.catchuyen)                           
                        }
                        await this.layDsCatChuyen()
                        this.$toast.success(result)
                    } else {
                        this.$toast.error('Chưa có dữ liệu đầu vào')
                        return
                    }
                }
            } catch (err) {
                this.$toast.error(err.message)
                return
            } finally {
                this.loading(false)
            }
        },
        async themMoi (data) {
            try {
                let response = await this.$root.context.post('/web-ecms/catchuyendslam/them', data)
                if (response.error_code === 'BSS-00000000') {
                return 'Thêm mới thành công'
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
                let response = await this.$root.context.post('/web-ecms/catchuyendslam/capnhat', data)
                if (response.error_code === 'BSS-00000000') {
                return 'Cập nhật thành công'
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
        grid1_selectedRowChanged: async function(dataItem){
            try {
                this.loading(true)
                if (dataItem != null) {
                    this.tendslam = ''
                    this.currentRow = dataItem
                    this.enableCBO = false
                    
                    this.isCreateNew = false
                    this.cboBras = dataItem.BRAS_ID
                    if (this.cboBras > 0){
                        await this.layDsDslamWBras()
                    }
                    this.cboDslam = dataItem.DSLAM_ID
                    if (this.cboDslam > 0){
                        await this.layDsDslamVDC()
                    }
                    this.cboBrasNew = dataItem.BRAS_MOI_ID
                    if (this.cboBrasNew > 0) {
                        await this.onChangBrasNew()
                    }
                    this.cboDslamVDC = dataItem.DSLAM_CHA_ID
                    this.cboKieuCC = (dataItem.BRAS_ID === dataItem.BRAS_MOI_ID) ? 1 : 0
                    this.cboDslamNew = dataItem.DSLAM_MOI_ID
                    this.cboPortBras = dataItem.PORTBRAS_ID
                    this.cboSlotBras = dataItem.CARDBRAS_ID
                    let trangthai = dataItem.TRANGTHAI
                    this.tendslam = dataItem.DSLAM
                    if (trangthai == 0)
                    {
                        this.setDisableBtn(true, true, true, true);
                    }
                    else
                    {
                        this.setDisableBtn(false, false, false, false);
                    }
                    await this.layDsThueBao(dataItem.DSLAM_ID)
                    this.setDisableBtnVisa(this.dsThueBao.length > 0)
                }
                else
                {
                    return;
                }              
            } catch (err) {
               this.$toast.error(err)
            } finally {
                this.loading(false)
            }
        },
        onChangeCbxHoanThien: async function(){
            await this.layDsCatChuyen()
            // this.setDisableBtnX(this.dsCatChuyen !== null && this.dsCatChuyen.length >0)
            this.setDisableBtnVisa(this.dsThueBao.length > 0)
            //this.setDisableBtn(true, false, false, false)
            // if (this.dsCatChuyen == null || this.dsCatChuyen.length == 0){
            //     this.clear()
            // }
            
        },
        setDisableBtnVisa: function (isHasData) {
            this.isDisableBtn = !isHasData;
        },
        xoaCC: async function(){
            if(!this.isDisableBtnXoa) {
                return   
            }
            this.modalXoa = !this.modalXoa;
        },
        xacNhanXoa: async function() {
            try {
                this.loading(true)
                if (this.currentRow.TRANGTHAI === 0){
                    this.modalXoa = !this.modalXoa;
                    let rs = await this.$root.context.post(`/web-ecms/catchuyendslam/xoa?dslam_id=${this.currentRow.DSLAM_ID}`)
                    var check = rs.data.result
                    if(check) {
                        this.setDisableBtn(false, true, true, true)
                        await this.layDsCatChuyen()
                        //this.setDisableBtnX(this.dsCatChuyen !== null && this.dsCatChuyen.length >0)
                        //this.nhatMoi()
                        if(this.dsCatChuyen.length == 0){
                            this.setDisableBtn(true, false, false, false)
                            this.cboBras = null
                            this.cboDslam = null
                            this.cboBrasNew = null
                            this.cboDslamVDC = null
                            this.cboKieuCC = 0
                            this.cboDslamNew = null
                            this.cboPortBras = null
                            this.cboSlotBras = null
                        }
                        this.$toast.success('Xóa thông tin cắt chuyển thành công')
                    }
                    else {
                        this.$toast.error('Xóa thông tin cắt chuyển thất bại!')
                    }
                }
                else{
                    this.$toast.error('Không thể xóa thông tin cắt chuyển')
                }
            } catch (error) {
                this.$toast.error('Xóa thông tin cắt chuyển bại!' + error)
            }
            finally {
                this.loading(false)
            }
        },
        kiemtraDulieu: function(){
            this.$v.$touch()
            if (this.$v.$invalid && !this.$v.cboBras.required) {
                this.$toast.error('Bạn phải chọn Bras')
                return false
            }
            if (this.$v.$invalid && !this.$v.cboDslam.required) {
                this.$toast.error('Bạn phải chọn DSLAM')
                return false
            }
            if (this.$v.$invalid && !this.$v.cboBrasNew.required) {
                this.$toast.error('Bạn phải chọn Bras mới')
                return false
            }
            if (this.$v.$invalid && !this.$v.cboDslamVDC.required) {
                this.$toast.error('Bạn phải chọn DSLAM vdc')
                return false
            }
            if (this.$v.$invalid && !this.$v.cboPortBras.required) {
                this.$toast.error('Bạn phải chọn port mới')
                return false
            }
            if (this.$v.$invalid && !this.$v.cboSlotBras.required) {
                this.$toast.error('Bạn phải chọn slot mới')
                return false
            }
            if (this.cboKieuCC == 0)//Cắt chuyển sang 1 bras khác
            {
                if (this.cboBrasNew == this.cboBras)
                {
                    // this.$refs.cboKieu.focus()
                    // this.isError=true
                    this.$toast.error('Bras mới phải khác Bras hiện tại.')
                    return false
                }
                
            }
            else
            {
                if (this.cboBrasNew != this.cboBras)
                {
                    //  this.$refs.cboKieu.focus()
                    // this.isError=true
                    this.$toast.error('Kiểu cắt chuyển này chỉ đổi Slot, port Bras.')
                    return false
                }
            }
            return true
        },
        nhatMoi: async function(){
            try {
                this.loading(true)
                this.setDisableBtn(false, true, false, false);
                this.enableCBO = true
                this.cboBras = null
                this.cboDslam = null
                this.cboBrasNew = null
                this.cboDslamVDC = null
                this.cboKieuCC = 0
                this.cboDslamNew = null
                this.cboPortBras = null
                this.cboSlotBras = null
                this.chkDSHoanThien = false
                this.isCreateNew = true
                this.dsDlslamWBras = []
                this.dsDslamVDC = []
                this.dsDslamNew = []
                this.dsSlot = []
                this.dsPort = []
            } catch (error) {
                this.$toast.error(error)
            }
            finally{
                this.loading(false)
            }
            
            // await this.layDsCatChuyen()
        },
        hoanThienView: async function(){
            if(this.dsCatChuyen.length <= 0){
                return;
            }
            let hoanthienD = {
                        dslam_id: this.currentRow.DSLAM_ID,
                        dslam_moi_id: this.currentRow.DSLAM_MOI_ID,
                        bras_moi_id: this.currentRow.BRAS_MOI_ID,
                        portbras_id:  this.currentRow.PORTBRAS_ID,
                        slotbras_id:  this.currentRow.CARDBRAS_ID,
                        bras_ts: this.ts_bras
                        }
                let result = await this.$root.context.post('web-ecms/catchuyendslam/hoanthiendslam', hoanthienD)
                if (result){
                    await this.layDsCatChuyen()
                    this.$toast.success('Hoàn thiện thành công')
                }
                else{
                    this.$toast.error(result)
                }
            
        },
        setDisableBtn: function(isNhapmoi, isCapnhat, isXoa, isHoanthien) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnCapNhat = isCapnhat;
            this.isDisableBtnHoanThien = isHoanthien;
            this.isDisableBtnXoa = isXoa;
        },
        layDsCatChuyen: async function(){
            try {
                this.loading(true)
                this.dsThueBao = []
                this.dsCatChuyen = []
                let value = this.chkDSHoanThien ? 1 : 0
                let rs = await this.$root.context.get('/web-ecms/catchuyendslam/danhsachcatchuyen', { trangthai: value })
                if (rs.data.length > 0) {
                    this.dsCatChuyen = rs.data
                    this.$refs.grid1.setCurrentSelectedRow(0);
                    this.setDisableBtn(true, false, false, false)
                }
                else
                {
                    this.setDisableBtn(true, false, false, false)
                }
                this.setDisableBtn(true, false, false, false)
               
            } catch (error) {
                this.$toast.error(error)
            }
            finally{
                this.loading(false)
            }
        },
        layDsThueBao: async function(dslam_id){
            try {
                this.loading(true)
                this.dsThueBao = []
                let rs = await this.$root.context.get('/web-ecms/catchuyendslam/danhsachthuebao', { dslam_id: dslam_id })
                if (rs.data.length > 0) {
                    this.dsThueBao = rs.data
                }
                else{
                    this.dsThueBao = []
                }
                this.$refs.grid2.setCurrentSelectedRow(0);
                return this.dsThueBao
            } catch (error) {
                this.$toast.error('error '+error)
            }
            finally{
                this.loading(false) 
            }
            
        },
        KIEMTRA_CHUYEN_DSLAM_CHUAHT: async function(dslam_id)
        {
            try {
                let rs = await this.$root.context.get(`/web-ecms/catchuyendslam/kiemtracatchuyen?dslam_id=${dslam_id}`)
                if (rs.data.length > 0)  
                {
                    this.message = rs.data[0].NGUOI_CN
                    return true;
                }
                else return false;
            } catch (error) {
                return false;
            }

            
        },
        clear: function(){
            this.cboBras = null
            this.cboDslam = null
            this.cboDslamVDC = null
            this.cboBrasNew = null
            this.cboSlotBras = null
            this.cboDslamNew = null
            this.cboPortBras = null

        },
        laydulieuexcel: async function(dslam_id){
            this.dataExcel = []
            let rs = await this.$root.context.post('/web-ecms/catchuyendslam/laydulieuexceltheodslamid', { dslam_id: dslam_id })
            this.dataExcel = rs.data
            // if (rs.data.length > 0) {
            //     let temp2 = rs.data;
            //     for (let j = 0; j < temp2.length; j++) {
            //     let data = {
            //         ACCOUNT: temp2[j].ACCOUNT,
            //         IP_BRAS: temp2[j].IP_BRAS,
            //         MA_DSLAM: temp2[j].MA_DSLAM,
            //         THAMSO_DSLAM: temp2[j].THAMSO_DSLAM,
            //     };
            //     temp2.push(data);
            //     }
            //     this.dataExcel = temp2
            // }
            // else{
            //     this.dataExcel = []
            // }
        },
        xuatExcel: async function(){
            try {
                this.loading(true)
                let dslam_id = this.currentRow.DSLAM_ID;
                await this.laydulieuexcel(dslam_id)
                var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(this.dataExcel));
                var wb = XLSX.utils.book_new(); 
                XLSX.utils.book_append_sheet(wb, ma_thuebao, "VISA"); 
                XLSX.writeFile(wb, "export.xlsx");
            } catch (error) {
                this.$toast.error('error '+error)
            }
            finally{
                this.loading(false)
            }
            
        }
        
        
    }
}
</script>
    <style scoped>
    a.disabled {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
    }
    </style>

