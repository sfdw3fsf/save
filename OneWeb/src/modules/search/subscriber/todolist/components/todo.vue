<template>
  <div >
    <p class="todo is-complited" id="myid"><input type="checkbox" class="c"/>
    {{ todo.content }}
    <button class="del-btn"
    v-on:click="handleDelete">
    Delete</button>
  </p>
  </div>
</template>

<script>
export default {
    name: 'todo',
    props:{
      todo:{
        type:Object,
        default:null
      }
    },
    data(){
        return {
          
        }
    },
    methods:{
        handleDelete(e){
          // console.log(e);
          var data={
            id:this.todo.id
          }
          console.log('log tu todo');
          this.$emit('deletetodo',data);
        }
    }

}
</script>

<style>
.todo{
  background: #f4f4f4f4;
  padding: 10px;
  margin: 0;
  margin-bottom: 1px #ccc dotted;
}
.is-complited{
  text-decoration: line-through;
}
.del-btn{
  background-color: red;
  color: white;
  border: none;
  cursor: pointer;
  float: right;
}
</style>