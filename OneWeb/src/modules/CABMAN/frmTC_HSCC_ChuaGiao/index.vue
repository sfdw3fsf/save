<template src="./index.template.html"></template>
<script>
export default {
    data() {
        return {
            tltDonVi: {
                DataSource: []
            },
            grcDSPT: {
                DataSource: [],
            },
            grcDSGV: {
                DataSource: [],
                allowRefresh: false
            },
            gridTienTrinh: {
                DataSource: [],
                allowRefresh: true
            },
            grcThueBaoAnhHuong: {
                DataSource: [],
                allowRefresh: true
            },
            grcDSHSCC: {
                DataSource: [],
                idHoSoDuocChon: null,
                hoSoDuocChon: null
            },
            vdonvi_id: 0,
            hscc_id: -1,
            phieucc_id: -1,
            chitietcc_id: -1,
            hsptm_id: -1,
            ptm_id: -1,
            tabTienTrinh: {
                SelectedTabPageIndex: 1
            }
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
        }
    },
    watch: {
        "tltDonVi.DataSource": function() {
            this.vdonvi_id = 0
        },
        "grcDSHSCC.DataSource": function() {
            this.grcDSHSCC.idHoSoDuocChon = null
            this.grcDSHSCC.hoSoDuocChon = null
            this.hscc_id = -1
            this.phieucc_id = -1
            this.grcDSPT.DataSource = [];
            this.grcDSGV.DataSource = [];
            this.gridTienTrinh.DataSource = [];
        }
    },
    async mounted() {
        this.frmTC_HSCC_ChuaGiao_Load()
    },
    methods: {
        async frmTC_HSCC_ChuaGiao_Load() {
            await this.NAP_DS_DONVI();
        },
        async btnTimKiem_Click() {
            try {
                this.loading(true)
                await this.NAP_DS_HOSO_CATCHUYEN();
            } finally {
                this.loading(false)
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
        tltDonVi_AfterFocusNode(args) {
            let nodeData = this.tltDonVi.DataSource.find(item => item.ID == args.node.dataset.uid)
            this.vdonvi_id = !nodeData ? 0 : nodeData.DONVI_ID
        },
        async NAP_DS_HOSO_CATCHUYEN() {

            this.grcDSPT.DataSource = [];
            this.grcDSGV.DataSource = [];
            this.gridTienTrinh.DataSource = [];

            let data = {
                trangthai_id: 0,
                donvi_id: this.vdonvi_id,
                ma_hs_cc: 0
            }

            try {
                let response = await this.$root.context.post(`/web-cabman/tracuu-hscc-chuagiao/lay_ds_hoso_ctbd_donvi`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.grcDSHSCC.DataSource = response.data
                } else {
                    throw {response: { data: response}}
                }
            } catch (err) {
                this.grcDSHSCC.DataSource = []
            }

            if (this.grcDSHSCC.DataSource.length == 0) {
                this.hscc_id = -1;
                this.phieucc_id = -1;
                this.chitietcc_id = -1;
            }
        },
        getColumnTemplateChonHoSo(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div class="check-action" @click="repoCHON_DSHSCC_CheckedChanged()">
                                <input 
                                    type="radio" 
                                    class="check"
                                    v-model="parent.grcDSHSCC.idHoSoDuocChon"
                                    :value="data.HSCC_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        computed: {
                            isSelected() {
                                return this.parent.grcDSHSCC.idHoSoDuocChon == this.data.HSCC_ID
                            }
                        },
                        methods: {
                            repoCHON_DSHSCC_CheckedChanged() {
                                if (this.isSelected) {
                                    this.parent.grcDSHSCC.idHoSoDuocChon = null
                                    this.parent.grcDSHSCC.hoSoDuocChon = null
                                    this.parent.hscc_id = -1
                                    this.parent.phieucc_id = -1
                                    this.parent.grcDSPT.DataSource = []
                                    this.parent.grcDSGV.DataSource = []
                                    this.parent.gridTienTrinh.DataSource = []
                                    this.parent.grcThueBaoAnhHuong.DataSource = []
                                } else {
                                    this.parent.grcDSHSCC.idHoSoDuocChon = this.data.HSCC_ID
                                    this.parent.grcDSHSCC.hoSoDuocChon = this.data
                                    this.parent.hscc_id = this.data.HSCC_ID
                                    this.parent.phieucc_id = this.data.PHIEUCC_ID
                                    this.parent.NAP_DS_PHANTU_MANG();
                                }
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateChonPtm() {
            return function() {
                return { 
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return { 
                                
                            }
                        },
                    }
                }
            }
        },
        async NAP_DS_PHANTU_MANG() {

            let data = {
                hscc_id: this.hscc_id,
                phieucc_id: this.phieucc_id
            }

            try {
                this.loading(true)
                let response = await this.$root.context.post(`/web-cabman/tracuu-hscc-chuagiao/lay_ds_ptm_caitao_baoduong`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.grcDSPT.DataSource = response.data
                } else {
                    throw {response: { data: response}}
                }
            } catch (err) {
                this.grcDSPT.DataSource = []
            } finally {
                this.loading(false)
            }
        },
        async NAP_DS_GIAOVIEC() {

            let data = {
                chitietcc_id: this.chitietcc_id,
                phieucc_id: this.phieucc_id
            }

            try {
                let response = await this.$root.context.post(`/web-cabman/tracuu-hscc-chuagiao/lay_ds_gv_caitao_baoduong`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.grcDSGV.DataSource = response.data
                } else {
                    throw {response: { data: response}}
                }
            } catch (err) {
                this.grcDSPT.DataSource = []
            }
        },
        async NAP_DS_TIENTRINH() {
            try {
                let response = await this.$root.context.get(`/web-cabman/tracuu-hscc-chuagiao/lay_ds_tientrinh_baoduong?chitietcc_id=${this.chitietcc_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.gridTienTrinh.DataSource = response.data
                } else {
                    throw {response: { data: response}}
                }
            } catch (err) {
                this.gridTienTrinh.DataSource = []
            }
        },
        async NAP_DS_THUEBAO_ANHHUONG() {
            try {
                let response = await this.$root.context.get(`/web-cabman/tracuu-hscc-chuagiao/lay_ds_thuebao_anhhuong_baoduong?chitietcc_id=${this.chitietcc_id}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.grcThueBaoAnhHuong.DataSource = response.data
                } else {
                    throw {response: { data: response}}
                }
            } catch (err) {
                this.grcThueBaoAnhHuong.DataSource = []
            }
        },
        async grvDSPT_FocusedRowChanged(args) {

            this.chitietcc_id = args.data.CHITIETCC_ID;
            this.hsptm_id = args.data.HSPTM_ID;
            this.ptm_id = args.data.PTM_ID;

            await this.NAP_DS_GIAOVIEC();
            await this.NAP_DS_TIENTRINH();
            await this.NAP_DS_THUEBAO_ANHHUONG();
        },
        onActionCompleteGridGiaoViec(args) {
            if (args.requestType == "refresh" && this.tabTienTrinh.SelectedTabPageIndex != 1) {
                this.grcDSGV.allowRefresh = true
            }
        },
        onActionCompleteGridTienTrinh(args) {
            if (args.requestType == "refresh" && this.tabTienTrinh.SelectedTabPageIndex != 2) {
                this.gridTienTrinh.allowRefresh = true
            }
        },
        onActionCompleteGridThueBaoAnhHuong(args) {
            if (args.requestType == "refresh" && this.tabTienTrinh.SelectedTabPageIndex != 3) {
                this.grcThueBaoAnhHuong.allowRefresh = true
            }
        },
        tabTienTrinh_SelectedPageChanged(tabIndex) {
            
            this.tabTienTrinh.SelectedTabPageIndex = tabIndex

            if (tabIndex == 1 && this.grcDSGV.allowRefresh) {
                this.grcDSGV.allowRefresh = false
                setTimeout(() => { this.$refs['ref-grid-giao-viec'].grid.refresh() }, 200)
                return
            }

            if (tabIndex == 2 && this.gridTienTrinh.allowRefresh) {
                this.gridTienTrinh.allowRefresh = false
                setTimeout(() => { this.$refs['ref-grid-tien-trinh'].grid.refresh() }, 200)
                return
            }

            if (tabIndex == 3 && this.grcThueBaoAnhHuong.allowRefresh) {
                this.grcThueBaoAnhHuong.allowRefresh = false
                setTimeout(() => { this.$refs['ref-grid-tbah'].grid.refresh() }, 200)
            }
        },
        grvDSHSCC_DoubleClick(args) {

            if (!args || !args.rowData) {
                return
            }

            switch(args.rowData.TTCC_ID) {
                case 5:
                    this.$router.push('/cabman/caitao-baoduong')
                    break
                case 12:
                    this.$router.push('/cabman/tiepnhan-caitao-baoduong')
                    break
                case 13:
                    this.$router.push('/cabman/duyet-caitao-baoduong')
                    break
            }
        }
    }
}
</script>
<style>
    .frm-tra-cuu-hscc-chua-giao .grid-hoso div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .frm-tra-cuu-hscc-chua-giao .grid-hoso div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div,
    .frm-tra-cuu-hscc-chua-giao .grid-hoso div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .frm-tra-cuu-hscc-chua-giao .grid-hoso div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }

    .frm-tra-cuu-hscc-chua-giao .tree-plus-ext .e-treeview,
    .frm-tra-cuu-hscc-chua-giao .tree-plus-ext .e-treeview .e-ul {
        overflow: visible !important;
    }
</style>