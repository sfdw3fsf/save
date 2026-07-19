<template src="./index.html">
</template>

<script>
export default {
    components: {},
    name: 'Modal',
    props: {
        id: String,
        dataSend: Object
    },
    data: function() {
        return {
            dtList: [],
            modelCbo:{
                cboKieuKH: 0,
                vcboKieuKH: [],
                cboNhomKH: 0,
                vcboNhomKH: []
            },
            key: 1,
            isShown: false,
            show:false,
            loaikh_id: -1,
            kieukh_id: -1,
            nhomlkh_id: -1,
            selectedRow: {}
        }
    },
    computed: {
        // dieukienTK: function() {
        //     let obj = this.dsKieuTK.find(v => v.kieutk_id == this.kieuTKId);
        //     return obj.dieukien;
        // }
    },
    methods: {
        onHiddenModal() {
            this.isShown = false
            this.show = false
            this.key++
        
            //   this.dataSearch = []
            //   this.tabThueBao = false
        },
        selectedItemsDsHoSo: async function(data){
            console.log('row selected',data)
            this.selectedRow = data
        },
        HT_Kieu_KH_Combobox: async function (){
            let data = {
            P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_KIEU_KH'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            this.modelCbo.vcboKieuKH = res.data
            if (this.dataSend.kieukh_id != -1){
                this.modelCbo.cboKieuKH = this.dataSend.kieukh_id
            }
            else {
                this.modelCbo.cboKieuKH = res.data[0].KIEU_ID    
            }
            
            if  (this.modelCbo.cboKieuKH > -1)
                await this.HT_Nhom_KH_Combobox(this.modelCbo.cboKieuKH) 
        },
        HT_Nhom_KH_Combobox: async function (kieu_kh){
            let data = {
            P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            P_KIEU_KH: kieu_kh,
            PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_NHOM_KH'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            this.modelCbo.vcboNhomKH = res.data
            this.modelCbo.cboNhomKH = res.data[0].NHOMLKH_ID
            if (this.dataSend.nhomlkh_id != -1){
                this.modelCbo.cboNhomKH = this.dataSend.nhomlkh_id
            }
            else {
                this.modelCbo.cboNhomKH = res.data[0].NHOMLKH_ID    
            }
            if  (this.modelCbo.cboNhomKH != -1)
                this.dtList = await this.Lay_ds_loaikh(this.modelCbo.cboNhomKH);
            
        },
        Lay_ds_loaikh: async function(nhomKH){
            let data = {
            P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            VNHOMLKH_ID: nhomKH,
            PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LAY_DS_LOAIKH'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            return res.data;
        },
        changeCBO: async function(){
            let data = {
            P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            P_KIEU_KH: this.modelCbo.cboKieuKH,
            PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_NHOM_KH'
            }
            let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
            this.modelCbo.vcboNhomKH = res.data
            this.modelCbo.cboNhomKH = res.data[0].NHOMLKH_ID
            this.modelCbo.cboNhomKH = res.data[0].NHOMLKH_ID    
            if  (this.modelCbo.cboNhomKH != -1)
                this.dtList = await this.Lay_ds_loaikh(this.modelCbo.cboNhomKH);
        },
        onDoubleClickRow: async function(){
            await this.chapnhan();
        },
        chapnhan: async function(){
            if (this.dtList.length <= 0)
            {
                this.$root.$toast.error('Không có dữ liệu!')
                return;
            }
            this.loaikh_id = this.selectedRow.LOAIKH_ID;
            this.$emit('chon', this.loaikh_id)
            this.$bvModal.hide(this.id)
        },
        changeCBO2: async function(){
            this.dtList = []
            if  (this.modelCbo.vcboNhomKH.length == 0)
                return;
            this.dtList = await this.Lay_ds_loaikh(this.modelCbo.cboNhomKH)  
        },
        async onShownModal() {
            this.isShown = true
            this.show = true
            try {
                this.$root.showLoading(true)
                await this.HT_Kieu_KH_Combobox();
            } catch (error) {
                this.$root.toastError(error)
            }
            finally{
                this.$root.showLoading(false)
            }
        },
    },
    mounted: async function() {
        try {
            this.loading(true);
        } catch (error) {
            
        } finally {
            this.loading(false);
        }
    },
    created: async function() {
        try {
            this.loading(true);
            
        } catch (error) {
            
        } finally {
            this.loading(false);
        }
    },
    watch: {
        
    }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
