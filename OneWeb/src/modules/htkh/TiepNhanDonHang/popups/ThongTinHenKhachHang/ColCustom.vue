<template>
    <td v-if="visible" :class="{highlight1: highlight1}">
        <slot></slot>  
    </td>
</template>
<script>
export default {
    name:'col-custom',
    props: ['visible', 'highlight1'],
}
</script>