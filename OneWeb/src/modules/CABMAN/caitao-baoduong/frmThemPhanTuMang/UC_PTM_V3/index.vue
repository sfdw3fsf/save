<template src="./index.template.html"></template>
<script>
    export default {
        data() {
            return {
                dsThietBi: [],
                checkAllThietBi: false,
                dsKeyThietBiDuocChon: [],
                hs_ptm_id: null,
                isNew: true,
                dt1: [],
                vloaitb_id: null,
                vnhomtbi_id_2: null,
                tag: null,
                hscc_id: null,
                vphieucc_id: null,
                is_add_ptm: false,
                chapnhan: false,
                is_search: false,
                is_valid: true,
                vloaitb_id_tmp: 0,
                soluong: 0,
                ten_loai: "",
                old_loaitb: null,
                vdonvi_id: 0,
                dsTinhTrang: [],
                tltDonVi: {
                    DataSource: []
                },
                grcPTMDaGan: {
                    DataSource: [],
                    SelectedValue: []
                },
                grcThietBi: {
                    DataSource: [],
                    SelectedValue: []
                },
                cboNhomTbi: {
                    DataSource: [],
                    Value: null
                },
                cboLoaiTbi: {
                    DataSource: [],
                    Value: null
                },
                cboKieuTbi: {
                    DataSource: [],
                    Value: null
                },
                cboDungLuong: {
                    DataSource: [],
                    Value: null
                },
                cboLopMang: {
                    DataSource: [],
                    Value: null,
                    Visible: false
                },
                cboPhanLoaiKC: {
                    DataSource: [],
                    Value: null,
                    Visible: false
                },
                txtSoLuong: {
                    Text: ""
                },
                txtTenTbi: {
                    Text: ""
                },
                tsbtnGhiLai: {
                    Visible: false
                },
                tsbtnChapNhan: {
                    Visible: false
                },
            }
        },
        computed: {
            donvi_dl_id() {
                let donvi_dl_id = this.$root.context.user.getProperty('donvi_dl_id')
                return !donvi_dl_id ? 0 : donvi_dl_id 
            },
            treeFields() {
                return {
                    dataSource: this.tltDonVi.DataSource,
                    id: "ID",
                    text: "TEN_DV",
                    parentID: "PID",
                    hasChildren: 'hasChildren'
                }
            },
            vten_tbi() {
                return this.txtTenTbi.Text
            },
            vnhomtbi_id() {
                return !this.cboNhomTbi.Value ? -1 : parseInt(this.cboNhomTbi.Value)
            },
            vloaitbi_id() {
                return !this.cboLoaiTbi.Value ? -1 : parseInt(this.cboLoaiTbi.Value)
            },
            vkieutbi_id() {
                return !this.cboKieuTbi.Value ? -1 : parseInt(this.cboKieuTbi.Value)
            },
            vdungluong_id() {
                return !this.cboDungLuong.Value ? -1 : parseInt(this.cboDungLuong.Value)
            },
            vlopmc_id() {
                return !this.cboLopMang.Visible ? -1 : (!this.cboLopMang.Value ? -1 : parseInt(this.cboLopMang.Value))
            },
            vphanloaikc_id() {
                return !this.cboPhanLoaiKC.Visible ? -1 : (!this.cboPhanLoaiKC.Value ? -1 : parseInt(this.cboPhanLoaiKC.Value))
            }
        },
        watch: {
            "tltDonVi.DataSource": function() {
                this.vdonvi_id = 0
            },
            "cboLoaiTbi.Value": async function() {
                await this.cboLoaiTbi_EditValueChanged()
            },
            "cboNhomTbi.Value": async function() {
                await this.cboNhomTbi_EditValueChanged()
            },
            "grcThietBi.DataSource": function() {
                this.grcThietBi.SelectedValue = []
            },
        },
        methods: {
            async UC_PTM_V3(dt, vloaitb_id, vnhomtbi_id, vhsptm_id) {
                this.dt1 = dt;
                this.vloaitb_id = vloaitb_id;
                this.vnhomtbi_id_2 = vnhomtbi_id;
                this.vloaitb_id_tmp = vloaitb_id;
                this.isNew = false;
                this.tag = vhsptm_id;
                await this.UC_PTM_V3_Load()
            },
            async UC_PTM_V3_1(dt, vloaitb_id, vnhomtbi_id, vhsptm_id, soluong) {
                this.dt1 = dt;
                this.soluong = soluong;
                this.vloaitb_id = vloaitb_id;
                this.vnhomtbi_id_2 = vnhomtbi_id;
                this.isNew = false;
                this.tag = vhsptm_id;
                this.cboNhomTbi.Value = vnhomtbi_id;
                this.cboLoaiTbi.Value = vloaitb_id;

                let loaiTbiInfo = await this.lay_tt_loai_tbi(vloaitb_id)
                this.ten_loai = loaiTbiInfo.loai_tbi
                
                this.vloaitb_id_tmp = vloaitb_id;
                this.cboNhomTbi.ReadOnly = true;
                this.cboLoaiTbi.ReadOnly = true;
                this.txtSoLuong.Text = this.soluong;

                await this.UC_PTM_V3_Load()
            },
            async UC_PTM_V3_2(vphieucc_id, vhscc_id) {
                this.hscc_id = vhscc_id;
                this.vphieucc_id = vphieucc_id;
                this.isNew = true;
                this.is_add_ptm = true;
                await this.UC_PTM_V3_Load()
            },
            async UC_PTM_V3_3(is_search) {
                this.isNew = true;
                this.is_search = true;
                await this.UC_PTM_V3_Load()
            },
            tltDonVi_AfterFocusNode(args) {
                let nodeData = this.tltDonVi.DataSource.find(item => item.ID == args.node.dataset.uid)
                this.vdonvi_id = !nodeData ? 0 : nodeData.DONVI_ID
            },
            async lay_tt_loai_tbi(loaitbi_id) {
                try {
                    let response = await this.$root.context.get(`/web-cabman/them_ptm/lay_tt_loai_tbi?loaitbi_id=${loaitbi_id}`)
                    return response.data[0]
                } catch (err) {
                    return null
                }
            },
            async NAP_DS_DONVI() {
                try {
                    let response = await this.$root.context.get(`/web-cabman/tracuu-hscc-chuagiao/view_cay_donvi_v2?id=${this.donvi_dl_id}`)

                    if (response.error_code === 'BSS-00000000' && response.data != null) {
                        response.data.forEach(item => {
                            item.hasChildren = item.ISLEAF == 0
                            item.expanded = !item.PID
                        })

                        this.tltDonVi.DataSource = response.data
                    } else {
                        throw {response: { data: response}}
                    }
                } catch (err) {
                    this.tltDonVi.DataSource = []
                }
            },
            async NAP_DS_NHOM_TBI() {
                try {
                    let response = await this.$root.context.get(`/web-ecms/danhmuc/layDsNhomTbi`)
                    this.cboNhomTbi.DataSource = response.data

                    if (this.cboNhomTbi.DataSource.length > 0) {
                        this.cboNhomTbi.Value = this.cboNhomTbi.DataSource[0].NHOMTBI_ID
                    }
                } catch (err) {
                    this.cboNhomTbi.DataSource = []
                    this.cboNhomTbi.Value = null
                }
            },
            async NAP_DS_LOAI_TBI() {
                try {
                    let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsLoaiTbiTheoNhom`, {id: this.vnhomtbi_id})
                    this.cboLoaiTbi.DataSource = response.data

                    if (this.cboLoaiTbi.DataSource.length > 0) {
                        this.cboLoaiTbi.Value = this.cboLoaiTbi.DataSource[0].LOAITBI_ID
                    }
                } catch (err) {
                    this.cboLoaiTbi.DataSource = []
                    this.cboLoaiTbi.Value = null
                }
            },
            async NAP_DS_KIEU_TBI() {
                try {
                    let response = await this.$root.context.get(`/web-suco/danhmuc/ds-kieu-tbi?loaitbi_id=${this.vloaitbi_id}`)
                    this.cboKieuTbi.DataSource = [{KIEUTBI_ID: -1, KIEU_TBI: '-- Tất cả --'}].concat(response.data)

                    if (this.cboKieuTbi.DataSource.length > 0) {
                        this.cboKieuTbi.Value = this.cboKieuTbi.DataSource[0].KIEUTBI_ID
                    }
                } catch (err) {
                    this.cboKieuTbi.DataSource = []
                    this.cboKieuTbi.Value = null
                }
            },
            async NAP_DS_LOP_MC() {
                try {
                    let response = await this.$root.context.post(`/web-cabman/danhmuc/layDsLopMC`)
                    this.cboLopMang.DataSource = [{LOPMC_ID: -1, LOP_MC: '-- Tất cả --'}].concat(response.data)

                    if (this.cboLopMang.DataSource.length > 0) {
                        this.cboLopMang.Value = this.cboLopMang.DataSource[0].LOPMC_ID
                    }
                } catch (err) {
                    this.cboLopMang.DataSource = []
                    this.cboLopMang.Value = null
                }
            },
            async NAP_DS_PHANLOAI_KC() {
                try {
                    let response = await this.$root.context.post(`/web-cabman/danhmuc/layDsPhanLoaiKC`)
                    this.cboPhanLoaiKC.DataSource = [{PHANLOAIKC_ID: -1, PHANLOAI_KC: '-- Tất cả --'}].concat(response.data)

                    if (this.cboPhanLoaiKC.DataSource.length > 0) {
                        this.cboPhanLoaiKC.Value = this.cboPhanLoaiKC.DataSource[0].PHANLOAIKC_ID
                    }
                } catch (err) {
                    this.cboPhanLoaiKC.DataSource = []
                    this.cboPhanLoaiKC.Value = null
                }
            },
            async NAP_DS_DUNGLUONG_TBI() {
                try {
                    let response = await this.$root.context.get(`/web-suco/danhmuc/ds-dungluong-tbi?loaitbi_id=${this.vloaitbi_id}`)
                    this.cboDungLuong.DataSource = [{DUNGLUONG_ID: -1, DUNGLUONG: '-- Tất cả --'}].concat(response.data)

                    if (this.cboDungLuong.DataSource.length > 0) {
                        this.cboDungLuong.Value = this.cboDungLuong.DataSource[0].DUNGLUONG_ID
                    }
                } catch (err) {
                    this.cboDungLuong.DataSource = []
                    this.cboDungLuong.Value = null
                }
            },
            async NAP_DS_THIETBI() {

                if (this.old_loaitb != this.vloaitbi_id) {
                    this.grcPTMDaGan.DataSource = [];
                }

                let data = {
                    ten_tbi: this.vten_tbi,
                    nhomtbi_id: this.vnhomtbi_id,
                    loaitbi_id: this.vloaitbi_id,
                    kieutbi_id: this.vkieutbi_id,
                    donvi_id: this.vdonvi_id,
                    dungluong_id: this.vdungluong_id,
                    lopmc_id: this.vlopmc_id,
                    phanloaikc_id: this.vphanloaikc_id,
                }

                try {
                    let response = await this.$root.context.post(`/web-suco/qlsc/nghiem-thu/timKiemDsThietBi`, data)
                    this.grcThietBi.DataSource = response.data
                } catch (err) {
                    this.grcThietBi.DataSource = []
                }
            },
            async NAP_COMBO_REPO() {
                try {
                    let response = await this.$root.context.get(`/web-cabman/them_ptm/lay_ds_tinhtrang_ptm`)
                    this.dsTinhTrang = response.data
                } catch (err) {
                    this.dsTinhTrang = []
                }
            },
            async UC_PTM_V3_Load() {
                try {
                    this.loading(true)
                    await this.NAP_DS_DONVI();
                    await this.NAP_DS_NHOM_TBI();
                    await this.NAP_DS_LOP_MC();
                    await this.NAP_DS_PHANLOAI_KC();
                    await this.NAP_COMBO_REPO();
                } finally {
                    this.loading(false)
                }

                if (!this.isNew)
                {
                    if (parseInt(dt1[0]["FLAG"]) == 1) {
                        this.grcPTMDaGan.DataSource = [...this.dt1];
                    }
                        
                    this.cboNhomTbi.Value = this.vnhomtbi_id_2
                    this.cboLoaiTbi.Value = this.vloaitb_id_tmp

                    let loaiTbiInfo = await this.lay_tt_loai_tbi(this.cboLoaiTbi.Value)
                    this.ten_loai = loaiTbiInfo.loai_tbi
                    this.old_loaitb = this.vloaitbi_id;
                }

                if (this.is_add_ptm) {
                    this.tsbtnGhiLai.Visible = true;
                }

                if (this.is_search) {
                    this.tsbtnChapNhan.Visible = true;
                }
            },
            lay_ds() {
                return this.grcPTMDaGan.DataSource
            },
            async btnTimKiem_Click() {
                try {
                    this.loading(true)
                    await this.NAP_DS_THIETBI();
                } finally {
                    this.loading(false)
                }
            },
            async txtTenTbi_KeyUp() {
                await this.btnTimKiem_Click()
            },
            async cboLoaiTbi_EditValueChanged()
            {
                let loaiTbi = this.cboLoaiTbi.DataSource.find(item => parseInt(item.LOAITBI_ID) == parseInt(this.cboLoaiTbi.Value))

                if (loaiTbi.LOAI_TBI == "Cáp")
                {
                    this.cboLopMang.Visible = true;
                    this.cboPhanLoaiKC.Visible = false;
                }
                else
                {
                    this.cboLopMang.Visible = false;

                    if (loaiTbi.LOAI_TBI == "Kết cuối")
                    {
                        this.cboPhanLoaiKC.Visible = true;
                    }
                }

                await this.NAP_DS_KIEU_TBI();
                await this.NAP_DS_DUNGLUONG_TBI();
            },
            async cboNhomTbi_EditValueChanged()
            {
                await this.NAP_DS_LOAI_TBI();
            },
            btnPreviousDvi_Click() {

                if (this.grcThietBi.DataSource.length == 0) {
                    return;
                }

                if (this.grcThietBi.SelectedValue.length == 0) {
                    this.grcPTMDaGan.DataSource = []
                    this.grcPTMDaGan.SelectedValue = []
                    return
                }

                let thietbi_id = parseInt(this.grcThietBi.SelectedValue[0])
                let thietbi = this.grcThietBi.DataSource.find(item => item.THIETBI_ID == thietbi_id)
                this.old_loaitb = thietbi.LOAITBI_ID

                let ngay_bd = new Date()
                let ngay_kt = new Date()
                ngay_kt.setSeconds(ngay_kt.getSeconds() + 1)

                let dr = {
                    TINHTRANG_ID: 1,
                    GHICHU: null,
                    NGAY_BD: ngay_bd,
                    NGAY_KT: ngay_kt,
                    ...thietbi
                }

                this.grcPTMDaGan.DataSource = [dr]
                this.grcPTMDaGan.SelectedValue = [dr.THIETBI_ID]
            },
            btnNextDVi_Click() {
                if (this.grcPTMDaGan.SelectedValue.length > 0) {
                    this.grcPTMDaGan.DataSource = []
                    this.grcPTMDaGan.SelectedValue = []
                }
            },
            get_valid() {
                
                if (this.grcPTMDaGan.DataSource.length > 0) {

                    let thietbi = this.grcPTMDaGan.DataSource[0]

                    if (thietbi.NGAY_BD >= thietbi.NGAY_KT) {
                        return false;
                    }
                    else if (thietbi.NGAY_KT <= new Date()) {
                        return false;
                    }
                }

                return true;
            },
            async tsbtnGhiLai_Click() {
                
                if (this.get_valid() == false)
                {
                    this.$root.toastError("Thời gian bắt đầu và thời gian kết thúc chưa chính xác!");
                    return;
                }

                if (this.grcPTMDaGan.DataSource.length == 0)
                {
                    this.$root.toastError("Bạn chưa chọn phần tử mạng!");
                    return;
                }

                let thietbi = this.grcPTMDaGan.DataSource[0]

                let json_hs_ptm = JSON.stringify([{
                    LOAITBI_ID: thietbi.LOAITBI_ID,
                    SOLUONG: 1
                }])

                let json_ptm = JSON.stringify([{
                    LOAITBI_ID: thietbi.LOAITBI_ID,
                    PTM_ID: thietbi.THIETBI_ID,
                    TINHTRANG_ID: thietbi.TINHTRANG_ID,
                    GHICHU: thietbi.GHICHU,
                    NGAY_BD: this.ddmmyyyyhh24miss(thietbi.NGAY_BD),
                    NGAY_KT: this.ddmmyyyyhh24miss(thietbi.NGAY_KT)
                }])
                
                try {
                    this.loading(true)

                    let kq = await this.them_ptm({
                        hscc_id: this.hscc_id,
                        ds_hs: json_hs_ptm,
                        vs_ct: json_ptm,
                        phieucc_id: this.vphieucc_id
                    })

                    if (kq == "OK")
                    {
                        this.$root.toastSuccess("Cập nhật thành công!");
                        this.$emit("complete", { KtThucHien: true })
                    }
                    else
                    {
                        this.$root.toastError("Có lỗi! " + kq);
                    }
                } finally {
                    this.loading(false)
                }
            },
            async them_ptm(data) {
                try {
                    let response = await this.$root.context.post(`/web-cabman/them_ptm/them_ptm`, data)
                    return response.data[0].KQ
                } catch (err) {
                    return ""
                }
            },
            tsbtnChapNhan_Click() {
                this.chapnhan = true;
                this.$emit("complete", { KtThucHien: false })
            },
            repositoryItemDateEdit1_EditValueChanged(ngay_bd) {

                let thietbi = this.grcPTMDaGan.DataSource[0]
                thietbi.NGAY_BD = ngay_bd

                if (thietbi.NGAY_BD >= thietbi.NGAY_KT)
                {
                    this.$root.toastError("Ngày kết thúc phải lớn hơn ngày bắt đầu!");
                    this.is_valid = false;
                }
                else if (thietbi.NGAY_KT <= new Date())
                {
                    this.$root.toastError("Ngày kết thúc phải lớn hơn ngày hiện tại!");
                    this.is_valid = false;
                }
                else
                {
                    this.is_valid = true;
                }
            },
            repositoryItemDateEdit2_EditValueChanged(ngay_kt) {
                
                let thietbi = this.grcPTMDaGan.DataSource[0]
                thietbi.NGAY_KT = ngay_kt

                if (thietbi.NGAY_BD >= thietbi.NGAY_KT)
                {
                    this.$root.toastError("Ngày kết thúc phải lớn hơn ngày bắt đầu!");
                    this.is_valid = false;
                }
                else if (thietbi.NGAY_KT <= new Date())
                {
                    this.$root.toastError("Ngày kết thúc phải lớn hơn ngày hiện tại!");
                    this.is_valid = false;
                }
                else
                {
                    this.is_valid = true;
                }
            },
            getColumnTemplateChonThietBi(parent) {
                return function() {
                    return { 
                        template: {
                            template: `
                                <div class="check-action">
                                    <input 
                                        type="checkbox" 
                                        class="check"
                                        v-model="parent.grcThietBi.SelectedValue"
                                        :value="data.THIETBI_ID"
                                        @click="onClickChonThietBi($event)"/>
                                    <span class="name"></span>
                                </div>
                            `,
                            data() {
                                return { 
                                    parent: parent,
                                    data: { }
                                }
                            },
                            methods: {
                                onClickChonThietBi(event) {
                                    if (event.target.checked) {
                                        this.parent.grcThietBi.SelectedValue = [this.data.THIETBI_ID]
                                    }
                                }
                            }
                        }
                    }
                }
            },
            getColumnTemplateChonThietBiDaGan(parent) {
                return function() {
                    return { 
                        template: {
                            template: `
                                <div class="check-action">
                                    <input 
                                        type="checkbox" 
                                        class="check"
                                        v-model="parent.grcPTMDaGan.SelectedValue"
                                        :value="data.THIETBI_ID"/>
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
            getColumnTemplateTinhTrang(parent) {
                return function() {
                    return { 
                        template: {
                            template: `
                                <SelectExt
                                    :settings="{dropdownParent: parent.$refs.frm_uc_ptm_v3}"
                                    v-model="value" 
                                    :dataSource="dataSource" 
                                    dataValueField="TINHTRANG_ID"
                                    dataTextField="TINHTRANG" />
                            `,
                            data() {
                                return { 
                                    parent: parent,
                                    dataSource: [],
                                    value: null,
                                    data: { }
                                }
                            },
                            
                            watch: {
                                value(val) {
                                    this.parent.grcPTMDaGan.DataSource[0].TINHTRANG_ID = parseInt(val)
                                }
                            },
                            mounted() {

                                this.dataSource = this.parent.dsTinhTrang.map(item => { 
                                    return { 
                                        TINHTRANG_ID: item.TINHTRANG_ID,
                                        TINHTRANG: item.TINHTRANG
                                    }
                                })

                                if (this.dataSource.length > 0) {
                                    this.value = this.data.TINHTRANG_ID
                                }
                            },
                        }
                    }
                }
            },
            getColumnTemplateGhiChu(parent) {
                return function() {
                    return { 
                        template: {
                            template: `
                                <input ref="ref-ten-thietbi" type="text" class="form-control" v-model="value" />
                            `,
                            data() {
                                return { 
                                    parent: parent,
                                    value: null,
                                    data: { }
                                }
                            },
                            watch: {
                                value(val) {
                                    this.parent.grcPTMDaGan.DataSource[0].GHICHU = val
                                }
                            }
                        }
                    }
                }
            },
            getColumnTemplateNgayBd(parent) {
                return function() {
                    return { 
                        template: {
                            template: `
                                <ejs-datetimepicker	
                                    :style="{'padding-top': '7px'}"
                                    class="form-control bg-none bor0 fw6 text h24"
                                    format="dd/MM/yyyy HH:mm:ss"
                                    v-model="value"
                                    :showClearButton="false"></ejs-datetimepicker>
                            `,
                            data() {
                                return { 
                                    parent: parent,
                                    value: null,
                                    data: { },
                                }
                            },
                            mounted() {
                                this.value = this.data.NGAY_BD
                            },
                            watch: {
                                value(val) {
                                    this.parent.repositoryItemDateEdit1_EditValueChanged(val)
                                }
                            }
                        }
                    }
                }
            },
            getColumnTemplateNgayKt(parent) {
                return function() {
                    return { 
                        template: {
                            template: `
                                <ejs-datetimepicker	
                                    :style="{'padding-top': '7px'}"
                                    class="form-control bg-none bor0 fw6 text h24"
                                    format="dd/MM/yyyy HH:mm:ss"
                                    v-model="value"
                                    :showClearButton="false"></ejs-datetimepicker>
                            `,
                            data() {
                                return { 
                                    parent: parent,
                                    value: null,
                                    data: { }
                                }
                            },
                            mounted() {
                                this.value = this.data.NGAY_KT
                            },
                            watch: {
                                value(val) {
                                    this.parent.repositoryItemDateEdit2_EditValueChanged(val)
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
            ddmmyyyyhh24miss(date) {

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
    .frm-uc-ptm-v3 .grid-thietbi div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .frm-uc-ptm-v3 .grid-thietbi div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div,
    .frm-uc-ptm-v3 .grid-thietbi div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .frm-uc-ptm-v3 .grid-thietbi div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }

    .frm-uc-ptm-v3 .tree-plus-ext .e-treeview,
    .frm-uc-ptm-v3 .tree-plus-ext .e-treeview .e-ul {
        overflow: visible !important;
    }
</style>