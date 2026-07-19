<template src="./index.template.html"></template>
<style>
    .chon-cong-soi-bi-suco .e-treeview .e-list-item.e-disable > .e-text-content {
        opacity: 1 !important;
    }
</style>
<script>
export default {
    data() {
        return {
            loaiTbiId: null,
            thietBiId: null,
            congSoi: null,
            dsThietBi: [],
            dsPort: [],
            idThietBiTimKiem: null,
            treeView: null,
            kieu: 0
        }
    },
    computed: {
        treeFields() {
            return {
                dataSource: this.dsThietBi,
                id: "THIETBI_ID",
                text: "TEN_TBI",
                parentID: "THIETBI_CHA_ID",
                hasChildren: 'hasChildren'
            }
        },
        allowSelect() {
            return this.kieu == 0
        },
        title() {
            return this.allowSelect ? 'Chọn cổng thiết bị / sợi cáp bị ảnh hưởng bởi sự cố' : 'Chi tiết cổng sợi bị sự cố'
        }
    },
    watch: {
        idThietBiTimKiem(newVal) {

            if (!newVal) {
                return
            }
            
            let checkedNodes = this.treeView.ej2Instances.checkedNodes

            if (!checkedNodes || checkedNodes.length == 0) {
                this.treeView.ensureVisible(newVal)
                this.treeView.ej2Instances.checkedNodes = [newVal]
                this.treeView.ej2Instances.selectedNodes = [newVal]
                return
            }
            
            if (!checkedNodes.some(item => item.THIETBI_ID == newVal)) {
                this.treeView.ensureVisible(newVal)
                this.treeView.ej2Instances.checkedNodes = [newVal].concat(checkedNodes)
                this.treeView.ej2Instances.selectedNodes = [newVal]
            }
        }
    },
    methods: {
        show() {
            return this.$refs['ref-modal'].show()
        },
        hide() {
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {
            this.loaiTbiId = null
            this.thietBiId = null
            this.congSoi = null
            this.dsThietBi = []
            this.dsPort = []
            this.idThietBiTimKiem = null
            this.treeView = null
            this.kieu = 0
        },
        onModalShown() {
            this.treeView = this.$refs['ref-treeview']
            this.onClickBtnLamMoi()
        },
        async onClickBtnChon() {
            
            let checkedNodes = this.treeView.getAllCheckedNodes()
            let dsPort = this.dsThietBi.filter(item => {
                return item.HINHANH == 'PORT' && checkedNodes.some(value => value == item.THIETBI_ID)
            })

            if (dsPort.length == 0) {
                this.$root.toastError('Bạn chưa chọn cổng thiết bị / sợi cáp')
                return
            }

            dsPort = dsPort.map(item => item.THIETBI_ID)
            this.$emit('select', dsPort.join())
            this.hide()
        },
        async onClickBtnLamMoi() {
            try {
                this.loading(true)
                await this.layThietBi()
            } catch (err) {

            } finally {
                this.loading(false)
            }
            
            this.dsPort = []
            this.idThietBiTimKiem = null
        },
        onDataBound() {
            if (!!this.treeView) {
                
                if (!!this.congSoi) {

                    let congSoi = this.congSoi.split(',')

                    congSoi = this.dsThietBi.filter(item => {
                        return congSoi.some(element => element == item.THIETBI_ID)
                    })

                    if (congSoi.length > 0) {
                        this.treeView.ej2Instances.checkedNodes = congSoi.map(item => item.THIETBI_ID)
                    }
                }

                if (!this.allowSelect) {
                    this.treeView.ej2Instances.disableNodes(this.dsThietBi.map(item => item.THIETBI_ID))
                }
            }
        },
        onNodeChecked(args) {

            let checkedNodes = this.treeView.getAllCheckedNodes()
            let dsPort = this.dsThietBi.filter(item => {
                return item.HINHANH == 'PORT' && checkedNodes.some(value => value == item.THIETBI_ID)
            })

            this.dsPort = dsPort.map(item => {
                return this.layViTriCong(item)
            })
        },
        setLoaiTbiId(loaiTbiId) {
            this.loaiTbiId = loaiTbiId
        },
        setThietBiId(thietBiId) {
            this.thietBiId = thietBiId
        },
        setCongSoi(congSoi) {
            this.congSoi = congSoi
        },
        setKieu(kieu) {
            this.kieu = kieu
        },
        layViTriCong(node) {

            if (node.THIETBI_CHA_ID == null) {
                return node.TEN_TBI
            }

            let parentNode = this.dsThietBi.find(item => item.THIETBI_ID == node.THIETBI_CHA_ID)
            return this.layViTriCong(parentNode) + (node.VITRI != null ? '/' + node.VITRI : '')
        },
        async layThietBi() {

            let data = {
                loaitbi_id: this.loaiTbiId,
	            thietbi_id: this.thietBiId
            }

            try {
                let response = await this.$root.context.post(`/web-suco/danhmuc/cay-thietbi`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(item => {
                        item.hasChildren = true
                        item.expanded = true
                    })
                    this.dsThietBi = response.data
                    return
                }
                
                throw {response: { data: response}}
            } catch (err) {
                this.dsThietBi = []
            }
        }
    }
}
</script>
