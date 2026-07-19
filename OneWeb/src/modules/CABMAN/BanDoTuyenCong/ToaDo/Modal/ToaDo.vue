<template src="./ToaDo.html"></template>
<script>
    import './../../../../../utils/gmaps'
    import GMap from '../../Gmap/GMap.vue'
    import GraphNode from '../../structures/GraphNode'
    export default {
        name: "ToaDo",
        props: ["modalId", "data"],
        created: async function () {
        },
        components:{GMap},
        computed: {},
        watch:{
            nodes: function(val){
                let map = this.$refs.mapcontrol
                map.clear()
                map.setNodes(this.nodes.slice(0))
                map.setLocationEx(null)
            }
        },
        methods: {
            focusMyElement: async function () {
                this.nodes=[]
                this.edit=false;
                let macdinh="21.019324322184655,105.80942518391265"
                let diachi=""
                console.log(this.data.toado)
                if (this.data == null) {
                    this.toado=macdinh
                }
                else {
                    if(this.data.toado=="0,0" || this.data.toado == "null,null")
                        this.toado  =macdinh
                    else{
                        this.toado  = this.data.toado
                    }
                    diachi= this.data.diachi!=""?this.data.diachi:"Vị trí mặc định theo tỉnh"
                }
                this.query= this.toado

                let node_n = this.taoDuLieuNode('COT_1',this.toado,diachi,101)
                this.nodes.push(node_n)

            },
            taoDuLieuNode:function(id,toado,diachi,icon){
                let node  = new GraphNode()
                try{
                    if(toado!=null){
                        node.id = id
                        if(diachi!="")
                            node.tooltipText = diachi
                        node.setPosition(toado)
                        node.setIcon(icon)
                        node.draggable = true
                    }
                    return node
                }catch(e){
                    return node
                }
            },
            xacnhan:async function(){
                try {
                    this.$root.showLoading(true);

                    this.$emit('xacnhan',this.toado)
                    this.$bvModal.hide(this.modalId)
                } catch (error) {
                    this.$toast.error(error.messeage);
                } finally {
                    this.$root.showLoading(false);
                }
            },
            print: function(){
                var divContents = document.getElementById("chonbando").innerHTML;
                var a = window.open('', '', 'height=500, width=500');
                a.document.write('<html>');
                a.document.write('<body > <h1>Bản đồ<br>');
                a.document.write(divContents);
                a.document.write('</body></html>');
                a.document.close();
                a.print();
            },
            luuToaDoBanDo: function(nodes){
                if(nodes!=null){
                    let node=nodes[0]
                    this.toado=node.lat+","+node.lng
                }
            },
            timkiem:function(){
                if(this.checkToaDo()){
                    if(this.nodes.length==2)
                        this.nodes.splice(1, 1);
                    var node = this.taoDuLieuNode('COT_2',this.query,'',3)
                    this.nodes.push(node)
                }
            },
            checkToaDo:function(){
                if(this.query=="")
                    return false
                else
                {
                    if(isNaN(parseFloat(this.query.split(',')[0])) ||isNaN(parseFloat(this.query.split(',')[1])))
                        return false
                }
                return true
            }
        },
        data: function () {
            return {
                toado:"",
                query:"",
                nodes:[]
            };
        },
    };
</script>
<style>
  div#popupTCTTTB___BV_modal_outer_ {
    z-index: 10 !important;
    margin-top: 100px;
  }
  .dashboard-header {
    z-index: 9 !important;
  }
  a.disabled {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }
</style>
