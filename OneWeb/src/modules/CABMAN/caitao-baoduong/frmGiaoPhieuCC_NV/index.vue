<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
//import sendSms from '@/modules/QLSC/send-sms'
export default {
    data() {
        return {
            isOpened: false,
            KtThucHien: false,
            returnedMessage: null,
            resolvePromise: null,
            const: {
                ALL: -1
            },
            cboDonVi: {
                DataSource: [],
                Value: null,
                Enabled: false
            },
            cboTramTC: {
                DataSource: [],
                Value: null,
                Enabled: false
            },
            grcDSDG: {
                DataSource: []
            },
            grcDSCG: {
                DataSource: []
            },
            gridNhiemVu: {
                DataSource: []
            },
            cbxDonViQL: {
                Checked: false
            },
            ds_ctptm: "",
            phieucc_id: 0,
            hsptm_id: 0,
            chitietcc_id: 0,
            nhanvien_giao_id: 0,
            huonggiao_id: 0,
            dt_kqxl: [],
            ds_nv_duoc_chon: [],
            ngaygiao: null
        }
    },
    watch: {
        "cbxDonViQL.Checked": async function() {
            if (this.isOpened) {
                await this.cbxDonViQL_CheckedChanged()
            }
        },
        "cboTramTC.Value": async function() {
            if (this.isOpened) {
                await this.cboTramTC_EditValueChanged()
            }
        },
        "cboDonVi.Value": async function() {
            if (this.isOpened) {
                await this.cboDonVi_EditValueChanged()
            }
        },
        "grcDSCG.DataSource": function() {
            this.ds_nv_duoc_chon = []
        }
    },
    methods: {
        show() {
            this.$refs['ref-modal'].show()
            return new Promise(resolve => this.resolvePromise = resolve)
        },
        hide() {
            this.resolvePromise({ KtThucHien: this.KtThucHien, returnedMessage: this.returnedMessage})
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {

            if (this.KtThucHien && !!this.returnedMessage) {
                this.$root.toastSuccess(this.returnedMessage)
            }

            this.isOpened = false
            this.returnedMessage = null
            this.KtThucHien = false
            this.resolvePromise = null
            this.cboDonVi.DataSource = []
            this.cboDonVi.Value = null
            this.cboDonVi.Enabled = false
            this.cboTramTC.DataSource = []
            this.cboTramTC.Value = null
            this.cboTramTC.Enabled = false
            this.grcDSDG.DataSource = []
            this.grcDSCG.DataSource = []
            this.gridNhiemVu.DataSource = []
            this.cbxDonViQL.Checked = false
            this.ds_ctptm = ""
            this.phieucc_id = 0
            this.hsptm_id = 0
            this.chitietcc_id = 0
            this.nhanvien_giao_id = 0
            this.huonggiao_id = 0
            this.dt_kqxl = []
            this.ngaygiao = null
        },
        async onModalShown() {
            this.isOpened = true
            await this.frmGiaoPhieuCC_NV_Load()
        },
        async frmGiaoPhieuCC_NV_Load() {
            try {
                this.loading(true)
                this.gridNhiemVu.DataSource = await this.LAY_DS_NHIEMVU_V2(this.huonggiao_id)
                this.dt_kqxl = await this.LAY_DS_KQXL()
                await this.NAP_DS_DAGIAO();
                await this.NAP_DS_DONVI();
            } finally {
                this.loading(false)
            }
        },
        getColumnTemplateCheckbox(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div class="check-action">
                                <input 
                                    type="checkbox" 
                                    class="check"
                                    v-model="parent.ds_nv_duoc_chon"
                                    :value="data.KEY"
                                    disabled="true"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateTextInput(parent, columnName) {
            return function() {
                return { 
                    template: {
                        template: `
                            <input type="text" class="form-control" v-model.trim="value" />
                        `,
                        data() {
                            return { 
                                parent: parent,
                                columnName: columnName,
                                value: null,
                                data: { }
                            }
                        },
                        watch: {
                            value(newVal) {

                                this.parent.grcDSCG.DataSource.find(item => item.KEY == this.data.KEY)[this.columnName] = newVal

                                if (!!newVal && !this.parent.ds_nv_duoc_chon.includes(this.data.KEY)) {
                                    this.parent.ds_nv_duoc_chon.push(this.data.KEY)
                                }
                            }
                        },
                        mounted() {
                            this.value = this.data[this.columnName]
                        }
                    }
                }
            }
        },
        getColumnTemplateTextInputDsdg(parent, columnName) {
            return function() {
                return { 
                    template: {
                        template: `
                            <input type="text" class="form-control" v-model.trim="value" />
                        `,
                        data() {
                            return { 
                                parent: parent,
                                columnName: columnName,
                                value: null,
                                data: { }
                            }
                        },
                        watch: {
                            value(newVal) {
                                this.parent.grcDSDG.DataSource.find(item => item.KEY == this.data.KEY)[this.columnName] = newVal
                            }
                        },
                        mounted() {
                            this.value = this.data[this.columnName]
                        }
                    }
                }
            }
        },
        getHeaderTemplateKqxl() {
            return function() {
                return {
                    template: {
                        template: `<span style="color: maroon">Kết quả XL</span>`
                    }
                }
            }
        },
        getColumnTemplateKqxl(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <SelectExt
                                v-model="value"
                                :settings="{dropdownParent: parent.$refs['ref-modal-content']}"
                                placeholder="[Chưa chọn giá trị]"
                                :dataSource="dataSource"
                                dataValueField="KQXL_ID"
                                dataTextField="KETQUA_XL" />
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { },
                                value: null,
                                dataSource: []
                            }
                        },
                        watch: {
                            value(newVal) {
                                let rowData = this.parent.grcDSDG.DataSource.find(item => item.KEY == this.data.KEY)
                                rowData.KQXL_ID = !newVal ? null : parseInt(newVal)
                                rowData.KETQUA_XL = this.parent.dt_kqxl.find(item => item.KQXL_ID == rowData.KQXL_ID).KETQUA_XL
                            }
                        },
                        mounted() {
                            this.dataSource = this.parent.dt_kqxl.map(item => { return {...item} })
                            this.value = this.data.KQXL_ID
                        }
                    }
                }
            }
        },
        async giaoviec_nv_danhsach_ctbd(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/giaophieu_ccnv/giaoviec_nv_danhsach_ctbd`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data[0].KQ
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async btnGhiLai_Click() {
            try {
                this.loading(true)
                await this.GIAOPHIEU_NV()
            } finally {
                this.loading(false)
            }
        },
        async GIAOPHIEU_NV() {

            let hasData = this.grcDSCG.DataSource.some(item => {
                return !!item.NHIEMVU_ID
            })

            if (!hasData) {
                this.$root.toastError('Bạn chưa chọn nhiệm vụ cho nhân viên !')
                return
            }

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")

            this.grcDSCG.DataSource.forEach(item => {

                if (!item.NHIEMVU_ID) {
                    return
                }

                let vnhanvienid = parseInt(item.NHANVIEN_TH_ID)
                let vghichu = !item.GHICHU ? '' : item.GHICHU
                let vnd_tontai = !item.ND_TONTAI ? '' : item.ND_TONTAI
                let vnd_dexuat = !item.ND_DEXUAT ? '' : item.ND_DEXUAT
                let vkqxl_id = !item.KQXL_ID ? '' : parseInt(item.KQXL_ID)
                let vnhiemvu = item.NHIEMVU
                let arr_nhiemvu_id = item.NHIEMVU_ID.split(',')

                for (let j = 0; j < arr_nhiemvu_id.length; j++) {

                    let vnhiemvu_id = parseInt(arr_nhiemvu_id[j])

                    let node = doc.createElement("item")
                    node.setAttribute("ni", vnhanvienid)
                    node.setAttribute("nv", vnhiemvu)
                    node.setAttribute("gc", vghichu)
                    node.setAttribute("nvi", vnhiemvu_id)
                    node.setAttribute("kqi", vkqxl_id)
                    node.setAttribute("ndtt", vnd_tontai)
                    node.setAttribute("nddx", vnd_dexuat)
                    root.appendChild(node)
                }
            })

            doc.appendChild(root)

            let dsGiaoViec = (new XMLSerializer()).serializeToString(doc)

            let data = {
                phieucc_id: this.phieucc_id,
                ds_ctptm: this.ds_ctptm,
                nhanvien_id: this.$root.token.getNhanVienID(),
                hsptm_id: this.hsptm_id,
                ds_giaoviec: dsGiaoViec,
                nhanvien_giao_id: this.nhanvien_giao_id,
                ngaygiao: this.ddmmyyyyhhmiss(this.ngaygiao)
            }

            try {
                let kq = await this.giaoviec_nv_danhsach_ctbd(data)

                if (kq == "OK") {
                    this.returnedMessage = "Giao việc thành công"
                    this.KtThucHien = true
                    this.hide()
                } else {
                    throw kq
                }
            } catch (err) {
                this.$root.toastError(err)
            } finally {
                this.loading(false)
            }
        },
        async NAP_DS_CANGIAO_THEO_DV() {
            if (this.cbxDonViQL.Checked) {

                if (!!this.cboTramTC.Value) {

                    let tramTcId = parseInt(this.cboTramTC.Value)

                    this.grcDSCG.DataSource = await this.layDsCanGiaoTheoDv({
                        chitietcc_id: this.chitietcc_id,
                        phieucc_id: this.phieucc_id,
                        donvi_id: tramTcId == this.const.ALL ? 0 : tramTcId
                    })
                }
            } else {
                await this.NAP_DS_CANGIAO_THEO_NV()
            }
        },
        async NAP_DS_CANGIAO_THEO_NV() {
            this.grcDSCG.DataSource = await this.layDsCanGiaoTheoNv({
                chitietcc_id: this.chitietcc_id,
                phieucc_id: this.phieucc_id,
                nhanvien_id: this.nhanvien_giao_id
            })
        },
        async NAP_DS_DAGIAO() {
            this.grcDSDG.DataSource = await this.layDsDaGiao({
                ds_ctptm: this.ds_ctptm,
                phieucc_id: this.phieucc_id,
                hsptm_id: this.hsptm_id
            })
        },
        async NAP_DS_TRAMTC() {

            if (parseInt(this.cboDonVi.Value) == this.const.ALL) {
                this.cboTramTC.DataSource = await this.layAllDsToKt()
            } else {
                this.cboTramTC.DataSource = await this.layDsToKt(this.cboDonVi.Value)
            }

            if (this.cboTramTC.DataSource.length > 0) {
                this.cboTramTC.Value = this.cboTramTC.DataSource[0].DONVI_ID
            }
        },
        async NAP_DS_DONVI() {

            this.cboDonVi.DataSource = await this.layDsTtvt()

            if (this.cboDonVi.DataSource.length > 0) {
                this.cboDonVi.Value = this.cboDonVi.DataSource[0].DONVI_ID
            }
        },
        async LAY_DS_KQXL() {
            try {
                let response = await this.$root.context.get(`/web-cabman/giaophieu_ccnv/lay_ds_kqxl`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async LAY_DS_NHIEMVU_V2(huonggiao_id) {
            try {
                let response = await this.$root.context.get(`/web-cabman/giaophieu_ccnv/lay_ds_nhiemvu_v2`, {huonggiao_id})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => item.KEY = index + 1)
                    return response.data
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async layDsCanGiaoTheoDv(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/giaophieu_ccnv/lay_ds_cangiao_nhanvien_xl_theo_dv`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => item.KEY = index + 1)
                    return response.data
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async layDsCanGiaoTheoNv(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/giaophieu_ccnv/lay_ds_cangiao_nhanvien_xl_theo_nv`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => item.KEY = index + 1)
                    return response.data
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async layDsDaGiao(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/giaophieu_ccnv/lay_ds_dagiao_nhanvien_xl_ctbd`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => {
                        item.KEY = index + 1
                        item.KETQUA_XL = !item.KQXL_ID ? null : this.dt_kqxl.find(kqxl => kqxl.KQXL_ID == item.KQXL_ID).KETQUA_XL
                    })
                    return response.data
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async layDsToKt(ttvtId) {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsToKT`, {id: ttvtId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data.concat([{DONVI_ID: this.const.ALL, TEN_DV: 'Tất cả'}])
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async layAllDsToKt() {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsToKTTheoPhanVung`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data.concat([{DONVI_ID: this.const.ALL, TEN_DV: 'Tất cả'}])
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async layDsTtvt() {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsTTVTTrongToken`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return [{DONVI_ID: this.const.ALL, TEN_DV: 'Tất cả'}].concat(response.data)
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async btnCNKQXL_Click() {
            
            let tb_nvdgv = this.grcDSDG.DataSource
            
            if (tb_nvdgv.length <= 0) {
                this.$root.toastError("Bạn chưa giao việc cho nhân viên nào nên không thể cập nhật kết quả xử lý")
                return
            }

            let check_nv_gv = tb_nvdgv.some(item => !!item.KQXL_ID)

            if (!check_nv_gv) {
                this.$root.toastError("Bạn chưa chọn kết quả xử lý cho bất kỳ công việc nào nên không thể cập nhật kết quả xử lý ")
                return
            }

            try {
                this.loading(true)

                for (let i = 0; i < tb_nvdgv.length; i++) {

                    let row = tb_nvdgv[i]

                    if (!!row.KQXL_ID) {

                        let kqxl_id = row["KQXL_ID"]
                        let vnhanvien_th_id = row["NHANVIEN_TH_ID"]
                        let vnhiemvu_id = row["NHIEMVU_ID"]
                        let vchitietcc_id = row["CHITIETCC_ID"]
                        let vnd_dexuat = row["ND_DEXUAT"]
                        let vnd_tontai = row["ND_TONTAI"]

                        await this.capnhat_kqxl_giaoviec_nv_ctbd({
                            chitietcc_id: vchitietcc_id,
                            kqxl_id: kqxl_id,
                            nhanvien_th_id: vnhanvien_th_id,
                            nhiemvu_id: vnhiemvu_id,
                            nd_dexuat: vnd_dexuat,
                            nd_tontai: vnd_tontai,
                        })
                    }
                }

                this.$root.toastSuccess("Cập nhật kết quả xử lý thành công");
            } 
            catch(e) {
                this.$root.toastError("Cập nhật kết quả thất bại")
            } finally {
                this.loading(false)
            }
        },
        async capnhat_kqxl_giaoviec_nv_ctbd(data) {
            await this.$root.context.post(`/web-cabman/giaophieu_ccnv/capnhat_kqxl_giaoviec_nv_ctbd`, data)
        },
        getColumnTemplateChucNangDsdg(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div>
                                <a href="javascript:void(0)" class="btn btn-main pad4 lh14" @click="onClick(0)">
                                    <span class="-ap one-check"></span>
                                </a>
                                <a href="javascript:void(0)" class="btn btn-main pad4 lh14" @click="onClick(1)">
                                    <span class="-ap icon-close"></span>
                                </a>
                            </div>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        methods: {
                            async onClick(buttonIndex) {
                                let rowData = this.parent.grcDSDG.DataSource.find(item => item.KEY == this.data.KEY)
                                await this.parent.repoCHUCNANG_DSDG_ButtonClick(buttonIndex, rowData)
                            }
                        }
                    }
                }
            }
        },
        async repoCHUCNANG_DSDG_ButtonClick(buttonIndex, rowData) {
            switch (buttonIndex) {
                case 0:
                    if (!rowData.KQXL_ID) {
                        this.$root.toastError("Bạn chưa chọn kết quả xử lý!")
                        return;
                    }

                    try {
                        this.loading(true)

                        let kqxl_id = rowData.KQXL_ID
                        let vnhanvien_th_id = rowData.NHANVIEN_TH_ID
                        let vnhiemvu_id = rowData.NHIEMVU_ID
                        let vchitietcc_id = rowData.CHITIETCC_ID
                        let vnd_dexuat = rowData.ND_DEXUAT
                        let vnd_tontai = rowData.ND_TONTAI
                        
                        await this.capnhat_kqxl_giaoviec_nv_ctbd({
                            chitietcc_id: vchitietcc_id,
                            kqxl_id: kqxl_id,
                            nhanvien_th_id: vnhanvien_th_id,
                            nhiemvu_id: vnhiemvu_id,
                            nd_dexuat: vnd_dexuat,
                            nd_tontai: vnd_tontai,
                        })

                        this.$root.toastSuccess("Cập nhật kết quả xử lý thành công");
                        this.KtThucHien = true
                    }
                    catch (e) {
                        this.$root.toastError("Cập nhật kết quả xử lý thất bại");
                    } finally {
                        this.loading(false)
                    }
                    break;
                case 1:
                    let confirmation = await this.$bvModal.msgBoxConfirm("Bạn có đồng ý hủy phiếu giao việc này không?", {
                        title: 'Thông báo',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                        footerClass: 'p-2',
                        hideHeaderClose: false,
                        centered: true
                    })

                    if (!confirmation) {
                        return
                    }

                    try {
                        this.loading(true)

                        let vnhanvien_th_id = rowData.NHANVIEN_TH_ID
                        let vnhiemvu_id = rowData.NHIEMVU_ID
                        let vchitietcc_id = rowData.CHITIETCC_ID

                        await this.huy_giaoviec_nv_ctbd({
                            chitietcc_id: vchitietcc_id,
                            nhanvien_th_id: vnhanvien_th_id,
                            nhiemvu_id: vnhiemvu_id
                        })

                        this.$root.toastSuccess("Hủy phiếu giao việc thành công")
                        this.KtThucHien = true

                        setTimeout(() => {
                            this.grcDSDG.DataSource = this.grcDSDG.DataSource.filter(item => item.KEY != rowData.KEY)
                        }, 300)
                    }
                    catch (e) {
                        this.$root.toastError("Hủy giao việc nhân viên thất bại")
                    } finally {
                        this.loading(false)
                    }
                    break;
            }
        },
        async huy_giaoviec_nv_ctbd(data) {
            await this.$root.context.post(`/web-cabman/giaophieu_ccnv/huy_giaoviec_nv_ctbd`, data)
        },
        async btnLamMoi_DSNV_1_Click() {
            try {
                this.loading(true)
                await this.NAP_DS_DAGIAO();
            } finally {
                this.loading(false)
            }
        },
        async btnLamMoi_DSNV_Click() {
            try {
                this.loading(true)

                if (this.cbxDonViQL.Checked) {
                    await this.NAP_DS_CANGIAO_THEO_DV();
                } else {
                    await this.NAP_DS_CANGIAO_THEO_NV();
                }
            } finally {
                this.loading(false)
            }
        },
        async btnSMS_Click() {
            
            let confirmation = await this.$bvModal.msgBoxConfirm("Bạn có muốn nhắn tin giao việc cho nhân viên không ?", {
                title: 'Thông báo',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
                footerClass: 'p-2',
                hideHeaderClose: false,
                centered: true
            })

            if (!confirmation) {
                return
            }

            if (this.grcDSDG.DataSource.length <= 0)
            {
                this.$root.toastError("Chưa có thông tin giao việc nhân viên!")
                return
            }

            try {
                this.loading(true)

                let error = []

                for (let i = 0; i < this.grcDSDG.DataSource.length; i++) {
                    
                    let item = this.grcDSDG.DataSource[i]

                    let vchitietcc_id = item.CHITIETCC_ID
                    let vnhiemvu_id = item.NHIEMVU_ID
                    let vnhanvien_th_id = item.NHANVIEN_TH_ID
                    let vten_tbi = item.TEN_TBI
                    let noiDungGuiSms = await this.lay_nd_nhantin_giaoviec({
                        chitietcc_id: vchitietcc_id,
                        nhiemvu_id: vnhiemvu_id,
                        nhanvien_th_id: vnhanvien_th_id,
                        ten_ptm: vten_tbi
                    })
                    if(noiDungGuiSms.Result == true){
                            await this.capnhat_trangthai_da_gui_sms({
                                chitietcc_id: vchitietcc_id,
                                nhiemvu_id: vnhiemvu_id,
                                nhanvien_th_id: vnhanvien_th_id,
                            })
                    }
                    else {
                        error.push(noiDungGuiSms.Msg)
                    }
                    // let noiDungGuiSms = await this.lay_nd_nhantin_giaoviec({
                    //     chitietcc_id: vchitietcc_id,
                    //     nhiemvu_id: vnhiemvu_id,
                    //     nhanvien_th_id: vnhanvien_th_id,
                    //     ten_ptm: vten_tbi
                    // })

                    // if (!noiDungGuiSms || !noiDungGuiSms.SO_DT) {
                    //     error.push(`${vten_tbi}() - Không tìm thấy thông tin xử lý phần tử mạng để thực hiện nhắn tin!`)
                    // } else {
                    //     try {
                    //         await sendSms(noiDungGuiSms.SO_DT, noiDungGuiSms.NOIDUNG)
                    //         await this.capnhat_trangthai_da_gui_sms({
                    //             chitietcc_id: vchitietcc_id,
                    //             nhiemvu_id: vnhiemvu_id,
                    //             nhanvien_th_id: vnhanvien_th_id,
                    //         })
                    //     } catch(err) {
                    //         error.push(`${vten_tbi}(${noiDungGuiSms.SO_DT}) - Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)`)
                    //     }
                    // }
                }

                if (error.length == 0) {
                    this.$root.toastSuccess("Nhắn tin giao việc danh sách nhân viên thành công!");
                } else {
                    this.$root.toastError(error.join('\n'))
                }
            } finally {
                this.loading(false)
            }
        },
        async lay_nd_nhantin_giaoviec(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/giaophieu_ccnv/lay_nd_nhantin_giaoviec`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data[0]
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async capnhat_trangthai_da_gui_sms(data) {
            try {
                await this.$root.context.post(`/web-cabman/giaophieu_ccnv/capnhat_trangthai_da_gui_sms`, data)
            } catch (err) { }
        },
        async cbxDonViQL_CheckedChanged() {

            this.cboDonVi.Enabled = this.cbxDonViQL.Checked;
            this.cboTramTC.Enabled = this.cbxDonViQL.Checked;

            try {
                this.loading(true)

                if (this.cbxDonViQL.Checked) {
                    await this.NAP_DS_CANGIAO_THEO_DV();
                } else {
                    await this.NAP_DS_CANGIAO_THEO_NV();
                }
            } finally {
                this.loading(false)
            }
        },
        async cboTramTC_EditValueChanged() {
            try {
                this.loading(true)
                await this.NAP_DS_CANGIAO_THEO_DV();
            } finally {
                this.loading(false)
            }
        },
        async cboDonVi_EditValueChanged() {
            await this.NAP_DS_TRAMTC();
        },
        getColumnTemplateNhiemVu(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <SelectExt
                                ref="ref-select"
                                :settings="{dropdownParent: parent.$refs['ref-modal-content']}"
                                :dataSource="[]"
                                @open="onOpen" />
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { },
                                value: []
                            }
                        },
                        mounted() {

                            if (!this.data.NHIEMVU_ID) {
                                return
                            }

                            let dsNhiemVuId = this.data.NHIEMVU_ID.split(',').map(item => parseInt(item))
                            let dsNhiemVu = this.parent.gridNhiemVu.DataSource.filter(item => dsNhiemVuId.includes(item.NHIEMVU_ID))
                            this.value = dsNhiemVu.map(item => item.KEY)

                            let element = this.$refs['ref-select'].$el.querySelector('#select2--container span')
                            element.innerHTML = dsNhiemVu.map(item => item.NHIEMVU).join(';')
                            element.setAttribute('title', element.innerHTML)
                        },
                        methods: {
                            close() {
                                this.$refs['ref-select'].$children[0].select2.select2('close')
                            },
                            setValue(selectedRecords) {
                                
                                this.value = selectedRecords.map(item => item.KEY)

                                let nhiemvu = selectedRecords.map(item => item.NHIEMVU).join(';')
                                let nhiemvu_id = selectedRecords.map(item => item.NHIEMVU_ID).join(',')

                                let element = this.$refs['ref-select'].$el.querySelector('#select2--container span')
                                element.innerHTML = nhiemvu
                                element.setAttribute('title', nhiemvu)

                                let itemDscg = this.parent.grcDSCG.DataSource.find(item => item.KEY == this.data.KEY)
                                itemDscg.NHIEMVU = nhiemvu
                                itemDscg.NHIEMVU_ID = nhiemvu_id
                            },
                            onOpen(e) {

                                let nodeList = document.querySelectorAll(".select2-dropdown")
                                let dropdown = nodeList[nodeList.length - 1]

                                dropdown.innerHTML = "<div id='panel-chon-nhiemvu'></div>"

                                let component = new Vue({
                                    el: '#panel-chon-nhiemvu',
                                    data: {
                                        dataSource: [],
                                        parent: null
                                    },
                                    template: `
                                        <div class="popup-box" style="width: 705px">
                                            <div class="popup-header" style="padding: 5px 10px">
                                                <div>Chọn nhiệm vụ</div>
                                            </div>
                                            <div class="list-actions-top">
                                                <ul class="list">
                                                    <li @click="onClickBtnDongY">
                                                        <a href="javascript:void(0)">Đồng ý</a>
                                                    </li>
                                                    <li @click="onClickBtnHuy">
                                                        <a href="javascript:void(0)">Hủy</a>
                                                    </li>
                                                </ul>
                                            </div>
                                            <DataGrid
                                                ref='ref-grid'
                                                class="table-result"
                                                id="ref-grid"
                                                panelDataHeight="250"
                                                v-bind:columns="[
                                                    {fieldName: 'NHIEMVU_CHA', isGroupedColumn: true},
                                                    {fieldName: 'CHON', type: 'checkbox', headerText: ' ', textAlign: 'Center', allowFiltering: false, width: '70px' },
                                                    {fieldName: 'NHIEMVU', headerText: 'Nhiệm vụ', allowFiltering: true, width: '95%' }
                                                ]"
                                                v-bind:dataSource="dataSource"
                                                :showFilter="true"
                                                :allowPaging="false"
                                                :enablePagingServer="false"
                                                :enabledSelectFirstRow="false"
                                                @actionComplete='actionComplete'></DataGrid>
                                        </div>
                                    `,
                                    methods: {
                                        actionComplete(args) {

                                            if (args.requestType == "refresh") {

                                                this.$refs['ref-grid'].grid.ej2Instances.groupCollapseAll()

                                                setTimeout(() => {

                                                    let selectedRecords = this.dataSource.filter(item => this.parent.value.includes(item.KEY))

                                                    if (selectedRecords.length == 0) {
                                                        return
                                                    }

                                                    let parentRecords = []

                                                    for (let i = 0; i < selectedRecords.length; i++) {
                                                        parentRecords.push(selectedRecords[i].NHIEMVU_CHA)
                                                    }

                                                    parentRecords = [...new Set(parentRecords)]

                                                    parentRecords.forEach(item => {
                                                        document.querySelector(`#ref-grid td[ej-mappingvalue="${item}"]`).querySelector("div").click()
                                                    })

                                                    document.querySelector('#ref-grid .e-content').querySelector(`td[ej-mappingvalue="${parentRecords[0]}"]`).scrollIntoView()
                                                
                                                    let grid = this.$refs['ref-grid'].grid.ej2Instances

                                                    let selectedRowIndexs = []
                                                    let rows = grid.getRows()

                                                    for (let i = 0; i < rows.length; i++) {
                                                        
                                                        let rowInfo = grid.getRowInfo(rows[i])

                                                        if (this.parent.value.includes(rowInfo.rowData.KEY)) {
                                                            selectedRowIndexs.push(rowInfo.rowIndex)
                                                        }
                                                    }

                                                    grid.selectRows(selectedRowIndexs)
                                                }, 300)
                                            }
                                        },
                                        onClickBtnHuy() {
                                            this.parent.setValue([])
                                            this.parent.close()
                                        },
                                        onClickBtnDongY() {
                                            let selectedRecords = this.$refs['ref-grid'].grid.ej2Instances.getSelectedRecords()
                                            this.parent.setValue(selectedRecords)
                                            this.parent.close()
                                        }
                                    }
                                })
                               
                                component.parent = this
                                component.dataSource = this.parent.gridNhiemVu.DataSource.map(item => { 
                                    return {...item, CHON: false} 
                                })
                            }
                        }
                    }
                }
            }
        },
        getDateElements(date) {

            date = new Date(date)

            let day = date.getDate()
            day = day > 9 ? day : '0' + day

            let month = date.getMonth() + 1
            month = month > 9 ? month : '0' + month

            let hour = date.getHours()
            hour = hour > 9 ? hour : '0' + hour

            let min = date.getMinutes()
            min = min > 9 ? min : '0' + min

            let second = date.getSeconds()
            second = second > 9 ? second : '0' + second

            return {
                y: date.getFullYear(),
                M: month,
                d: day,
                h: hour,
                m: min,
                s: second
            }
        },
        ddmmyyyyhhmiss(date) {

            if (!date) {
                return ''
            }
            
            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}:${date.s}`
        },
    }
}
</script>
<style>
    .frm-giao-phieu-ccnv .grid-nhanvien-can-giao .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .frm-giao-phieu-ccnv .grid-nhanvien-can-giao .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }

    .frm-giao-phieu-ccnv #ref-grid .e-headercontent .e-checkbox-wrapper {
        display: none !important
    }
</style>
