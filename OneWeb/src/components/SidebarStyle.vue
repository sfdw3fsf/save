<template>
  <div class='sidebar'>
    <div class='search'>
      <span class='icon nc-icon-outline ui-1_zoom'></span>
      <input type='text' placeholder='Tìm kiếm' class='form-control'>
    </div>
    <ul class='menus'>
      <li class='haschild' v-for='(item, i) in menu' :key='i'>
        <a v-on:click='initMenu($event)'><span class='icon nc-icon-outline ui-1_settings-gear-63 bg-light'></span>{{item.TEN_MENU}}</a>
        <span v-if='item.CHILDREN' class='down' v-on:click='initMenu($event)'></span>
        <template v-if='item.CHILDREN'>
          <ul class='child' v-for='(item1, j) in item.CHILDREN' :key='j'>
            <li v-if='item1.CHILDREN'>
              <a v-on:click='initMenu($event)'>{{item1.TEN_MENU}}</a>
              <span v-if='item1.CHILDREN' class='down' v-on:click='initMenu($event)'></span>
              <ul class='child'>
                <li  v-for='(item2, k) in item1.CHILDREN' :key='k'>
                  <a href='#'>{{item2.TEN_MENU}}</a>
                </li>
              </ul>
            </li>
            <li v-if='!item1.CHILDREN'>
              <a href='#xxx'>{{item1.TEN_MENU}}</a>
            </li>
          </ul>
        </template>
      </li>
    </ul>
  </div>
</template>
<script>
import axios from 'axios'
import { mapGetters } from 'vuex'
export default {
  name: 'SidebarStyle',
  data () {
    return {
      menu: null
    }
  },
  mounted () {
    this.loading(true)
    axios
      .post(
        this.$API_URL + 'executor/admin_menu', {},
        {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'application/json'
          }
        }
      )
      .then((response) => {
        var menu = []
        $.each(response.data.data, function (i, v) {
          if (v.MENU_CHA_ID === 0) {
            menu.push(v)
          } else if (v.LEVEL_ === 2) {
            var item = menu.filter(j => j.MENU_ID === v.MENU_CHA_ID)[0]
            if (item !== undefined) {
              if (item['CHILDREN'] === undefined) {
                item['CHILDREN'] = []
              }
              item.CHILDREN.push(v)
            }
          } else {
            $.each(menu, function (j, x) {
              if (x.CHILDREN) {
                var item = x.CHILDREN.filter(j => j.MENU_ID === v.MENU_CHA_ID)[0]
                if (item !== undefined) {
                  if (item['CHILDREN'] === undefined) {
                    item['CHILDREN'] = []
                  }
                  item.CHILDREN.push(v)
                }
              }
            })
          }
        })
        this.menu = menu
        this.loading(false)
      })
      .catch((error) => {
        console.log(error)
        this.loading(false)
      })
  },
  destroyed () {

  },
  computed: {
    ...mapGetters({
      appName: 'appName',
      logo: 'logo'
    })
  },
  methods: {
    checkActive (route) {
      if (this.$route.name === route) {
        return true
      }
      if (route.includes(this.$route.name)) {
        return true
      }
    },
    initMenu (e) {
      console.log($(e.target))
      $(e.target).parent().siblings().removeClass('active')
      $(e.target).parent().addClass('active')
      $(e.target).parent().toggleClass('open')
    }
  }
}
</script>
