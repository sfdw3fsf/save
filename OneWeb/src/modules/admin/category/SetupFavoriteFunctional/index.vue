<template src="./template.html"></template>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import api from './API.js'
import { Icon } from '@iconify/vue2'
import toolFunc from './AutoAssignIcon.js'
export default {
  components: {
    breadcrumb,
    Icon
  },
  data() {
    return {
      menuItems: [],
      favoriteItems: [],
      searchMenu: '',
      listMenu: [],
      listStyle: 'metro',
      favoriteObjects: [],
      multiSelect: true,
      changeCount: 0,
    }
  },
  methods: {
    async load_data() {
      console.log('load_data')
      try {
        this.favoriteItems = []
        this.listMenu = []
        this.menuItems = []
        this.loading(true)
        this.changeCount = 0;
        let res = await this.$root.context.get('/quantri/user/khoitao_ungdung?p_idmodule=12')
        this.menuItems = this.createTree(res.data.ds_chucnang, 1, null).sort((a, b) => a.stt - b.stt)
        if(res.data.ds_yeuthich)
        this.favoriteItems = res.data.ds_yeuthich
        this.listMenu = this.addAttribute(this.menuItems)
        // console.log(this.listMenu);
        this.listMenu = this.flatten(this.listMenu)
        this.transformFavoriteIcon()
        // console.log(this.menuItems);
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    async refreshPage() {
      if(this.changeCount == 0) return;
      const confirm = await this.$confirm('Bạn có chắc chắn muốn làm mới ? Một số thao tác chưa được lưu lại sẽ mất', 'Làm mới danh sách yêu thích', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy bỏ',
        type: 'warning'
      })
      if(confirm)
      this.load_data()
    },
    flatten(a) {
      const flat = []
      a.forEach((item) => {
        flat.push(item)
        if (item.hasChildren) {
          flat.push(...this.flatten(item.children))
        }
      })

      return flat
    },
    addAttribute(raw) {
      const tree = []
      // First create all nodes
      raw.forEach((item) => {
        let newNode = {
          ...item,
          functional: false,
          unaccessable: false,
          hasChildren: false,
          open: false,
          show: item.level === 1,
          favorited: this.favoriteItems.includes(item.id_cn)
        }
        if (!item.children.length && item.url) {
          newNode.functional = true
        }
        if (!item.children.length && !item.url) newNode.unaccessable = true
        if (item.children.length) newNode.hasChildren = true
        newNode.children = this.addAttribute(item.children)
        tree.push(newNode)
      })
      // console.log(tree);
      return tree
    },
    createTree(raw, level, p_id) {
      const tree = []
      const copy = raw.filter((item) => item.level === level)
      for (const item of copy) {
        if (item.p_id == p_id) {
          item.children = this.createTree(raw, level + 1, item.id_cn)
          tree.push(item)
        }
      }
      return tree
    },
    selectMenu(item) {
      if (!item.functional) {
        if (item.hasChildren) {
          if (!item.open) {
            item.open = true
            this.listMenu.forEach((menu) => {
              if (menu.p_id === item.id_cn) {
                menu.show = true
                if (this.searchMenu !== '') this.searchMenuFunc()
              }
            })
          } else {
            item.open = false
            this.listMenu.forEach((menu) => {
              if (menu.p_id === item.id_cn) {
                menu.show = false
                if (menu.hasChildren && menu.open) this.selectMenu(menu)
              }
            })
          }
        }
      } else {
        if (!item.favorited) {
          this.favoriteItems.push(item.id_cn)
          item.favorited = true
          this.changeCount++;
        } else {
          this.favoriteItems = this.favoriteItems.filter((favorite) => favorite !== item.id_cn)
          item.favorited = false
          this.changeCount++;
        }
      }
    },
    getClass(item) {
      return {
        open: item.open,
        [`child${item.level}`]: item.level !== 1,
        childx: item.level !== 1,
        rootItem: item.level === 1
      }
    },
    searchMenuFunc() {
      if (this.searchMenu === '') {
        this.listMenu.forEach((menu) => {
          if (menu.level === 1) {
            menu.show = true
            menu.open = false
          } else menu.show = false
        })
      } else
        this.listMenu.forEach((menu) => {
          menu.show = false
          if (menu.displayName.toLowerCase().includes(this.searchMenu.toLowerCase()) && menu.functional) {
            menu.show = true
            this.showParent(menu.p_id)
          }
        })
    },
    showParent(id) {
      this.listMenu.forEach((menu) => {
        if (menu.id_cn === id) {
          menu.show = true
          menu.open = true
          this.showParent(menu.p_id)
        }
      })
    },
    transformFavoriteIcon() {
      this.favoriteObjects = []
      if(!this.favoriteItems != this.favoriteItems.length == 0) return;
      this.favoriteItems.forEach((item) => {
        this.listMenu.forEach((menu) => {
          if (menu.id_cn === item) {
            const newItem = {
              ...menu,
              name: menu.displayName.length > 30 ? menu.displayName.slice(0, 30) + '...' : menu.displayName,
              full_name: menu.displayName,
              icon: toolFunc.autoAssignIcon(menu.displayName),
              url: menu.url,
              id: menu.id_cn,
              selected: false,
              description: menu.mota_cn
            }
            this.favoriteObjects.push(newItem)
          }
        })
      })
      // console.log(this.favoriteObjects);
    },
    async removeSelected() {
      if (!this.favoriteObjects.some((item) => item.selected)) return this.$toast.error('Vui lòng chọn ít nhất 1 mục')
      try {
        this.loading(true)
        const rm = []
        this.favoriteObjects.forEach((item) => {
          if (item.selected) rm.push(item.id)
        })
        this.listMenu.forEach((item) => {
          rm.forEach((id) => {
            if (item.id_cn === id) {
              this.selectMenu(item)
            }
          })
        })
      } catch (error) {
      } finally {
        // this.load_data()
        this.loading(false)
      }
    },
    removeAll() {
      const rm = this.favoriteObjects.map((item) => item.id)
      this.listMenu.forEach((item) => {
        rm.forEach((id) => {
          if (item.id_cn === id) {
            this.selectMenu(item)
          }
        })
      })
    },
    selectFavorite(item) {
      if(!item.selected) item.selected = true;
      else
      item.selected = false
    },
    async saveChange() {
      const confirm = await this.$confirm('Bạn có chắc chắn muốn lưu thay đổi?', 'Lưu danh sách yêu thích', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy bỏ',
        type: 'warning'
      })
      if (confirm) {
        try {
          this.loading(true)
          const res = await api.updateFav(this.axios, {
            p_ds_chucnang_yeuthich: JSON.stringify(this.favoriteItems),
            p_ostype: 2
          })
          if (res.data.data.giatri == 1) this.$toast.success('Lưu thành công')
          else this.$toast.error('Lưu thất bại')
        } catch (error) {
          this.$toast.error('Lưu thất bại')
        } finally {
          this.load_data()
          this.loading(false)
        }
      }
    }
  },
  mounted() {
    this.load_data()
    // console.log(toolFunc.autoAssignIcon('Quản lý hợp đồng'))
  },
  watch: {
    'favoriteItems': function (val) {
      if(!val) return;
      this.transformFavoriteIcon()
    }
  }
}
</script>

<style lang="scss" src="./style.scss" scoped></style>
