<template src="./index.template.html"></template>
<script>
export default {
    data() {
        return {
            parameters: {
                tabContentConstructor: null,
                globalData: null
            },
            isDown: false,
            isOver: false,
            pos: { left: 0, x: 0 },
            selectedTabId: null,
            items: [],
        }
    },
    computed: {
        tabs() {
            return this.$refs['ref-tabs']
        }
    },
    methods: {
        setTabContentConstructor(tabContentConstructor) {
            this.parameters.tabContentConstructor = tabContentConstructor
        },
        setGlobalData(data) {
            this.parameters.globalData = data
        },
        setSelectedTabId(selectedTabId) {
            this.selectedTabId = selectedTabId
            if (this.isOver) {
                this.tabs.scrollLeft = this.$refs[`tab-${this.selectedTabId}`][0].offsetLeft
            }
        },
        setNameOfTabItemById(id, name) {
            let tabObj = this.getTabItemById(id)
            tabObj.args.name = name
        },
        getTabItemById(id) {
            return this.items.find(item => item.args.id == id)
        },
        getDsComponentTabItem() {
            return this.items.map(item => this.$refs[item.args.id][0])
        },
        getSelectedTabItemComponent() {
            return !this.selectedTabId ? null : (this.$refs[this.selectedTabId])[0]
        },
        getEmptyTabComponent() {
            for (let i = 0; i < this.items.length; i++) {

                let item = this.items[i]

                if (item.args.name == 'New tab') {
                    return (this.$refs[item.args.id])[0]
                }
            }

            return null
        },
        clear() {
            this.items = []
            this.selectedTabId = null
        },
        onMousedown(e) {
            this.isDown = true
            this.pos = {
                left: this.tabs.scrollLeft, // the current scroll 
                x: e.clientX,               // the current mouse position
            }
        },
        onMouseleave(e) {
            this.isDown = false
        },
        onMouseup(e) {
            this.isDown = false
        },
        onMousemove(e) {
            if(!this.isDown) { 
                return 
            }
            e.preventDefault()
            const dx = e.clientX - this.pos.x           // how far the mouse has been moved
            this.tabs.scrollLeft = this.pos.left - dx   // scroll the element
        },
        onClickAddTab() {
            this.addTab(null)
        },
        addTab(data, tabName) {

            let index = 1

            if (this.items.length > 0) {
                let lastItem = this.items[this.items.length - 1]
                index = lastItem.args.index + 1
            }

            this.selectedTabId = `item${index}`

            let args = { 
                id: this.selectedTabId, 
                index: index,
                name: !tabName ? 'New tab' : tabName,
                data: { ...data, ...this.parameters.globalData }
            }

            this.items.push(this.parameters.tabContentConstructor(args, this))

            setTimeout(() => {
                this.tabs.scrollLeft = this.tabs.scrollWidth
                this.isOver = this.tabs.scrollWidth > this.tabs.clientWidth
            }, 50)
        },
        onClickTab(tabItem, e) {

            e.preventDefault()

            if (e.target.tagName.toLowerCase() == 'span') {

                if (this.items.length == 1) {
                    return
                }

                let items = []
                let selectedTabId = this.selectedTabId

                for (let i = 0; i < this.items.length; i++) {

                    let item = this.items[i]

                    if (item.args.id != tabItem.args.id) {
                        items.push(item)
                        continue
                    }

                    if (item.args.id == this.selectedTabId) {
                        let selectedTab = i == 0 ? this.items[1] : this.items[i - 1]
                        selectedTabId = selectedTab.args.id
                    }
                }
                
                this.selectedTabId = selectedTabId
                this.items = items

                setTimeout(() => {
                    this.isOver = this.tabs.scrollWidth > this.tabs.clientWidth
                }, 50)

                return
            }

            this.selectedTabId = tabItem.args.id
        },
    }
}
</script>
<style scoped>
    .tabs a span.remove-item {
        display: none;
    }

    .tabs a:hover span.remove-item {
        display: inline;
    }
</style>