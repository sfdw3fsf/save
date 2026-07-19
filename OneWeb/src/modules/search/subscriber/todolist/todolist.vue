<template >
  <div id="app">
    <Header v-bind:titleheader="title"></Header>
    <p>my app vue</p>
    <todos v-bind:todos="todos" v-on:deletetodoevent="deleletodo" />
    <demoref />
    <!-- <demo-slot v-slot="CompTabs">    
      <div class="app-slot" >
        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Sequi dolor rerum unde possimus dicta reiciendis, reprehenderit asperiores explicabo harum impedit voluptatem eveniet nobis quod laborum sunt, nihil alias necessitatibus modi?</p>
      </div>
    </demo-slot> -->
  </div>
</template>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
<script>
// import { Header } from '@syncfusion/ej2-vue-navigations';
import Header from './components/Header'
import todos from './components/todos'
import demoref from './components/DemoRef.vue'
// import demoslot from './components/DemoSlot.vue'

export default {
  name: 'App',
  components: { Header, todos, demoref },
  data() {
    return {
      title: 'List viec can lam',
      todos: [
        { id: 1, content: 'Viec 1', iscomplite: true },
        { id: 2, content: 'Viec 2', iscomplite: false },
        { id: 3, content: 'Viec 3', iscomplite: false },
        { id: 4, content: 'Viec 4', iscomplite: true },
        { id: 5, content: 'Viec 5', iscomplite: false },
        { id: 6, content: 'Viec 6', iscomplite: true }
      ]
    }
  },
  methods: {
    deleletodo(data) {
      console.log('danlde delete to to form todolist', data)
      var indexDelete = -1
      this.todos.forEach((u, idx) => {
        console.log(u.id, idx, data.id)
        if (u.id === data.id) {
          indexDelete = idx
        }
      })
      console.log('indexDelete sau khi chay vong for', indexDelete)
      if (indexDelete != -1) {
        this.todos.splice(indexDelete, 1)
      }
    }
  }
}
</script>

<style>
#app {
  font-family: Arial, Helvetica, sans-serif;
}
.app-slot{
  color: #000;
}
</style>