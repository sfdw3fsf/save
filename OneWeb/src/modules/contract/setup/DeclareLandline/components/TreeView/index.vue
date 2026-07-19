<template>
    <ejs-treeview :seletedNodes="seletedNodes" :expandedNodes="expandedNodes" ref='treeview' :fields="fields" @nodeSelected="nodeSelected"></ejs-treeview>
</template>
<script>
export default {
    props: [
        'idField', 'textField', 'parentID'
    ],
    mounted() {
        console.log(this.parentID, "parentID")
    },
    data() {
        return {
            dataSource: [],
            seletedNodes: [],
            expandedNodes: []
        }
    },
    methods: {
        nodeSelected(data) {
            this.$emit("nodeSelected", data)
        }
    }
}
</script>