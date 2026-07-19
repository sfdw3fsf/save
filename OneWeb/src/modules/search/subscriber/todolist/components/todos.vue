<template>
  <div class="todos">
    <todo v-for="todo in todos"
    v-bind:key="todo.id"
    v-bind:todo="todo"
    v-on:deletetodo="deletetodo"/>
  </div>
</template>

<script>
 import todo from './todo'

export default {
  name: 'todos',
  props:{
    todos:{
      type:Array,
      default:[]
    }
  },
  components: {  todo },
  data(){
    return {

    }
  },
  methods:{
    deletetodo(data){
      console.log('delete tung viec todo ',data);
      this.$emit('deletetodoevent',data);
    }
  }
}
</script>

<style>
</style>