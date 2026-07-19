<template src="./ModalDsBienDong.html"></template>
<script>
export default {
    name: "ModalDsBienDong",
    props: ["modalId","data"],
    computed: {},
    created: async function () {
    },
    watch:{
    },
    methods: {
        focusMyElement: async function () {
            try{
                //console.log('dsBienDong', this.data)
                this.dsBienDong = this.data.dsBienDong
            }catch(er){}
            finally{
            }
        },
        grid_selectedRowChanged: async function(dataItem){
            this.giaoDich= dataItem
        },
        chonGiaoDich:async function(){
            this.$emit('xacnhan',this.giaoDich);
            this.$bvModal.hide(this.modalId)
        },

    },
    data:function() {
        return {
            dsBienDong:[],
            giaoDich: {}
        };
    }
};
</script>
<style>
</style>
